package com.breechswap.mixin;

import com.breechswap.BreechSwapClient;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Inject(method = "startAttack", at = @At("HEAD"))
    private void breechswap$selectMaceBeforeAttack(CallbackInfoReturnable<Boolean> cir) {
        Minecraft client = (Minecraft) (Object) this;
        if (BreechSwapClient.isEnabled() && client.player != null) {
            int slot = BreechSwapClient.getMaceSlotZeroBased();
            client.player.getInventory().setSelectedSlot(slot);
            // Send the held-slot update immediately so it reaches the server before the attack packet.
            client.player.connection.send(new ServerboundSetCarriedItemPacket(slot));
        }
    }
}
