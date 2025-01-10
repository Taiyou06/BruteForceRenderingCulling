package rogo.renderingculling.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rogo.renderingculling.api.CullingStateManager;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;render(Lnet/minecraft/client/DeltaTracker;Z)V", shift = At.Shift.AFTER))
    public void afterRunTick(boolean p_91384_, CallbackInfo ci) {
        CullingStateManager.onProfilerPopPush("runTick");
    }

    @Inject(method = "runTick", at = @At(value = "HEAD"))
    public void beforeRunTick(boolean p_91384_, CallbackInfo ci) {
        CullingStateManager.onProfilerPopPush("beforeRunTick");
    }

    @Inject(method = "setLevel", at = @At("HEAD"))
    private void onJoinWorld(ClientLevel clientLevel, ReceivingLevelScreen.Reason reason, CallbackInfo ci) {
        CullingStateManager.onWorldUnload(clientLevel);
    }
}
