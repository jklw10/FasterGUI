package dev.tr7zw.fastergui.gpuBuffers;

import org.lwjgl.opengl.GL46;

public class VertexArrays {
    int handle = GL46.glGenVertexArrays();
    
    public void bind(){
        GL46.glBindVertexArray(handle);
    }
    public static void unbind(){
        GL46.glBindVertexArray(0);
    }
}