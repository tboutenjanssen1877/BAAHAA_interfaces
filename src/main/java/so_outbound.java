import Structures.SalesOrders.LinesItem;
import Structures.SalesOrders.SalesOrders;
import Structures.SalesOrders.SalesorderDataItem;
import com.janssengroup.FunctionLibrary.MappingFunctions;
import com.janssengroup.XMLobjects.MultiOutbound.*;
import nl.copernicus.niklas.transformer.*;
import nl.copernicus.niklas.transformer.context.ComponentContext;
import nl.copernicus.niklas.transformer.context.EngineContext;
import nl.copernicus.niklas.transformer.context.NiklasLogger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class so_outbound implements NiklasComponent <byte[],byte[]>, NiklasLoggerAware, ComponentContextAware, EngineContextAware {

    protected NiklasLogger log;
    private ComponentContext cc;
    private EngineContext ec;

    private final MappingFunctions mf = new MappingFunctions();

    private final long relationNumber = 10012157;
    private final String searchname = "BAAHAA";

    @Override
    public byte[] process(Header header, byte[] payload) throws NiklasComponentException {

        SalesOrders so_file = mf.byteArrayToObject_JSON(payload, SalesOrders.class);

        MultiTypeOutbound outbounds = mapFile(so_file);

        return mf.objectToByteArray_XML(outbounds);
    }

    private MultiTypeOutbound mapFile(SalesOrders so_file) throws NiklasComponentException {
        MultiTypeOutbound outbounds = new MultiTypeOutbound();

        for (SalesorderDataItem so : so_file.getSalesorderData()) {

            LinesItem header = new LinesItem();
            List<LinesItem> lines = new ArrayList<>();

            for (LinesItem line : so.getLines()) {
                if (line.getMAINLINE().equals("*")) {
                    header = line;
                } else {
                    lines.add(line);
                }
            }

            //todo: Wat moest ik doen met SHIP_METHOD ?

            OutboundType outbound = new OutboundType();
            outbound.setEdiCustomerNumber(relationNumber);
            outbound.setEdiCustomerDepartment("klic");
            outbound.setEdiParm1(5);
            outbound.setEdiParm2("u");
            outbound.setEdiParm3("d");
            outbound.setTransmitter(searchname);
            outbound.setReceiver("SERVEN");
            outbound.setEdiReference(so.getDOCNUM());
            outbound.setEdiFunction1("9");
            outbound.setEdiCustomerSearchName(searchname);

            OrderType order = new OrderType();
            order.setLoadingDate(mf.now_XmlGregCal());
            order.setUnloadingDate(mf.now_XmlGregCal());
            order.setPrimaryReference(so.getDOCNUM());

            order.getAddress().addAll(mapAddresses(header));

            order.getArticleLine().addAll(mapArticles(lines));

            ExtraReferenceType ref = new ExtraReferenceType();
            ref.setReferenceCode(61);
            ref.setReferenceText(header.getInternalId());
            order.getExtraReference().add(ref);

            outbound.setOrder(order);

            outbounds.getOutbound().add(outbound);
        }

        return outbounds;
    }

    private List<AddressType> mapAddresses(LinesItem header) throws NiklasComponentException {
        List<AddressType> addresses = new ArrayList<>();

        TimeFramesType t = new TimeFramesType();
        t.setFromTime(mf.StringToXMLGregorian("08:00", "HH:mm"));
        t.setTillTime(mf.StringToXMLGregorian("17:00", "HH:mm"));

        AddressType a0 = new AddressType();
        AddressType a1 = new AddressType();
        AddressType a2 = new AddressType();
        AddressType a3 = new AddressType();
        AddressType a4 = new AddressType();

        a0.setAddressType(0);
        a1.setAddressType(1);
        a2.setAddressType(2);
        a3.setAddressType(3);
        a4.setAddressType(4);


        a0.setRelationNumber(relationNumber);
        a0.setSearchName(searchname);

        a1.setRelationNumber(10003496L);
        a1.setSearchName("SERVEN");
        a1.getTimeFrames().add(t);

        a2.setRelationNumber(relationNumber);
        a2.setSearchName(searchname);

        AddressDetailsType d = new AddressDetailsType();
        d.setNameLine1(header.getSHIPADDRESSEE());
        d.setAddressLine1(header.getSHIPADDRESS1());
        d.setAddressLine2(header.getSHIPADDRESS2());
        d.setAddressLine3(header.getSHIPADDRESS3());
        d.setCityName(header.getSHIPADDRESSCITY());
        d.setPostalCode(header.getSHIPADDRESSZIP());
        d.setCountryCode(header.getSHIPADDRESSCOUNTRY());

        String s = mf.CreateAddressSearchname(searchname, d.getNameLine1(), d.getAddressLine1(), d.getPostalCode(), d.getCountryCode());

        a3.setSearchName(s);
        a3.setAddressDetails(d);
        a3.getTimeFrames().add(t);

        a4.setSearchName(s);
        a4.setAddressDetails(d);

        addresses.add(a0);
        addresses.add(a1);
        addresses.add(a2);
        addresses.add(a3);
        addresses.add(a4);

        return addresses;
    }

    private List<ArticleLineType> mapArticles(List<LinesItem> items) {
        List<ArticleLineType> lines = new ArrayList<>();

        for (LinesItem item : items) {
            ArticleLineType line = new ArticleLineType();
            line.setArticleCode(item.getITEM());
            line.setQuantity(BigDecimal.valueOf(Double.parseDouble(item.getORDERQTY().isEmpty() ? "0.0" : item.getORDERQTY())));

            /*
            ArticlelineDescriptionType text = new ArticlelineDescriptionType();
            text.setArticleLineText("DESCR:" + item.getITEMDESC().trim());
            line.getArticlelineDescription().add(text);
            */

            ArticlelineDescriptionType rate = new ArticlelineDescriptionType();
            //rate.setArticleLineText("RATE:" + item.getRate());
            rate.setArticleLineText(item.getRate());
            line.getArticlelineDescription().add(rate);

            lines.add(line);
        }

        return lines;
    }

    @Override
    public void setLogger(NiklasLogger nl) {
        this.log = nl;
    }

    @Override
    public void setComponentContext(ComponentContext componentContext) {
        this.cc = componentContext;
    }

    @Override
    public void setEngineContext(EngineContext engineContext) { this.ec = engineContext; }
}

