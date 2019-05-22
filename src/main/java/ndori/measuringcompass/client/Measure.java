package ndori.measuringcompass.client;

import ndori.measuringcompass.client.gui.GuiMeasure;
import ndori.measuringcompass.util.BoundingBox;
import ndori.measuringcompass.util.Logging;
import ndori.measuringcompass.util.RenderingHandler;
import ndori.measuringcompass.util.WorldCoordinate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.HashMap;

public class Measure {

    private static final int MODE_C1 = 1;
    private static final int MODE_C2 = 2;

    private static final boolean debugMode = false;

    private static HashMap<String, Integer> coordData;

    public Measure() {
        coordData = new HashMap<>();
    }

    public void openGui() {
        ClientInfo.mc.displayGuiScreen(new GuiMeasure());
    }

    private void debug(EntityPlayer player, World worldIn, BlockPos pos) {
        Logging.message(player, TextFormatting.YELLOW + "Block: " + pos.toString());
        Logging.message(player, "Player: " + player.getPosition().toString());
    }

    public void measure(EntityPlayer player, World worldIn, BlockPos pos) {
        RenderingHandler renderingHandler = RenderingHandler.INSTANCE;

        int mode = getMode();

        if (mode == MODE_C1) {
            setCurrentBlock(new WorldCoordinate(pos, worldIn.provider.getDimension()));
            setMode(MODE_C2);
            setCorner1(pos);
            // Start highlighting first corner and draw distance text on HUD
            ClientInfo.addBox(new BoundingBox(pos).setDim(player.dimension));
            renderingHandler.isC1Selected = true;
            ClientInfo.c1Coordinate = getCurrentBlock();

        } else if (mode == MODE_C2) {

            if (debugMode) {
                debug(player, worldIn, pos);
                return;
            }

            WorldCoordinate currentBlock = getCurrentBlock();
            // Stop highlighting first corner
            if (renderingHandler.isC1Selected) {
                ClientInfo.removeLast();
                renderingHandler.isC1Selected = false;
            }
            if (currentBlock.getCoordinate().equals(pos)) {
                coordData.clear();
            } else {
                BlockPos c1 = getCorner1();

                if (c1 == null) {
                    coordData.clear();
                } else {
                    BoundingBox box = new BoundingBox(pos, c1).expand(1, 1, 1).setDim(player.dimension);

                    ClientInfo.addBox(box);

                    setMode(MODE_C1);
                    setCorner1(null);
                    Logging.message(player, "X:" + box.getxLen() + " Z:" + box.getzLen() + " Y:" + box.getyLen() + " Blocks:" + box.getVolume());
                }
            }
        }
    }

    public static void clearCoordData() {
        coordData.clear();
    }

    private static void setCorner1(BlockPos pos) {
        if (pos == null) {
            coordData.remove("c1x");
            coordData.remove("c1y");
            coordData.remove("c1z");
        } else {
            coordData.put("c1x", pos.getX());
            coordData.put("c1y", pos.getY());
            coordData.put("c1z", pos.getZ());
        }
    }

    private static BlockPos getCorner1() {
        if (!coordData.containsKey("c1x")) {
            return null;
        }
        return new BlockPos(coordData.get("c1x"), coordData.get("c1y"), coordData.get("c1z"));
    }

    private static void setCurrentBlock(WorldCoordinate coord) {
        if (coord == null) {
            coordData.remove("selectedX");
            coordData.remove("selectedY");
            coordData.remove("selectedZ");
            coordData.remove("selectedD");
        } else {
            coordData.put("selectedX", coord.getCoordinate().getX());
            coordData.put("selectedY", coord.getCoordinate().getY());
            coordData.put("selectedZ", coord.getCoordinate().getZ());
            coordData.put("selectedD", coord.getDimension());
        }
    }

    private static WorldCoordinate getCurrentBlock() {
        if (coordData.containsKey("selectedX")) {
            int x = coordData.get("selectedX");
            int y = coordData.get("selectedY");
            int z = coordData.get("selectedZ");
            int d = coordData.get("selectedD");
            return new WorldCoordinate(new BlockPos(x, y, z), d);
        }
        return null;
    }

    private static int getMode() {
        if (coordData.containsKey("mode")) {
            return coordData.get("mode");
        }
        return MODE_C1;
    }

    private static void setMode(int mode) {
        coordData.put("mode", mode);
    }
}
