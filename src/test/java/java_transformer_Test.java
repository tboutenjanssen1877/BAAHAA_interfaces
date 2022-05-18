import mappers.items;
import mappers.po_inbound;
import mappers.so_outbound;
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


    public void testProcess(String filename, String prefix) throws NiklasComponentException, IOException, NiklasInterruptionException {
        MockupNiklasProperties props = new MockupNiklasProperties();
        hdr = new MockupHeader(props.getProperties(), MockupHeader.DEFAULT_ENCODING);

        File file = new File("src/test/resources/input/" + filename);
        incoming transformerInstance = getTransformerInstance(incoming.class);
        hdr.setProperty("NIKLAS_FILE_NAME", prefix + "_" +file.getName());
        System.out.println(new String(transformerInstance.process(hdr, FileUtils.readFileToByteArray(file))));
        super.destroy(transformerInstance);
    }


    @Test(expected = NiklasInterruptionException.class)
    public void testProcess_Items() throws NiklasComponentException, IOException, NiklasInterruptionException {
        testProcess("Items.json", "");
    }

    @Test
    public void testProcess_PO() throws NiklasComponentException, IOException, NiklasInterruptionException {
        testProcess("PO.json", "purchaseorder");
    }

    @Test
    public void testProcess_SO() throws NiklasComponentException, IOException, NiklasInterruptionException {
        testProcess("SO.json", "salesorder");
    }
}