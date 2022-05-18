import nl.copernicus.niklas.test.*;
import nl.copernicus.niklas.transformer.Header;
import nl.copernicus.niklas.transformer.NiklasComponentException;
import nl.copernicus.niklas.transformer.NiklasInterruptionException;
import nl.copernicus.niklas.transformer.context.EngineContext;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Thijs
 */
public class java_transformer_Test extends FunctionalTestCase {
    private Header hdr;
    private EngineContext ec;

    public java_transformer_Test() {
        HashMap<String, Object> ccParameters = new HashMap<>();
        ccParameters.put("connection_string", "niklas_solutions");
        ccParameters.put("MailReceiver_VAD", "vad@janssen1877.com");
        ccParameters.put("MailReceiver_Transport_Fotos", "boordcomputer@janssen1877.com");
        ccParameters.put("MailReceiver_Transport_Wagenpark", "wagenparkjds@janssen1877.com");

        MockupComponentContext cc = new MockupComponentContext();
        cc.getProperties().putAll(ccParameters);

        this.setComponentContext(cc);

        this.setRoutingContext(new MockupRoutingContext());

    }



    @Test
    public void testProcess_Items() throws NiklasComponentException, IOException {
        MockupNiklasProperties props = new MockupNiklasProperties();
        hdr = new MockupHeader(props.getProperties(), MockupHeader.DEFAULT_ENCODING);

        File file = new File("src/test/resources/input/Items.json");
        items transformerInstance = getTransformerInstance(items.class);
        hdr.setProperty("NIKLAS_FILE_NAME", file.getName());
        System.out.println(new String(transformerInstance.process(hdr, FileUtils.readFileToByteArray(file))));
        super.destroy(transformerInstance);
    }

    @Test
    public void testProcess_PO() throws NiklasComponentException, IOException {
        MockupNiklasProperties props = new MockupNiklasProperties();
        hdr = new MockupHeader(props.getProperties(), MockupHeader.DEFAULT_ENCODING);

        File file = new File("src/test/resources/input/PO.json");
        po_inbound transformerInstance = getTransformerInstance(po_inbound.class);
        hdr.setProperty("NIKLAS_FILE_NAME", file.getName());
        System.out.println(new String(transformerInstance.process(hdr, FileUtils.readFileToByteArray(file))));
        super.destroy(transformerInstance);
    }

    @Test
    public void testProcess_SO() throws NiklasComponentException, IOException {
        MockupNiklasProperties props = new MockupNiklasProperties();
        hdr = new MockupHeader(props.getProperties(), MockupHeader.DEFAULT_ENCODING);

        File file = new File("src/test/resources/input/SO.json");
        so_outbound transformerInstance = getTransformerInstance(so_outbound.class);
        hdr.setProperty("NIKLAS_FILE_NAME", file.getName());
        System.out.println(new String(transformerInstance.process(hdr, FileUtils.readFileToByteArray(file))));
        super.destroy(transformerInstance);
    }


    public void testFilter(String filename, String prefix) throws NiklasComponentException, IOException, NiklasInterruptionException {
        MockupNiklasProperties props = new MockupNiklasProperties();
        hdr = new MockupHeader(props.getProperties(), MockupHeader.DEFAULT_ENCODING);
        incoming_filter transformerInstance = getTransformerInstance(incoming_filter.class);

        File items = new File("src/test/resources/input/" + filename);
        hdr.setProperty("NIKLAS_FILE_NAME", prefix + "_" + items.getName());
        transformerInstance.process(hdr, FileUtils.readFileToByteArray(items));
        super.destroy(transformerInstance);
    }

    @Test(expected = NiklasInterruptionException.class)
    public void testFilter_Items() throws NiklasInterruptionException, NiklasComponentException, IOException {
        testFilter("Items.json", "unknown");
    }

    @Test(expected = NiklasInterruptionException.class)
    public void testFilter_PO() throws NiklasInterruptionException, NiklasComponentException, IOException {
        testFilter("PO.json", "purchaseorder");
    }

    @Test(expected = NiklasInterruptionException.class)
    public void testFilter_SO() throws NiklasInterruptionException, NiklasComponentException, IOException {
        testFilter("SO.json", "salesorder");
    }
}