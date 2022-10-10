package dev.tr7zw.fastergui.gpuBuffers;

import org.lwjgl.opengl.GL46;

public class ShaderStorageBuffer extends GPUBuffer {

    public ShaderStorageBuffer(int bufferBase, int hint) {
        super(GL46.GL_SHADER_STORAGE_BUFFER, hint);
        bindTo(bufferBase);
    }
    public void bindTo(int bufferBase)
    {
        bind();
        GL46.glBindBufferBase(GL46.GL_SHADER_STORAGE_BUFFER, 
            bufferBase, Handle);
    }
}
