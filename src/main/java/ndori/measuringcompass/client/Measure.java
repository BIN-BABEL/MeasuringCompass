package ndori.measuringcompass.client;

import ndori.measuringcompass.client.gui.GuiMeasure;
import ndori.measuringcompass.util.BoundingBox;
import ndori.measuringcompass.util.Logging;
import ndori.measuringcompass.util.WorldCoordinate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Measure {

    private static final int MODE_C1 = 1;
    private static final int MODE_C2 = 2;
    private static int mode = 1;

    private static BlockPos cornerBlock;
    private static WorldCoordinate selectedBlock;

    public static void openGui() {
        ClientInfo.mc.displayGuiScreen(new GuiMeasure());
    }

    public static void measure(EntityPlayer player, World worldIn, BlockPos pos) {
        if (mode == MODE_C1) {
            selectedBlock = new WorldCoordinate(pos, worldIn.provider.getDimension());
            cornerBlock = pos;
            mode = 2;

            // Start highlighting first corner and draw distance text on HUD
            ClientInfo.addBox(new BoundingBox(pos).setDimension(player.dimension));

        } else if (mode == MODE_C2) {

            // Stop highlighting first corner
            if (selectedBlock != null) {
                ClientInfo.removeLast();
            }
            if (selectedBlock.getBlockPos().equals(pos)) {
                clearSelections();
            } else {
                if (cornerBlock == null) {
                    clearSelections();
                } else {
                    BoundingBox box = new BoundingBox(pos, cornerBlock).expand(1, 1, 1).setDimension(player.dimension);

                    ClientInfo.addBox(box);

                    mode = 1;
                    cornerBlock = null;
                    Logging.message(player, "X:" + box.getLengthX() + " Z:" + box.getLengthZ() + " Y:" + box.getLengthY() + " Blocks:" + box.getVolume());
                }
            }
        }
    }

    public static void clearSelections() {
        cornerBlock = null;
        selectedBlock = null;
        mode = 1;
    }

    public static WorldCoordinate getSelectedBlock() {
        return selectedBlock;
    }
}
