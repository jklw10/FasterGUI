package dev.tr7zw.fastergui.gpuBuffers;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL46;

public class ElementBuffer {
    public GPUBuffer internalBuffer = new GPUBuffer(GL46.GL_ELEMENT_ARRAY_BUFFER, GL46.GL_STATIC_DRAW);
    public int indexCount;
    
    public void setData(int[] indices){
        internalBuffer.setData(GPUBuffer.intToByteBuffer(indices)); 
        indexCount = indices.length;
    }
    public void setData(ByteBuffer indices, int count){
        internalBuffer.setData(indices); 
        indexCount = count;
    }
    public void delete(){
        internalBuffer.delete();
        VertexArrays.unbind();
    }
}
