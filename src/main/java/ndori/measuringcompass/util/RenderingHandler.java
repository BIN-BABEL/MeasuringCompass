package ndori.measuringcompass.util;

import ndori.measuringcompass.client.ClientInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.List;

@SideOnly(Side.CLIENT)
public class RenderingHandler {

    public boolean isC1Selected = false;

    public static RenderingHandler INSTANCE = new RenderingHandler();
    private static Minecraft mc = ClientInfo.mc;
    private static RenderManager renderManager = mc.getRenderManager();
    private static FontRenderer fontRenderer = mc.fontRenderer;

    public static void init() {
        MinecraftForge.EVENT_BUS.register(INSTANCE);
    }

    @SubscribeEvent
    public void renderInWorld(RenderWorldLastEvent event) {
        EntityPlayer player = mc.player;
        ItemStack heldItem = player.getHeldItem(EnumHand.MAIN_HAND);

        List<BoundingBox> boxes = ClientInfo.getBoxList();

        if (!heldItem.isEmpty() && heldItem.isItemEqual(ClientInfo.measurerItem)) {
            float partialTicks = event.getPartialTicks();
            double pX = mc.player.prevPosX + (mc.player.posX - mc.player.prevPosX) * partialTicks;
            double pY = mc.player.prevPosY + (mc.player.posY - mc.player.prevPosY) * partialTicks;
            double pZ = mc.player.prevPosZ + (mc.player.posZ - mc.player.prevPosZ) * partialTicks;

            GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
            GL11.glPushMatrix();
            GL11.glTranslated(-pX, -pY, -pZ);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glLineWidth(1.0F);

            // Render C1

            if (boxes != null) {
                // Draw stuff
                for (BoundingBox aabb : boxes) {
                    if (player.dimension == aabb.dim) {
                        if (ClientInfo.doFill && !player.getEntityBoundingBox().intersects(aabb)) {
                            float alpha = aabb.a/255f;
                            if (alpha > 0.5f) alpha = 0.5f;
                            RenderGlobal.renderFilledBox(aabb.grow(0.002D), aabb.r/255f, aabb.g/255f, aabb.b/255f, alpha);
                            RenderGlobal.drawSelectionBoundingBox(aabb.grow(0.002D), 1f - aabb.r/255f, 1f - aabb.g/255f, 1f - aabb.b/255f, aabb.a/255f);
                        } else {
                            RenderGlobal.drawSelectionBoundingBox(aabb.grow(0.002D), aabb.r/255f, aabb.g/255f, aabb.b/255f, aabb.a/255f);
                        }

                        int x = (int) (aabb.maxX - aabb.minX);
                        int y = (int) (aabb.maxY - aabb.minY);
                        int z = (int) (aabb.maxZ - aabb.minZ);
                        int[] arr = new int[]{x, y, z};

                        // Text
                        GL11.glEnable(GL11.GL_TEXTURE_2D);

                        if (isC1Selected) {
                            GL11.glPushMatrix();
                            fontRenderer.drawString("Test text", mc.displayWidth / 2, mc.displayHeight / 2, 0xFFFFFFFF, true);
                            GL11.glPopMatrix();
                        }

                        int i = 0;
                        for (Vec3d edge : getEdges(aabb)) {
                            GL11.glPushMatrix();

                            double offX = edge.x; // BlockPos coordinates for the middle of the edge
                            double offY = edge.y;
                            double offZ = edge.z;

                            GL11.glTranslated(offX, offY, offZ);
                            GL11.glRotated(-renderManager.playerViewY, 0.0, 1.0, 0.0);
                            GL11.glRotated(renderManager.playerViewX, 1.0, 0.0, 0.0);
                            GL11.glScaled(-0.04, -0.04, 0.04);

                            fontRenderer.drawString(String.valueOf(arr[i]), -fontRenderer.getStringWidth(String.valueOf(arr[i])) / 2, 0, aabb.getColorInt(), true);

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

    // This is so sloppy
    private Vec3d[] getEdges(BoundingBox aabb) {
        Minecraft mc = ClientInfo.mc;
        RenderManager renderManager = mc.getRenderManager();

        double x1 = aabb.minX;
        double x2 = aabb.maxX;
        double y1 = aabb.minY;
        double y2 = aabb.maxY;
        double z1 = aabb.minZ;
        double z2 = aabb.maxZ;

        double mx = (x1 + x2) / 2;
        double my = (y1 + y2) / 2;
        double mz = (z1 + z2) / 2;

        Vec3d camera = new Vec3d(renderManager.viewerPosX, renderManager.viewerPosY, renderManager.viewerPosZ);
        Vec3d minX = null;
        Vec3d minY = null;
        Vec3d minZ = null;

        Vec3d[] vArr = new Vec3d[]{new Vec3d(mx, y1, z1), new Vec3d(mx, y1, z2), new Vec3d(mx, y2, z1), new Vec3d(mx, y2, z2)};
        for (Vec3d v : vArr) {
            if (minX == null || camera.distanceTo(minX) > camera.distanceTo(v)) {
                minX = v;
            }
        }

        vArr = new Vec3d[]{new Vec3d(x1, my, z1), new Vec3d(x2, my, z1), new Vec3d(x2, my, z2), new Vec3d(x1, my, z2)};
        for (Vec3d v : vArr) {
            if (minY == null || camera.distanceTo(minY) > camera.distanceTo(v)) {
                minY = v;
            }
        }

        vArr = new Vec3d[]{new Vec3d(x1, y1, mz), new Vec3d(x1, y2, mz), new Vec3d(x2, y2, mz), new Vec3d(x2, y1, mz)};
        for (Vec3d v : vArr) {
            if (minZ == null || camera.distanceTo(minZ) > camera.distanceTo(v)) {
                minZ = v;
            }
        }

        return new Vec3d[]{minX, minY, minZ};
    }
}
