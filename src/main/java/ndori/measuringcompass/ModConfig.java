package ndori.measuringcompass;

import ndori.measuringcompass.client.ClientProxy;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

@Config(modid = MeasureMod.MODID)
public class ModConfig {

    @Config.Comment("Specify an item to use as a measuring tool (e.g., naturesaura:infused_iron_pickaxe)")
    public static String itemRegistryName = "minecraft:compass";

    public static void readConfig() {
        Configuration config = ClientProxy.config;
        try {
            config.load();
            initConfig(config);
        } catch (Exception e) {
            MeasureMod.logger.log(Level.ERROR, "There was a problem while loading the config file.", e);
        } finally {
            if (config.hasChanged()) config.save();
        }
    }

    private static void initConfig(Configuration config) {
        config.addCustomCategoryComment("general", "");
        itemRegistryName = config.getString("itemRegistryName", "general", itemRegistryName, "");
    }
}
