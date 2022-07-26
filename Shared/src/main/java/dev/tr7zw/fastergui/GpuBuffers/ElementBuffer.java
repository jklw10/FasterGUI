package dev.tr7zw.fastergui.GpuBuffers;

import java.nio.IntBuffer;

import org.lwjgl.opengl.GL46;

import com.mojang.blaze3d.systems.RenderSystem;


public class ElementBuffer {
    int VAOHandle;
    VertexAttributeBuffer[] VBO;
    GPUBuffer EBO;
    int indexCount;
    int vertexCount;
    int GlMode;
    public ElementBuffer(int[] indices, VertexAttributeBuffer[] VBO, int vertexCount, int glMode)
    {
        VAOHandle = GL46.glGenVertexArrays();
        GlMode = glMode;
        bind();
        for (var x : VBO) x.enable();
        indexCount = indices.length;
        this.vertexCount = vertexCount;

        EBO = new GPUBuffer(GL46.GL_ELEMENT_ARRAY_BUFFER, GL46.GL_STATIC_DRAW, DataType.UINT);
        EBO.setData(IntBuffer.wrap(indices).flip()); //why would the flip fix anything :universal_collapse:
        this.VBO = VBO;
        unbind();
    }
    public void bind(){
        GL46.glBindVertexArray(VAOHandle);
    }
    //you really gotta remember this or all hell breaks loose apparently :D
    public void unbind(){
        GL46.glBindVertexArray(0);
    }
    public void delete(){
        GL46.glDeleteVertexArrays(VAOHandle);
        for (var x : VBO) x.delete();
        EBO.delete();
        unbind();
    }
    public void draw(){
        bind();
        RenderSystem.drawElements(GlMode, vertexCount, DataType.UINT.GLType);
        unbind();
    }
    public void draw(int glMode){
        bind();
        RenderSystem.drawElements(glMode, vertexCount, DataType.UINT.GLType);
        unbind();
    }
}
