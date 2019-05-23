package ndori.measuringcompass.client;

import ndori.measuringcompass.ConfigSetup;
import ndori.measuringcompass.util.BoundingBox;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.LinkedList;
import java.util.List;

public class ClientInfo {

    private int alpha = 255;
    private int red = 255;
    private int green = 255;
    private int blue = 255;

    private ItemStack measurerItem = GameRegistry.makeItemStack(ConfigSetup.itemRegistryName, 0, 1, null);

    private List<BoundingBox> boxList = new LinkedList<>();

    void addBox(BoundingBox bb) {
        bb.a = alpha;
        bb.r = red;
        bb.g = green;
        bb.b = blue;

        if (!boxList.isEmpty() && boxList.contains(bb)) {
            Minecraft.getMinecraft().player.sendStatusMessage(new TextComponentString(TextFormatting.RED + "Specified box already exists."), false);
            return;
        }

        boxList.add(bb);
    }

    void removeLast() {
        if (boxList.isEmpty()) return;
        boxList.remove(boxList.size() - 1);
    }

    public List<BoundingBox> getBoxList() {
        return boxList;
    }

    public void clearAll() {
        boxList.clear();
        clearLast();
        Measure.clearSelections();
    }

    public void clearLast() {
        removeLast();
        Measure.clearSelections();
    }

    public int[] getColors() {
        return new int[]{alpha, red, green, blue};
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public ItemStack getMeasurerItem() {
        return measurerItem;
    }
}
