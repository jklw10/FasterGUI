package dev.tr7zw.fastergui.gpuBuffers;

import org.lwjgl.opengl.GL46;

public class ElementBuffer {
    public GPUBuffer internalBuffer = new GPUBuffer(GL46.GL_ELEMENT_ARRAY_BUFFER, GL46.GL_STATIC_DRAW, DataType.UINT);
    public int indexCount;
    public ElementBuffer(){}

    public void setData(int[] indices){
        internalBuffer.setData(indices); 
        indexCount = indices.length;
    }
    public void delete(){
        internalBuffer.delete();
        VertexArrays.unbind();
    }
}
