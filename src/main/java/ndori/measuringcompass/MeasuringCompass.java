package ndori.measuringcompass;

import ndori.measuringcompass.client.ClientInfo;
import ndori.measuringcompass.client.ClientProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = MeasuringCompass.MODID,
        name = MeasuringCompass.NAME,
        version = MeasuringCompass.VERSION,
        clientSideOnly = true,
        dependencies = "required-after:forge@[14.23.5.2768,)")
@Mod.EventBusSubscriber
public class MeasuringCompass {
    public static final String MODID = "measuringcompass";
    public static final String NAME = "Measuring Compass";
    public static final String VERSION = "1.1.1";

    public ClientInfo clientInfo = new ClientInfo();

    @Mod.Instance(MeasuringCompass.MODID)
    public static MeasuringCompass instance;

    public static Logger logger;

    @SidedProxy(clientSide = "ndori.measuringcompass.client.ClientProxy")
    public static ClientProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MeasuringCompass.logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
