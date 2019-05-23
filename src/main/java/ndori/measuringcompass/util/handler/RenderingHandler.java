package ndori.measuringcompass.util.handler;

import ndori.measuringcompass.MeasuringCompass;
import ndori.measuringcompass.util.BoundingBox;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.List;

@SideOnly(Side.CLIENT)
public class RenderingHandler {

    private static Minecraft mc = Minecraft.getMinecraft();
    private static RenderManager renderManager = mc.getRenderManager();
    private static FontRenderer fontRenderer = mc.fontRenderer;

    public static boolean doFill = false;

    @SubscribeEvent
    public void onRenderWorldLastEvent(RenderWorldLastEvent event) {
        EntityPlayer player = mc.player;
        ItemStack heldItem = player.getHeldItem(EnumHand.MAIN_HAND);

        List<BoundingBox> boxList = MeasuringCompass.instance.clientInfo.getBoxList();

        if (!heldItem.isEmpty() && heldItem.isItemEqual(MeasuringCompass.instance.clientInfo.getMeasurerItem())) {
            float partialTicks = event.getPartialTicks();
            double pX = mc.player.prevPosX + (mc.player.posX - mc.player.prevPosX) * partialTicks;
            double pY = mc.player.prevPosY + (mc.player.posY - mc.player.prevPosY) * partialTicks;
            double pZ = mc.player.prevPosZ + (mc.player.posZ - mc.player.prevPosZ) * partialTicks;

            if (boxList != null) {
                GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
                GL11.glPushMatrix();
                GL11.glTranslated(-pX, -pY, -pZ);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glLineWidth(1.0F);

                // Draw stuff
                for (BoundingBox bb : boxList) {
                    if (player.dimension == bb.dimension) {
                        if (doFill && !player.getEntityBoundingBox().intersects(bb)) {
                            float alpha = bb.a/255f;
                            if (alpha > 0.5f) alpha = 0.5f;
                            RenderGlobal.renderFilledBox(bb.grow(0.002D), bb.r/255f, bb.g/255f, bb.b/255f, alpha);
                            RenderGlobal.drawSelectionBoundingBox(bb.grow(0.002D), 1f - bb.r/255f, 1f - bb.g/255f, 1f - bb.b/255f, bb.a/255f);
                        } else {
                            RenderGlobal.drawSelectionBoundingBox(bb.grow(0.002D), bb.r/255f, bb.g/255f, bb.b/255f, bb.a/255f);
                        }

                        int x = (int) (bb.maxX - bb.minX);
                        int y = (int) (bb.maxY - bb.minY);
                        int z = (int) (bb.maxZ - bb.minZ);
                        int[] edgeLength = new int[]{x, y, z};

                        // Text
                        GL11.glEnable(GL11.GL_TEXTURE_2D);

                        int i = 0;
                        for (Vec3d edge : bb.nearestEdges()) {
                            GL11.glPushMatrix();

                            double offX = edge.x; // BlockPos coordinates for the middle of the edge
                            double offY = edge.y;
                            double offZ = edge.z;

                            GL11.glTranslated(offX, offY, offZ);
                            GL11.glRotated(-renderManager.playerViewY, 0.0, 1.0, 0.0);
                            GL11.glRotated(renderManager.playerViewX, 1.0, 0.0, 0.0);
                            GL11.glScaled(-0.04, -0.04, 0.04);

                            fontRenderer.drawString(String.valueOf(edgeLength[i]), -fontRenderer.getStringWidth(String.valueOf(edgeLength[i])) / 2, 0, bb.getColorInt(), true);

                            GL11.glPopMatrix();
                            i++;
                        }
                        GL11.glDisable(GL11.GL_TEXTURE_2D);
                    }
                }

                GL11.glDisable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                GL11.glPopMatrix();
                GL11.glPopAttrib();
            }
        }
    }
}
