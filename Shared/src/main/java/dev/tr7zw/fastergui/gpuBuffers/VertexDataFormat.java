package dev.tr7zw.fastergui.gpuBuffers;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL46;

import com.mojang.math.Vector3f;

import dev.tr7zw.fastergui.ecs.GPUEntityComponentContainer;
import dev.tr7zw.fastergui.ecs.IComponent;
import dev.tr7zw.fastergui.util.Model.Vector2f;

public class VertexDataFormat {
    public record LightComponent(int light) implements IComponent{
        static final DataType type = DataType.INT;
        //please for the love of god do no use this for an array
        @Override
        public ByteBuffer asBuffer() {
            var buf = BufferUtils.createByteBuffer(getStride());
            buf.asIntBuffer().put(light).flip();
            return buf;
        }
        @Override
        public int getStride() {
            return type.Size*type.Dimensions;
        }
    }
    public record VertexDataComponent(Vector3f[] positions) implements IComponent{
        static DataType type = DataType.FLOAT3;
        @Override
        public ByteBuffer asBuffer() {
            return GPUBuffer.vecToByteBuffer(positions);
        }
        @Override
        public int getStride() {
            return type.Size*type.Dimensions;
        }
    }
    public record UVDataComponent(Vector2f[] positions) implements IComponent{
        static DataType type = DataType.FLOAT2;
        @Override
        public ByteBuffer asBuffer() {
            return GPUBuffer.vecToByteBuffer(positions);
        }
        @Override
        public int getStride() {
            return type.Size*type.Dimensions;
        }
    }
    public record IndexDataComponent(int[] positions) implements IComponent{
        static DataType type = DataType.INT;
        @Override
        public ByteBuffer asBuffer() {
            return GPUBuffer.intToByteBuffer(positions);
        }
        @Override
        public int getStride() {
            return type.Size*type.Dimensions;
        }
    }

    public record VertexAttribute(int location, DataType dataType, int divisor, int componentType)  {}

    public final VertexAttribute[] vertexData;
    public VertexDataFormat(VertexAttribute... data){
        vertexData = data;
    }
    public void enable(GPUBuffer[] buffers){
        for (int i = 0; i < buffers.length; i++) {
            buffers[i].bind();
            enable(vertexData[i]);
        }
    }
    public static void enable(VertexAttributeBuffer[] buffers){
        for (var vab : buffers) {
            vab.internalBuffer.bind();
            enable(vab.attribute);
        }
    }
    public void enable(GPUEntityComponentContainer buffers){
        enable(buffers.getBuffers());
    }
    public static void enable(VertexAttribute attribute){
        GL46.glEnableVertexAttribArray(attribute.location);
        GL46.glVertexAttribPointer(attribute.location, attribute.dataType.Dimensions, attribute.dataType.GLType, false, 0, 0);
        GL46.glVertexAttribDivisor(attribute.location, attribute.divisor);
    }
}
