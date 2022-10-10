package dev.tr7zw.fastergui.util;

import com.mojang.math.Vector3f;

import dev.tr7zw.fastergui.ecs.GPUEntityComponentContainer;
import dev.tr7zw.fastergui.ecs.IComponent;
import dev.tr7zw.fastergui.gpuBuffers.DataType;
import dev.tr7zw.fastergui.gpuBuffers.VertexDataFormat;
import dev.tr7zw.fastergui.gpuBuffers.VertexDataFormat.VertexDataComponent;
import dev.tr7zw.fastergui.gpuBuffers.VertexDataFormat.IndexDataComponent;
import dev.tr7zw.fastergui.gpuBuffers.VertexDataFormat.LightComponent;
import dev.tr7zw.fastergui.gpuBuffers.VertexDataFormat.UVDataComponent;
import dev.tr7zw.fastergui.gpuBuffers.VertexDataFormat.VertexAttribute;
import dev.tr7zw.fastergui.util.Model.Vector2f;

public class DefaultModel {
    
    public static GPUEntityComponentContainer quadcontainer;
    
    private static Model QUAD;
    public static Model getQuadModel(){
        if(QUAD != null){
            return QUAD;
        }
        
        Vector3f[] modelData = new Vector3f[]{
            new Vector3f(0, 0, 0),
            new Vector3f(0, 1, 0),
            new Vector3f(1, 0, 0),
            new Vector3f(1, 1, 0),
        };
        Vector2f[] uvData = new Vector2f[]{
            new Vector2f(0, 0),
            new Vector2f(0, 1),
            new Vector2f(1, 0),
            new Vector2f(1, 1),
        };
        //clockwise rotation
        // side note. i need a mesh builder :D
        int[] indices = new int[]{
            0,1,2,
            3,2,1,
        };
        quadcontainer = new GPUEntityComponentContainer();
        QUAD = new Model(quadcontainer, null, fogShaderFormat(), fogShaderData(modelData,uvData,indices));
        return QUAD;
    }
    private static VertexDataFormat fogShaderFormat(){
        return new VertexDataFormat(
            new VertexAttribute(-1,DataType.INT,0),//index todo
            new VertexAttribute(0,DataType.FLOAT3,0),//vertex
            new VertexAttribute(1,DataType.FLOAT2,0),//UV
            new VertexAttribute(4,DataType.SHORT2,1) //light
            );
    }
    private static IComponent[] fogShaderData(Vector3f[] vertices, Vector2f[] uv, int[] indices){
        IComponent[] components = new IComponent[]{
            new IndexDataComponent(indices),
            new VertexDataComponent(vertices),
            new UVDataComponent(uv),
            new LightComponent(0)
        };
        return components;
    }
}
