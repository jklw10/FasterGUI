package dev.tr7zw.fastergui.GpuBuffers;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.opengl.GL46;

import com.mojang.math.Vector3f;

import dev.tr7zw.fastergui.util.Model.Vector2f;

public class GPUBuffer{
    final int Handle;
    final int bufferTarget;
    final int bufferUsageHint;
    final DataType Type;
    public GPUBuffer(int target, int hint, DataType type){
        Handle = GL46.glGenBuffers();
        bufferTarget = target;
        bufferUsageHint = hint;
        Type = type;
    }
    public void setData(FloatBuffer data)
    {
        Use();
        GL46.glBufferData(bufferTarget, data, bufferUsageHint);
    }
    public void SubData(int start, FloatBuffer data)
    {
        Use();
        GL46.glBufferSubData(bufferTarget, start, data);
    }
    public void setData(ShortBuffer data)
    {
        Use();
        GL46.glBufferData(bufferTarget, data, bufferUsageHint);
    }
    public void SubData(int start, ShortBuffer data)
    {
        Use();
        GL46.glBufferSubData(bufferTarget, start, data);
    }
    public void setData(IntBuffer data)
    {
        Use();
        GL46.glBufferData(bufferTarget, data, bufferUsageHint);
    }
    public void SubData(int start, IntBuffer data)
    {
        Use();
        GL46.glBufferSubData(bufferTarget, start, data);
    }
    int logs = 0;
    public void Use() 
    {
        if(logs <= 40){
            Thread.dumpStack();
            logs++;
        }else{
            System.exit(1);
        }
        
        GL46.glBindBuffer(bufferTarget, Handle);
    }
    public void delete(){
        GL46.glDeleteBuffers(Handle);
    }
    public static FloatBuffer vecToFloatBuffer(Vector2f[] array){
        FloatBuffer floats = FloatBuffer.allocate(array.length*2);
        for (var vec : array) {
            floats.put(vec.x());
            floats.put(vec.y()); 
        }
        return floats;
    }
    public static FloatBuffer vecToFloatBuffer(Vector3f[] array){
        FloatBuffer floats = FloatBuffer.allocate(array.length*3);
        for (var vec : array) {
            floats.put(vec.x());
            floats.put(vec.y()); 
            floats.put(vec.z()); 
        }
        return floats;
    }
}
