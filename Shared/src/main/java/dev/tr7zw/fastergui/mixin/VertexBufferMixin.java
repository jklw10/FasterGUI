package dev.tr7zw.fastergui.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.VertexBuffer;

//because mojang don't know how di do
@Mixin(VertexBuffer.class)
public class VertexBufferMixin {
    @Shadow
    public void bind() {};
    @Inject(at = @At("HEAD"), method = "draw")
    public void draw(CallbackInfo ci) {
        bind();
    }
}
