package dev.tr7zw.fastergui.GpuBuffers;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.opengl.GL46;



public class VertexAttributeBuffer {
    GPUBuffer internalBuffer;
    public final int Attribute;
    public final DataType DataType;
    public VertexAttributeBuffer(int hint, int attribute, DataType dataType){
        internalBuffer = new GPUBuffer(GL46.GL_ARRAY_BUFFER, hint, dataType);
        Attribute = attribute;
        DataType = dataType;
    }
    public void setData(ByteBuffer data)
    {
        internalBuffer.setData(data);
    }
    public void setData(FloatBuffer data)
    {
        internalBuffer.setData(data);
    }
    public void setData(ShortBuffer data)
    {
        internalBuffer.setData(data);
    }
    public void setData(IntBuffer data)
    {
        internalBuffer.setData(data);
    }
    public void enable()
    {
        internalBuffer.Use();
        GL46.glVertexAttribPointer(Attribute, DataType.Dimensions, DataType.GLType, false, 0, 0);
        GL46.glEnableVertexAttribArray(Attribute);
    }
    public void delete(){
        internalBuffer.delete();
    }
}
