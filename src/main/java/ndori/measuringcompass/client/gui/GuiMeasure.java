package ndori.measuringcompass.client.gui;

import ndori.measuringcompass.MeasuringCompass;
import ndori.measuringcompass.util.handler.RenderingHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class GuiMeasure extends GuiScreen {

    private GuiButton buttonClear;
    private GuiButton buttonMode;
    private GuiButton buttonApply;
    private GuiButton buttonDeleteLast;
    private GuiButton buttonClose;

    private GuiTextField a;
    private GuiTextField r;
    private GuiTextField g;
    private GuiTextField b;

    private List<GuiTextField> colorList = new LinkedList<>();
    private List<GuiButtonColored> presetList = new LinkedList<>();

    @Override
    public void initGui() {
        super.initGui();

        ItemStack heldItem = mc.player.getHeldItem(EnumHand.MAIN_HAND);
        if (heldItem.isEmpty()) return;

        this.labelList.clear();

        GuiButtonColored bPre1 = this.addButton(new GuiButtonColored(elementId.P1.ordinal(), this.width / 2 - 10, this.height / 2 - 100, 20, 20, "", 0xFFFF5555));
        GuiButtonColored bPre2 = this.addButton(new GuiButtonColored(elementId.P2.ordinal(), this.width / 2 + 15, this.height / 2 - 100, 20, 20, "", 0xFFFFAA00));
        GuiButtonColored bPre3 = this.addButton(new GuiButtonColored(elementId.P3.ordinal(), this.width / 2 + 40, this.height / 2 - 100, 20, 20, "", 0xFFFFFF55));
        GuiButtonColored bPre4 = this.addButton(new GuiButtonColored(elementId.P4.ordinal(), this.width / 2 - 10, this.height / 2 - 75, 20, 20, "", 0xFF55FF55));
        GuiButtonColored bPre5 = this.addButton(new GuiButtonColored(elementId.P5.ordinal(), this.width / 2 + 15, this.height / 2 - 75, 20, 20, "", 0xFF55FFFF));
        GuiButtonColored bPre6 = this.addButton(new GuiButtonColored(elementId.P6.ordinal(), this.width / 2 + 40, this.height / 2 - 75, 20, 20, "", 0xFF00AAAA));
        GuiButtonColored bPre7 = this.addButton(new GuiButtonColored(elementId.P7.ordinal(), this.width / 2 - 10, this.height / 2 - 50, 20, 20, "", 0xFF5555FF));
        GuiButtonColored bPre8 = this.addButton(new GuiButtonColored(elementId.P8.ordinal(), this.width / 2 + 15, this.height / 2 - 50, 20, 20, "", 0xFFFF55FF));
        GuiButtonColored bPre9 = this.addButton(new GuiButtonColored(elementId.P9.ordinal(), this.width / 2 + 40, this.height / 2 - 50, 20, 20, "", 0xFFAAAAAA));

        presetList.add(bPre1);
        presetList.add(bPre2);
        presetList.add(bPre3);
        presetList.add(bPre4);
        presetList.add(bPre5);
        presetList.add(bPre6);
        presetList.add(bPre7);
        presetList.add(bPre8);
        presetList.add(bPre9);

        buttonClear = this.addButton(new GuiButton(elementId.CLEAR.ordinal(), this.width / 2 - 100, this.height * 3 / 4, 95, 20, "Delete All"));
        buttonApply = this.addButton(new GuiButton(elementId.APPLY.ordinal(), this.width / 2 - 82, this.height / 2 + 20, 50, 20, "Apply"));
        buttonDeleteLast = this.addButton(new GuiButton(elementId.DELETE.ordinal(), this.width / 2 + 5, this.height * 3 / 4, 95, 20, "Delete Previous"));
        buttonClose = this.addButton(new GuiButton(elementId.CLOSE.ordinal(), this.width / 2 + 5, this.height * 3 / 4 + 30, 95, 20, "Close"));

        String mode = (RenderingHandler.doFill) ? "Mode: Filled" : "Mode: Outline";
        buttonMode = this.addButton(new GuiButton(elementId.MODE.ordinal(), this.width / 2 - 100, this.height * 3 / 4 + 30, 95, 20, mode));

        a = new GuiTextField(elementId.ALPHA.ordinal(), this.fontRenderer, this.width / 2 - 82, this.height / 2 - 10, 50, 20);
        r = new GuiTextField(elementId.RED.ordinal(), this.fontRenderer, this.width / 2 - 82, this.height / 2 - 100, 50, 20);
        g = new GuiTextField(elementId.GREEN.ordinal(), this.fontRenderer, this.width / 2 - 82, this.height / 2 - 70, 50, 20);
        b = new GuiTextField(elementId.BLUE.ordinal(), this.fontRenderer, this.width / 2 - 82, this.height / 2 - 40, 50, 20);

        colorList.add(r);
        colorList.add(g);
        colorList.add(b);
        colorList.add(a);

        int[] colors = MeasuringCompass.instance.clientInfo.getColors();
        a.setText(String.valueOf(colors[0]));
        r.setText(String.valueOf(colors[1]));
        g.setText(String.valueOf(colors[2]));
        b.setText(String.valueOf(colors[3]));

        a.setMaxStringLength(3);
        r.setMaxStringLength(3);
        g.setMaxStringLength(3);
        b.setMaxStringLength(3);

        GuiLabel labelAlpha = new GuiLabel(this.fontRenderer, elementId.A.ordinal(), this.width / 2 - 100, this.height / 2 - 10, 20, 20, 0xFFFFFF);
        GuiLabel labelRed = new GuiLabel(this.fontRenderer, elementId.R.ordinal(), this.width / 2 - 100, this.height / 2 - 100, 20, 20, 0xFFFFFF);
        GuiLabel labelGreen = new GuiLabel(this.fontRenderer, elementId.G.ordinal(), this.width / 2 - 100, this.height / 2 - 70, 20, 20, 0xFFFFFF);
        GuiLabel labelBlue = new GuiLabel(this.fontRenderer, elementId.B.ordinal(), this.width / 2 - 100, this.height / 2 - 40, 20, 20, 0xFFFFFF);

        labelAlpha.addLine("A :");
        labelRed.addLine("R :");
        labelGreen.addLine("G :");
        labelBlue.addLine("B :");

        this.labelList.add(labelRed);
        this.labelList.add(labelGreen);
        this.labelList.add(labelBlue);
        this.labelList.add(labelAlpha);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button == buttonClear) {
            MeasuringCompass.instance.clientInfo.clearAll();
        } else if (button == buttonMode) {
            RenderingHandler.doFill = !RenderingHandler.doFill;
            button.displayString = (RenderingHandler.doFill) ? "Mode: Filled" : "Mode: Outline";
        } else if (button == buttonApply) {
            for (GuiTextField field : colorList) {
                applyColor(field);
            }
        } else if (button == buttonDeleteLast) {
            MeasuringCompass.instance.clientInfo.clearLast();
        } else if (button instanceof GuiButtonColored) {
            ((GuiButtonColored) button).setSelected(true);
            deselectButtonColored((GuiButtonColored) button);
            for (GuiTextField field : colorList) {
                applyColor(field, (GuiButtonColored) button);
            }
            mc.player.closeScreen();
        } else if (button == buttonClose) {
            mc.player.closeScreen();
        }
    }

    private void applyColor(GuiTextField field) {
        if (field.getId() == elementId.ALPHA.ordinal()) MeasuringCompass.instance.clientInfo.setAlpha(checkInput(field));
        else if (field.getId() == elementId.RED.ordinal()) MeasuringCompass.instance.clientInfo.setRed(checkInput(field));
        else if (field.getId() == elementId.GREEN.ordinal()) MeasuringCompass.instance.clientInfo.setGreen(checkInput(field));
        else if (field.getId() == elementId.BLUE.ordinal()) MeasuringCompass.instance.clientInfo.setBlue(checkInput(field));
    }

    private void applyColor(GuiTextField field, GuiButtonColored button) {
        if (field.getId() == elementId.ALPHA.ordinal()) {
            field.setText(String.valueOf(button.a));
            MeasuringCompass.instance.clientInfo.setAlpha(button.a);
        } else if (field.getId() == elementId.RED.ordinal()) {
            field.setText(String.valueOf(button.r));
            MeasuringCompass.instance.clientInfo.setRed(button.r);
        } else if (field.getId() == elementId.GREEN.ordinal()) {
            field.setText(String.valueOf(button.g));
            MeasuringCompass.instance.clientInfo.setGreen(button.g);
        } else if (field.getId() == elementId.BLUE.ordinal()) {
            field.setText(String.valueOf(button.b));
            MeasuringCompass.instance.clientInfo.setBlue(button.b);
        }
    }

    private void deselectButtonColored(GuiButtonColored button) {
        for (GuiButtonColored button2 : presetList) {
            if (!button.equals(button2)) button2.setSelected(false);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        a.drawTextBox();
        r.drawTextBox();
        g.drawTextBox();
        b.drawTextBox();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1) {
            this.mc.displayGuiScreen(null);

            if (this.mc.currentScreen == null) {
                this.mc.setIngameFocus();
            }
            return;
        }

        for (GuiTextField field : colorList) {
            if (field.isFocused()) {
                field.textboxKeyTyped(typedChar, keyCode);
                checkInput(field);
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (GuiTextField field : colorList) {
            if (field.mouseClicked(mouseX, mouseY, mouseButton)) {
                field.setFocused(true);
            } else {
                field.setFocused(false);
            }
            checkInput(field);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private int checkInput(GuiTextField field) {
        if (field.getText().isEmpty()) return 0;

        StringBuilder stringBuilder = new StringBuilder();
        for (char c : field.getText().toCharArray()) {
            if (Character.isDigit(c)) stringBuilder.append(c);
        }

        int value = (stringBuilder.length() > 0) ? Integer.valueOf(stringBuilder.toString()) : 0;

        if (value > 255) value = 255;
        field.setText(String.valueOf(value));
        return value;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    enum elementId {
        CLOSE,
        CLEAR,
        MODE,
        APPLY,
        ALPHA,
        RED,
        GREEN,
        BLUE,
        A,
        R,
        G,
        B,
        DELETE,
        P1,
        P2,
        P3,
        P4,
        P5,
        P6,
        P7,
        P8,
        P9
    }
}
