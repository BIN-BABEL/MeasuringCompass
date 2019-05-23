package ndori.measuringcompass.client;

import ndori.measuringcompass.MeasuringCompass;
import ndori.measuringcompass.client.gui.GuiMeasure;
import ndori.measuringcompass.util.BoundingBox;
import ndori.measuringcompass.util.WorldCoordinate;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class Measure {

    private static int mode = 1;

    private static BlockPos cornerBlock;
    private static WorldCoordinate selectedBlock;

    public static void openGui() {
        Minecraft.getMinecraft().displayGuiScreen(new GuiMeasure());
    }

    public static void measure(EntityPlayer player, World worldIn, BlockPos pos) {
        if (mode == 1) {
            selectedBlock = new WorldCoordinate(pos, player.dimension);
            cornerBlock = pos;
            mode = 2;

            // Start highlighting first corner and draw distance text on HUD
            MeasuringCompass.instance.clientInfo.addBox(new BoundingBox(pos).setDimension(player.dimension));

        } else if (mode == 2) {

            if (player.dimension == selectedBlock.getDimension()) {
                if (selectedBlock != null) {
                    MeasuringCompass.instance.clientInfo.removeLast();
                }
                if (selectedBlock.getBlockPos().equals(pos)) {
                    clearSelections();
                } else {
                    if (cornerBlock == null) {
                        clearSelections();
                    } else {
                        BoundingBox box = new BoundingBox(pos, cornerBlock).expand(1, 1, 1).setDimension(player.dimension);

                        MeasuringCompass.instance.clientInfo.addBox(box);

                        mode = 1;
                        cornerBlock = null;
                        player.sendStatusMessage(new TextComponentString("X:" + box.getLengthX() + " Z:" + box.getLengthZ() + " Y:" + box.getLengthY() + " Blocks:" + box.getVolume()), false);
                    }
                }
            }
            mode = 1;
            selectedBlock = null;
        }
    }

    static void clearSelections() {
        cornerBlock = null;
        selectedBlock = null;
        mode = 1;
    }

    public static WorldCoordinate getSelectedBlock() {
        return selectedBlock;
    }
}
