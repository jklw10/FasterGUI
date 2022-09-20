package dev.tr7zw.fastergui.util;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.ShaderInstance;

public class InstanceRenderer {
    public Model model;
    public Buffer perInstanceData;
    public ShaderInstance shader;
    private int instanceCount;
    private List<GPUEntityComponentContainer> data = new ArrayList<GPUEntityComponentContainer>();
    public InstanceRenderer(ShaderInstance shader, Model instanceModel){
        this.shader = shader;
        this.model = instanceModel.instance();
    }
    public int createInstance(GPUEntityComponentContainer data){
        this.data.add(data);
        return instanceCount++;
    }
    boolean dirty = false;
    public void queueInstance(int instanceId, GPUEntityComponentContainer data){
        this.data[instanceId].update(data);
        dirty = true;
    }
    public void updateInstances(){
        model.update(data.toArray());
        dirty = false;
    }

    public void draw(){
        if(dirty) updateInstances();
        shader.apply();
        model.drawInstanced(instanceCount);
        shader.clear();
    }

}
