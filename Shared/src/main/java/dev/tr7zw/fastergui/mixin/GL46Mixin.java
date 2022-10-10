package dev.tr7zw.fastergui.mixin;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.DisplayData;
import com.mojang.blaze3d.platform.ScreenManager;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.platform.WindowEventHandler;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Window.class)
public class GL46Mixin {

    @Inject(method = "<init>", 
    at = @At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwWindowHint(II)V",ordinal = 5 , shift = At.Shift.AFTER, remap = false))
    private void windowHintOverride(WindowEventHandler windowEventHandler, ScreenManager screenManager, DisplayData displayData, String string, String string2, CallbackInfo asd) {
        
      GLFW.glfwWindowHint(139266, 4);
      GLFW.glfwWindowHint(139267, 6);
      GLFW.glfwWindowHint(139270, 0);
    }
}