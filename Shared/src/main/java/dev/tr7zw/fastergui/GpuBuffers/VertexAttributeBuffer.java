package dev.tr7zw.fastergui.gpuBuffers;

import org.lwjgl.opengl.GL46;

import dev.tr7zw.fastergui.gpuBuffers.VertexDataFormat.VertexAttribute;

public class VertexAttributeBuffer {
    GPUBuffer internalBuffer;
    VertexAttribute attribute;
    public VertexAttributeBuffer(int hint, int attribute, DataType dataType){
        internalBuffer = new GPUBuffer(GL46.GL_ARRAY_BUFFER, hint);
        this.attribute = new VertexAttribute(attribute, dataType, 0);
    }
    public VertexAttributeBuffer(int hint, int attribute, DataType dataType, int divisor){
        internalBuffer = new GPUBuffer(GL46.GL_ARRAY_BUFFER, hint);
        this.attribute = new VertexAttribute(attribute, dataType, divisor);
    }
    public VertexAttributeBuffer(int hint, VertexAttribute attribute){
        internalBuffer = new GPUBuffer(GL46.GL_ARRAY_BUFFER, hint);
        this.attribute = attribute;
    }
    public VertexAttributeBuffer(VertexAttribute attribute){
        internalBuffer = new GPUBuffer(GL46.GL_ARRAY_BUFFER, GL46.GL_STATIC_DRAW);
        this.attribute = attribute;
    }
}
