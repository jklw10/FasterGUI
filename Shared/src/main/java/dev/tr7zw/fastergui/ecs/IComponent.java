package dev.tr7zw.fastergui.ecs;

import java.nio.ByteBuffer;

import dev.tr7zw.fastergui.gpuBuffers.DataType;

public interface IComponent {
    public ByteBuffer asBuffer();
    public default int getStride(){
        var dt = getDataType();
        return dt.Size*dt.Dimensions;
    }
    public DataType getDataType();
}