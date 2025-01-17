package dev.tr7zw.fastergui.gpuBuffers;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.Arrays;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;
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
   //public void setData(int[] data)
   //{
   //    bind();
   //    var buf = BufferUtils.createIntBuffer(data.length).put(data).flip();
   //    GL46.glBufferData(bufferTarget, buf, bufferUsageHint);
   //}
    public void setData(ByteBuffer data)
    {
        bind();
        GL46.glBufferData(bufferTarget, data, bufferUsageHint);
        if(bufferTarget == GL46.GL_ELEMENT_ARRAY_BUFFER){
            int[] array = new int[6];
            GL46.glGetBufferSubData(bufferTarget, 0, array);
            System.out.println(Arrays.toString(array));
        }else{
            var array = new float[data.remaining()/DataType.FLOAT.Size];
            GL46.glGetBufferSubData(bufferTarget, 0, array);
            System.out.println(Arrays.toString(array));
        }
    }
    public void subData(int start, ByteBuffer data)
    {
        bind();
        GL46.glBufferSubData(bufferTarget, start, data);
    }
    public void bind() 
    {
        System.out.println("bound buffer target:"+bufferTarget+", handle:"+Handle);
        GL46.glBindBuffer(bufferTarget, Handle);
    }
    public void delete(){
        GL46.glDeleteBuffers(Handle);
    }
    //public static FloatBuffer vecToFloatBuffer(Vector2f[] array){
    //    FloatBuffer floats = BufferUtils.createFloatBuffer(array.length*2);
    //    for (var vec : array) {
    //        floats.put(vec.x());
    //        floats.put(vec.y()); 
    //    }
    //    return floats.flip();
    //}
    //public static FloatBuffer vecToFloatBuffer(Vector3f[] array){
    //    FloatBuffer floats = BufferUtils.createFloatBuffer(array.length*3);
    //    for (var vec : array) {
    //        floats.put(vec.x());
    //        floats.put(vec.y()); 
    //        floats.put(vec.z()); 
    //    }
    //    return floats.flip();
    //}
    public static ByteBuffer vecToByteBuffer(Vector3f[] array){
        System.out.println("vec3 array:"+Arrays.toString(array));
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
        System.out.println("vec2 array:"+Arrays.toString(array));
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
        for (var value : array) {
            values.putInt(value);
        }
        return values.flip();
    }
    public static ByteBuffer byteBufferFromDataType(int length,DataType dt){
        return BufferUtils.createByteBuffer(length*dt.Dimensions*dt.Size);
    }
}
