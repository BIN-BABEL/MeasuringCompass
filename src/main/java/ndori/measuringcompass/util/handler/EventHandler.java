package ndori.measuringcompass.util.handler;

import ndori.measuringcompass.MeasuringCompass;
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
        MeasuringCompass.instance.clientInfo.clearAll();
    }

    @SubscribeEvent
    public void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        ItemStack heldItem = event.getEntityPlayer().getHeldItem(event.getHand());
        if (event.getEntityPlayer().world.isRemote && event.getHand() == EnumHand.MAIN_HAND && heldItem.isItemEqual(MeasuringCompass.instance.clientInfo.getMeasurerItem())) {
            if (event.getEntityPlayer().isSneaking()) {
                MeasuringCompass.instance.clientInfo.clearLast();
            } else {
                Measure.measure(event.getEntityPlayer(), event.getWorld(), event.getPos());
            }
            event.setCanceled(true);
            event.setCancellationResult(EnumActionResult.SUCCESS);
        }
    }

    @SubscribeEvent
    public void onPlayerRightClickItem(PlayerInteractEvent.RightClickItem event) {
        ItemStack heldItem = event.getEntityPlayer().getHeldItem(event.getHand());
        if (event.getEntityPlayer().world.isRemote && event.getHand() == EnumHand.MAIN_HAND && heldItem.isItemEqual(MeasuringCompass.instance.clientInfo.getMeasurerItem())) {
            if (event.getEntityPlayer().isSneaking()) {
                MeasuringCompass.instance.clientInfo.clearLast();
            } else {
                Measure.openGui();
            }
        }
    }
}
