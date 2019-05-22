package ndori.measuringcompass.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiButtonColored extends GuiButton {

    private int color;
    public int r;
    public int g;
    public int b;
    public int a;

    private boolean isSelected;

    public GuiButtonColored(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, int color) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.color = color;
        this.isSelected = false;
        separateColors();
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (isSelected) {
            drawRect(this.x - 1, this.y - 1, this.x + this.width + 1, this.y + this.height + 1, 0xFFFFFF55);
        }
        super.drawButton(mc, mouseX, mouseY, partialTicks);
        drawRect(this.x + 2, this.y + 2, this.x + this.width - 2, this.y + this.height - 2, this.color);
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private void separateColors() {
        this.r = color >> 16 & 255;
        this.g = color >> 8 & 255;
        this.b = color & 255;
        this.a = color >> 24 & 255;
    }
}
