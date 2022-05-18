import Structures.Items.ItemDataItem;
import Structures.Items.Items;
import Structures.PurchaseOrders.LinesItem;
import Structures.PurchaseOrders.PurchaseOrders;
import Structures.PurchaseOrders.PurchaseorderDataItem;
import com.janssengroup.FunctionLibrary.MappingFunctions;
import com.janssengroup.XMLobjects.MultiArticles.*;
import com.janssengroup.XMLobjects.MultiInbound.*;
import nl.copernicus.niklas.transformer.*;
import nl.copernicus.niklas.transformer.context.ComponentContext;
import nl.copernicus.niklas.transformer.context.EngineContext;
import nl.copernicus.niklas.transformer.context.NiklasLogger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class items implements NiklasComponent <byte[],byte[]>, NiklasLoggerAware, ComponentContextAware, EngineContextAware {

    protected NiklasLogger log;
    private ComponentContext cc;
    private EngineContext ec;

    private final MappingFunctions mf = new MappingFunctions();

    private final long relationNumber = 10012157;
    private final String searchname = "BAAHAA";

    @Override
    public byte[] process(Header header, byte[] payload) throws NiklasComponentException {

        Items items_file = mf.byteArrayToObject_JSON(payload, Items.class);

        MultiTypeArticles articles = mapFile(items_file);

        return mf.objectToByteArray_XML(articles);
    }

    private MultiTypeArticles mapFile(Items items_file) {
        MultiTypeArticles articles = new MultiTypeArticles();

        for (ItemDataItem item : items_file.getItemData()) {

            //todo: Wat moet ik met de velden MFG, MFG_COUNTRY, SHELF_LIFE_DAYS, IS_BATCH, ITEM_EXPIRES, STOCK_LEVEL ?

            ArticlesType articleFile = new ArticlesType();
            articleFile.setEdiCustomerNumber(relationNumber);
            articleFile.setEdiCustomerDepartment("klmd");
            articleFile.setEdiParm1(5);
            articleFile.setEdiParm3("a");
            articleFile.setEdiReference(searchname + "-" + item.getNSINTERNALID());
            articleFile.setEdiFunction1("9");
            articleFile.setEdiCustomerSearchName(searchname);

            ArticleType article = new ArticleType();
            article.setArticleCode(item.getNSINTERNALID());
            article.setSearchNameArticle(item.getSKU().toUpperCase());
            article.setInternalDescription(item.getCOMMODITYDESC());
            article.setCommodityCode("dive");
            article.setArticleGroup(1);
            article.setEanNumber(Long.valueOf(item.getUPC()));
            article.setNettoWeight(parseBD(item.getITEMWEIGHTKG()));

            StockRegistrationType stock = new StockRegistrationType();
            stock.setCodeLot(2);
            stock.setCodeAutomaticLotDate(1);
            stock.setCodeSerialNumbers(0);
            article.setStockRegistration(stock);

            StockLevelControlType level = new StockLevelControlType();
            level.setStockUnit("st");
            level.setReceiptPackageCode("st");
            level.setOutboundUnit("st");
            level.setDespatchPackageCode("st");
            article.setStockLevelControl(level);

            BranchSpecificsType branch = new BranchSpecificsType();
            branch.setBranchCode(1);
            branch.setDespatchPackageCode("st");
            branch.setAbcClassification("C");
            branch.setRFSwitchLot("0");
            branch.setRFSwitchPallet("0");

            ReplenishmentType replenishment = new ReplenishmentType();
            replenishment.setQuantityToTransfer(BigDecimal.ONE);
            replenishment.setPackageCode("eu");
            branch.setReplenishment(replenishment);
            article.getBranchSpecifics().add(branch);

            PackagePatternType sku = new PackagePatternType();
            sku.setPackageCode("st");
            sku.setNumberPerUnit(1);
            sku.setGrossWeightPerUnit(parseBD(item.getITEMWEIGHTKG()));
            sku.setLength(parseBD(item.getITEMLENGTHCM()));
            sku.setWidth(parseBD(item.getITEMWIDTHCM()));
            sku.setHeight(parseBD(item.getITEMHEIGHTCM()));
            sku.setCalculationPackageCode("eu");
            sku.setPalletRegistration("0");

            PackagePatternType eu = new PackagePatternType();
            eu.setPackageCode("eu");
            eu.setNumberPerUnit(1000);
            eu.setGrossWeightPerUnit(parseBD(item.getITEMWEIGHTKG(), 1000d));
            eu.setPalletRegistration("1");

            article.getPackagePattern().add(sku);
            article.getPackagePattern().add(eu);

            CustomGoodsCodeType customs = new CustomGoodsCodeType();
            customs.setCountryCode("NL");
            customs.setImportTaricCode(item.getHARMONIZEDCODE());
            customs.setExportTaricCode(item.getHARMONIZEDCODE());

            article.getCustomGoodsCode().add(customs);

            articleFile.getArticle().add(article);

            articles.getArticles().add(articleFile);
        }

        return articles;
    }

    private BigDecimal parseBD(String string) {
        return BigDecimal.valueOf(Double.parseDouble(string));
    }

    private BigDecimal parseBD(String string, Double multiplier) {
        return BigDecimal.valueOf(Double.parseDouble(string) * multiplier);
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

