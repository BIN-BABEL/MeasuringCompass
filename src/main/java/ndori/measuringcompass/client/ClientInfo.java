package ndori.measuringcompass.client;

import ndori.measuringcompass.ModConfig;
import ndori.measuringcompass.util.BoundingBox;
import ndori.measuringcompass.util.Logging;
import ndori.measuringcompass.util.WorldCoordinate;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.LinkedList;
import java.util.List;

public class ClientInfo {

    public static Minecraft mc = Minecraft.getMinecraft();

    public static Measure measurer = new Measure();

    public static boolean doFill = false;

    public static WorldCoordinate c1Coordinate;

    public static int currR = 255;
    public static int currG = 255;
    public static int currB = 255;
    public static int currA = 255;

    public static ItemStack measurerItem = GameRegistry.makeItemStack(ModConfig.itemRegistryName, 0, 1, null);

    private static List<BoundingBox> boxList = new LinkedList<>();

    public static void addBox(BoundingBox aabb) {
        aabb.r = currR;
        aabb.g = currG;
        aabb.b = currB;
        aabb.a = currA;

        if (!boxList.isEmpty() && boxList.contains(aabb)) {
            Logging.message(mc.player, TextFormatting.RED + "Specified box already exists.");
            return;
        }

        boxList.add(aabb);
    }

    public static void removeLast() {
        if (boxList.isEmpty()) return;
        boxList.remove(boxList.size() - 1);
    }

    public static List<BoundingBox> getBoxList() {
        return boxList;
    }

    public static void clearBoxes() {
        boxList.clear();
    }
}
