import com.janssengroup.FunctionLibrary.MappingFunctions;
import nl.copernicus.niklas.NiklasProperties;
import nl.copernicus.niklas.transformer.*;
import nl.copernicus.niklas.transformer.context.ComponentContext;
import nl.copernicus.niklas.transformer.context.EngineContext;
import nl.copernicus.niklas.transformer.context.NiklasLogger;
import nl.copernicus.niklas.transformer.context.RoutingContext;


public class incoming_filter implements NiklasComponent <byte[],byte[]>, NiklasLoggerAware, ComponentContextAware, EngineContextAware, RoutingContextAware {

    protected NiklasLogger log;
    private ComponentContext cc;
    private EngineContext ec;
    private RoutingContext rc;

    @Override
    public byte[] process(Header header, byte[] payload) throws NiklasComponentException, NiklasInterruptionException {

        String fileName = header.getProperty(NiklasProperties.FILE_NAME);

        String docType;

        if (fileName.startsWith("purchaseorder_")) {
            docType = "po";
        } else if (fileName.startsWith("salesorder_")) {
            docType = "so";
        } else {
            throw new NiklasInterruptionException("filename not following the given rules");
        }

        Header hdr = header.cloneHeader();
        hdr.setProperty(NiklasProperties.DOC_TYPE, docType);

        try {
            rc.dispatchToAgreement(hdr, payload);
        } catch (RoutingException e) {
            throw new NiklasComponentException("Unable to dispatch to Agreement", e);
        }

        throw new NiklasInterruptionException("Successfull dispatched file");
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

    @Override
    public void setRoutingContext(RoutingContext routingContext) {
        this.rc = routingContext;
    }
}

