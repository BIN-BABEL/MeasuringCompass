package ndori.measuringcompass.client;

import ndori.measuringcompass.ModConfig;
import ndori.measuringcompass.client.gui.GuiOverlay;
import ndori.measuringcompass.util.EventHandler;
import ndori.measuringcompass.util.RenderingHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mod.EventBusSubscriber
public class ClientProxy {

    public static Configuration config;

    public void preInit(FMLPreInitializationEvent event) {
        File dir = event.getModConfigurationDirectory();
        config = new Configuration(new File(dir.getPath(), "measuringcompass.cfg"));
        ModConfig.readConfig();
    }

    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new RenderingHandler());
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        MinecraftForge.EVENT_BUS.register(new GuiOverlay());
    }

    public void postInit(FMLPostInitializationEvent event) {
        if (config.hasChanged()) config.save();
    }
}
