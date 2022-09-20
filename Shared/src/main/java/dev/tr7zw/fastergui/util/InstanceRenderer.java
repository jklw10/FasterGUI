package dev.tr7zw.fastergui.util;

import java.nio.Buffer;

import dev.tr7zw.fastergui.ecs.GPUEntityComponentContainer;
import dev.tr7zw.fastergui.ecs.IComponent;
import net.minecraft.client.renderer.ShaderInstance;

public class InstanceRenderer {
    public Model model;
    public Buffer perInstanceData;
    public ShaderInstance shader;
    private int instanceCount;
    private GPUEntityComponentContainer data = new GPUEntityComponentContainer();
    public InstanceRenderer(ShaderInstance shader, Model instanceModel){
        this.shader = shader;
        this.model = instanceModel.instance();
    }
    public int createInstance(IComponent[] data){
        instanceCount++;
        return this.data.createEntity(data);
    }
    
    public void updateInstance(int id, IComponent[] data){
        this.data.setEntity(id, data);
    }

    public void draw(){
        shader.apply();
        model.drawInstanced(instanceCount);
        shader.clear();
    }
}
