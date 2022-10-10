package dev.tr7zw.fastergui.ecs;

import java.util.HashMap;
import java.util.Map;


import org.lwjgl.opengl.GL46;

import dev.tr7zw.fastergui.gpuBuffers.GPUBuffer;

public class GPUEntityComponentContainer {
    //TODO expandable gpu buffer
    private final Map<Class<?>, GPUBuffer> buffers = new HashMap<>();
    
    public int createEntity(IComponent[] entitydata)
    {
        int id = establishEntity();

        for(var component : entitydata)
        {
            setComponent(id, component);
        }
        return id;
    }
    public void setComponent(int id, IComponent component)
    {
        var buf = getComponentBuffer(component);
        if(buf == null){
            buf = establishType(component);
            return;
        }
        //TODO if you get here you're fucked until expandable buffers
        buf.subData(id*component.getStride(),component.asBuffer());
    }
    public void setEntity(int id, IComponent[] entitydata)
    {
        for(var component : entitydata)
        {
            setComponent(id, component);
        }
    }
    public GPUBuffer getComponentBuffer(IComponent component)
    {
        return buffers.get(component.getClass());
    }
    public GPUBuffer[] getAllBuffers()
    {
        return buffers.values().toArray(new GPUBuffer[0]);
    }
    
    private GPUBuffer establishType(IComponent comp){
        var buf = new GPUBuffer(GL46.GL_ARRAY_BUFFER, GL46.GL_STATIC_DRAW);
        buf.setData(comp.asBuffer());
        buffers.put(comp.getClass(),buf);
        return buf;
    }
    
    private int latestEntity;
    private int establishEntity(){
        return latestEntity++;
    }
}
