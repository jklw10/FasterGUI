package dev.tr7zw.fastergui.ecs;

import java.nio.ByteBuffer;

public interface IComponent {
    public ByteBuffer asBuffer();
    public int getStride();
}
