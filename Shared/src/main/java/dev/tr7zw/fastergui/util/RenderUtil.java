package dev.tr7zw.fastergui.util;

import com.mojang.blaze3d.pipeline.RenderCall;
import com.mojang.blaze3d.systems.RenderSystem;

public class RenderUtil {
    //why not runnable mojang?
    public static void EnsureRenderThread(RenderCall call){
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(call);
         } else {
            call.execute();
         }
    }
}
