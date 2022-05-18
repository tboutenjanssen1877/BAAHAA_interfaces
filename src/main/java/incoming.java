import com.janssengroup.FunctionLibrary.MappingFunctions;
import mappers.po_inbound;
import mappers.so_outbound;
import nl.copernicus.niklas.NiklasProperties;
import nl.copernicus.niklas.transformer.*;
import nl.copernicus.niklas.transformer.context.ComponentContext;
import nl.copernicus.niklas.transformer.context.EngineContext;
import nl.copernicus.niklas.transformer.context.NiklasLogger;


public class incoming implements NiklasComponent <byte[],byte[]>, NiklasLoggerAware, ComponentContextAware, EngineContextAware {

    protected NiklasLogger log;
    private ComponentContext cc;
    private EngineContext ec;

    private final MappingFunctions mf = new MappingFunctions();

    @Override
    public byte[] process(Header header, byte[] payload) throws NiklasComponentException, NiklasInterruptionException {

        String fileName = header.getProperty(NiklasProperties.FILE_NAME);

        if (fileName.startsWith("purchaseorder_")) {
            po_inbound mapper = new po_inbound();
            payload = mapper.process(header, payload);
        } else if (fileName.startsWith("salesorder_")) {
            so_outbound mapper = new so_outbound();
            payload = mapper.process(header, payload);
        } else {
            throw new NiklasInterruptionException("filename not following the given rules");
        }

        return payload;
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

