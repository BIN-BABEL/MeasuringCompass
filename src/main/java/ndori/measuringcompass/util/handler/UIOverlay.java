package ndori.measuringcompass.util.handler;

import ndori.measuringcompass.client.Measure;
import ndori.measuringcompass.util.WorldCoordinate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class UIOverlay {
    private static Minecraft mc = Minecraft.getMinecraft();
    private static FontRenderer fontRenderer = mc.fontRenderer;

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT && Measure.getSelectedBlock() != null) {
            EntityPlayer player = mc.player;
            BlockPos pl = new BlockPos(mc.getRenderViewEntity().posX, mc.getRenderViewEntity().getEntityBoundingBox().minY, mc.getRenderViewEntity().posZ);
            WorldCoordinate playerCoordinate = new WorldCoordinate(pl, player.dimension);
            String text = playerCoordinate.getOffsetsString(Measure.getSelectedBlock());
            if (!text.isEmpty()) {
                ScaledResolution res = new ScaledResolution(mc);
                int w = res.getScaledWidth();
                int h = res.getScaledHeight();

                fontRenderer.drawStringWithShadow(text, w / 2 - fontRenderer.getStringWidth(text) / 2, h / 10 * 7, 0xFFFFFF);
            }
        }
    }
}
