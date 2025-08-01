package com.github.spookie6.frozen.features.dungeons;

import com.github.spookie6.frozen.config.ModConfig;
import com.github.spookie6.frozen.events.impl.ChatPacketEvent;
import com.github.spookie6.frozen.utils.ChatUtils;
import com.github.spookie6.frozen.utils.overlays.BooleanConfigBinding;
import com.github.spookie6.frozen.utils.overlays.OverlayManager;
import com.github.spookie6.frozen.utils.overlays.TextOverlay;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import static com.github.spookie6.frozen.Frozen.mc;

public class CrystalTitle {

    private static long pickedUp = -1;

    public CrystalTitle() {
        OverlayManager.register(new TextOverlay(
                        new BooleanConfigBinding(
                                () -> ModConfig.crystalTitle,
                                (val) -> ModConfig.crystalTitle = val
                        ),
                        "Energy crystal spawn ticks",
                        () -> "Place Crystal!",
                        () -> pickedUp > -1,
                        "Place Crystal!"
                )
        );
    }

    @SubscribeEvent(receiveCanceled = true)
    public void onChatPacket(ChatPacketEvent e) {
        if (e.message.matches("(\\w+) picked up an Energy Crystal!")) {
            if (e.message.split(" ")[0].equals(mc.thePlayer.getDisplayNameString())) pickedUp = System.currentTimeMillis();
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent e) {
        if (pickedUp <= 0) return;
        ItemStack item = mc.thePlayer.inventory.getStackInSlot(8);
        String displayName = ChatFormatting.stripFormatting(item.getDisplayName());

        if (!displayName.contains("Energy Crystal")) {
            if (pickedUp > 0) {
                if (ModConfig.sendCrystalTime) sendCrystalMessage();
                pickedUp = -1;
            }
        }
    }

    private static void sendCrystalMessage() {
        float time = System.currentTimeMillis() - pickedUp;
        ChatUtils.sendModInfo(String.format("Crystal placed in &a%.2fs&7.", time / 1000));
    }
}