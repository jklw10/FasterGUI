package dev.tr7zw.fastergui.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.platform.GlDebug;

@Mixin(GlDebug.class)
public class DebugCallBackMixin {
    
    @Inject(at = @At("TAIL"), method = "printDebugLog")
    private static void printDebugLog(int i, int j, int k, int l, int m, long n, long o, CallbackInfo ci){
        Thread.dumpStack();
    }
}
