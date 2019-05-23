package ndori.measuringcompass.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiButtonColored extends GuiButton {

    private int color;
    int a;
    int r;
    int g;
    int b;

    private boolean isSelected;

    GuiButtonColored(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, int color) {
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

    void setSelected(boolean selected) {
        isSelected = selected;
    }

    private void separateColors() {
        this.a = color >> 24 & 255;
        this.r = color >> 16 & 255;
        this.g = color >> 8 & 255;
        this.b = color & 255;
    }
}
