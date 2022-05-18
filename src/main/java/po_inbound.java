import Structures.PurchaseOrders.LinesItem;
import Structures.PurchaseOrders.PurchaseOrders;
import Structures.PurchaseOrders.PurchaseorderDataItem;
import com.janssengroup.FunctionLibrary.MappingFunctions;
import com.janssengroup.XMLobjects.MultiInbound.*;
import nl.copernicus.niklas.transformer.*;
import nl.copernicus.niklas.transformer.context.ComponentContext;
import nl.copernicus.niklas.transformer.context.EngineContext;
import nl.copernicus.niklas.transformer.context.NiklasLogger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class po_inbound implements NiklasComponent <byte[],byte[]>, NiklasLoggerAware, ComponentContextAware, EngineContextAware {

    protected NiklasLogger log;
    private ComponentContext cc;
    private EngineContext ec;

    private final MappingFunctions mf = new MappingFunctions();

    private final long relationNumber = 10012157;
    private final String searchname = "BAAHAA";

    @Override
    public byte[] process(Header header, byte[] payload) throws NiklasComponentException {

        PurchaseOrders po_file = mf.byteArrayToObject_JSON(payload, PurchaseOrders.class);

        MultiTypeInbound inbounds = mapFile(po_file);

        return mf.objectToByteArray_XML(inbounds);
    }

    private MultiTypeInbound mapFile(PurchaseOrders po_file) throws NiklasComponentException {
        MultiTypeInbound inbounds = new MultiTypeInbound();

        for (PurchaseorderDataItem po : po_file.getPurchaseorderData()) {

            LinesItem header = new LinesItem();
            List<LinesItem> lines = new ArrayList<>();

            for (LinesItem line : po.getLines()) {
                if (line.getMAINLINE().equals("*")) {
                    header = line;
                } else {
                    lines.add(line);
                }
            }

            //todo: Wat moest ik doen met SHIP_METHOD ?

            InboundType inbound = new InboundType();
            inbound.setEdiCustomerNumber(relationNumber);
            inbound.setEdiCustomerDepartment("klic");
            inbound.setEdiParm1(5);
            inbound.setEdiParm2("i");
            inbound.setEdiParm3("d");
            inbound.setTransmitter(searchname);
            inbound.setReceiver("SERVEN");
            inbound.setEdiReference(po.getDOCNUM());
            inbound.setEdiFunction1("9");
            inbound.setEdiCustomerSearchName(searchname);

            OrderType order = new OrderType();
            order.setLoadingDate(mf.now_XmlGregCal());
            order.setUnloadingDate(mf.StringToXMLGregorian(header.getDATE(), "MM/dd/yyyy"));
            order.setPrimaryReference(po.getDOCNUM());

            order.getAddress().addAll(mapAddresses(header));

            //todo: Wat moet ik doen met AMOUNT? Het lijkt een getal met 3 decimalen achter de komma.

            order.getArticleLine().addAll(mapArticles(lines));

            inbound.setOrder(order);

            inbounds.getInbound().add(inbound);
        }

        return inbounds;
    }

    private List<AddressType> mapAddresses(LinesItem header) throws NiklasComponentException {
        List<AddressType> addresses = new ArrayList<>();

        TimeFramesType t = new TimeFramesType();
        t.setFromTime(mf.StringToXMLGregorian("08:00", "HH:mm"));
        t.setTillTime(mf.StringToXMLGregorian("17:00", "HH:mm"));

        AddressDetailsType d = new AddressDetailsType();
        d.setNameLine1(header.getVENDOR());
        d.setAddressLine1(header.getVENDORADDRESS1());
        d.setAddressLine2(header.getVENDORADDRESS2());
        d.setAddressLine3(header.getVENDORADDRESS3());
        d.setCityName(header.getVENDORADDRESSCITY());
        d.setPostalCode(header.getVENDORADDRESSZIP());
        d.setCountryCode(header.getVENDORADDRESSCOUNTRY()); //fixme: Country opvragen in ISO2 code, ipv Landnaam

        String s = mf.CreateAddressSearchname(searchname, d.getNameLine1(), d.getAddressLine1(), d.getPostalCode(), d.getCountryCode());


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

        a1.setSearchName(s);
        a1.setAddressDetails(d);
        a1.getTimeFrames().add(t);

        a2.setRelationNumber(relationNumber);
        a2.setSearchName(searchname);

        a3.setRelationNumber(10003496L);
        a3.setSearchName("SERVEN");
        a3.getTimeFrames().add(t);

        a4.setRelationNumber(10003496L);
        a4.setSearchName("SERVEN");

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
            line.setQuantity(BigDecimal.valueOf(Double.parseDouble(item.getORDERQTY())));

            ArticleLineFreeTextType text = new ArticleLineFreeTextType();
            text.setArticleLineText(item.getITEMDESC().trim());
            line.getArticleLineFreeText().add(text);

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

