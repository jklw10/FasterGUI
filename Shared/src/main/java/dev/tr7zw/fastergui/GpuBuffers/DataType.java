package dev.tr7zw.fastergui.GpuBuffers;

import org.lwjgl.opengl.GL46;

public enum DataType {
    BYTE(GL46.GL_BYTE, 1, 1),
    BYTE2(GL46.GL_BYTE, 1, 2),
    BYTE3(GL46.GL_BYTE, 1, 3),
    BYTE4(GL46.GL_BYTE, 1, 4),
    SHORT(GL46.GL_SHORT, 2, 1),
    SHORT2(GL46.GL_SHORT, 2, 2),
    SHORT3(GL46.GL_SHORT, 2, 3),
    SHORT4(GL46.GL_SHORT, 2, 4),
    INT(GL46.GL_INT, 4, 1),
    INT2(GL46.GL_INT, 4, 2),
    INT3(GL46.GL_INT, 4, 3),
    INT4(GL46.GL_INT, 4, 4),
    UINT(GL46.GL_UNSIGNED_INT, 4, 1),
    UINT2(GL46.GL_UNSIGNED_INT, 4, 2),
    UINT3(GL46.GL_UNSIGNED_INT, 4, 3),
    UINT4(GL46.GL_UNSIGNED_INT, 4, 4),
    FLOAT(GL46.GL_FLOAT, 4, 1),
    FLOAT2(GL46.GL_FLOAT, 4, 2),
    FLOAT3(GL46.GL_FLOAT, 4, 3),
    FLOAT4(GL46.GL_FLOAT, 4, 4);
    public final int Size;
    public final int Dimensions;
    public final int GLType;
    private DataType(int glmode, int size, int dimensions){
        Size = size;
        GLType = glmode;
        Dimensions = dimensions;
    }
    
}
