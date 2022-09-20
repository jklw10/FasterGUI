package dev.tr7zw.fastergui.gpuBuffers;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL46;

import com.mojang.math.Vector3f;

import dev.tr7zw.fastergui.util.Model.Vector2f;

public class GPUBuffer{
    final int Handle;
    final int bufferTarget;
    final int bufferUsageHint;
    public GPUBuffer(int target, int hint){
        Handle = GL46.glGenBuffers();
        bufferTarget = target;
        bufferUsageHint = hint;
    }
    public void setData(int[] data)
    {
        bind();
        var buf = BufferUtils.createIntBuffer(data.length).put(data).flip();
        GL46.glBufferData(bufferTarget, buf, bufferUsageHint);
    }
    public void setData(ByteBuffer data)
    {
        bind();
        GL46.glBufferData(bufferTarget, data, bufferUsageHint);
    }
    public void subData(int start, ByteBuffer data)
    {
        bind();
        GL46.glBufferSubData(bufferTarget, start, data);
    }
    public void bind() 
    {
        GL46.glBindBuffer(bufferTarget, Handle);
    }
    public void delete(){
        GL46.glDeleteBuffers(Handle);
    }
    public static FloatBuffer vecToFloatBuffer(Vector2f[] array){
        FloatBuffer floats = BufferUtils.createFloatBuffer(array.length*2);
        for (var vec : array) {
            floats.put(vec.x());
            floats.put(vec.y()); 
        }
        return floats.flip();
    }
    public static FloatBuffer vecToFloatBuffer(Vector3f[] array){
        FloatBuffer floats = BufferUtils.createFloatBuffer(array.length*3);
        for (var vec : array) {
            floats.put(vec.x());
            floats.put(vec.y()); 
            floats.put(vec.z()); 
        }
        return floats.flip();
    }
    public static ByteBuffer vecToByteBuffer(Vector3f[] array){
        var dt = DataType.FLOAT3;
        ByteBuffer floats = byteBufferFromDataType(array.length,dt);
        for (var vec : array) {
            floats.putFloat(vec.x());
            floats.putFloat(vec.y()); 
            floats.putFloat(vec.z()); 
        }
        return floats.flip();
    }
    public static ByteBuffer vecToByteBuffer(Vector2f[] array){
        var dt = DataType.FLOAT2;
        ByteBuffer floats = byteBufferFromDataType(array.length,dt);
        for (var vec : array) {
            floats.putFloat(vec.x());
            floats.putFloat(vec.y()); 
        }
        return floats.flip();
    }
    public static ByteBuffer intToByteBuffer(int[] array){
        var dt = DataType.INT;
        ByteBuffer values = byteBufferFromDataType(array.length,dt);
        values.asIntBuffer().put(array);
        return values.flip();
    }
    public static ByteBuffer byteBufferFromDataType(int length,DataType dt){
        return BufferUtils.createByteBuffer(length*dt.Dimensions*dt.Size);
    }
}
