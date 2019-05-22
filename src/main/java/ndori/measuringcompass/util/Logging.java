package ndori.measuringcompass.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

public class Logging {
    private static Logging instance;
    private Logger logger;

    private Logging() {
        logger = LogManager.getLogger();
        instance = this;
    }

    public static void message(@Nonnull EntityPlayer player, String msg) {
        player.sendStatusMessage(new TextComponentString(msg), false);
    }
}
