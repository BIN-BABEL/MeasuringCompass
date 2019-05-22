package ndori.measuringcompass.util;

import ndori.measuringcompass.client.ClientInfo;
import ndori.measuringcompass.client.Measure;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class EventHandler {
    @SubscribeEvent
    public void onClientConnected(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        ClientInfo.clearAll();
    }

    @SubscribeEvent
    public void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        ItemStack heldItem = event.getEntityPlayer().getHeldItem(event.getHand());
        if (event.getEntityPlayer().world.isRemote && event.getHand() == EnumHand.MAIN_HAND && heldItem.isItemEqual(ClientInfo.measurerItem)) {
            if (event.getEntityPlayer().isSneaking()) {
                ClientInfo.clearLast();
            } else {
                Measure.measure(event.getEntityPlayer(), event.getWorld(), event.getPos());
                event.setCanceled(true);
                event.setCancellationResult(EnumActionResult.SUCCESS);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerRightClickItem(PlayerInteractEvent.RightClickItem event) {
        ItemStack heldItem = event.getEntityPlayer().getHeldItem(event.getHand());
        if (event.getEntityPlayer().world.isRemote && event.getHand() == EnumHand.MAIN_HAND && heldItem.isItemEqual(ClientInfo.measurerItem)) {
            if (event.getEntityPlayer().isSneaking()) {
                ClientInfo.clearLast();
            } else {
                Measure.openGui();
            }
        }
    }
}
