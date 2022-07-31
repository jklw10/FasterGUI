package dev.tr7zw.fastergui.util;

import java.nio.ByteBuffer;
import org.lwjgl.opengl.GL46;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;

import dev.tr7zw.fastergui.GpuBuffers.DataType;
import dev.tr7zw.fastergui.GpuBuffers.ElementBuffer;
import dev.tr7zw.fastergui.GpuBuffers.GPUBuffer;
import dev.tr7zw.fastergui.GpuBuffers.VertexAttributeBuffer;
import net.minecraft.client.renderer.ShaderInstance;

public class Model {
    ElementBuffer toDraw;
    int vertexCount;
    int indexCount;
    int GlMode;
    VertexAttributeBuffer positions;
    VertexAttributeBuffer UVs;
    VertexAttributeBuffer lighting;
    ByteBuffer Lights;
    public Model(Vector3f[] modelData, Vector2f[] uvData, int[] indices){
        GlMode = GL46.GL_TRIANGLES;
        vertexCount = modelData.length;
        indexCount = indices.length;
        
        Lights = GPUBuffer.byteBufferFromDataType(vertexCount,DataType.SHORT2);
        MakeEBO(modelData, uvData, indices);
    }
    
    private void MakeEBO(Vector3f[] modelData, Vector2f[] uvData, int[] indices){
        positions = new VertexAttributeBuffer(GL46.GL_STATIC_DRAW, 0, DataType.FLOAT3);
        UVs = new VertexAttributeBuffer(GL46.GL_STATIC_DRAW, 1, DataType.FLOAT2);
        //yeah uhh... mmm make the shader stuff better ?
        lighting = new VertexAttributeBuffer(GL46.GL_STATIC_DRAW, 4, DataType.SHORT2);
        
        toDraw = new ElementBuffer(indices, new VertexAttributeBuffer[]{positions,UVs,lighting}, vertexCount, GlMode);
        
        positions.setData(GPUBuffer.vecToByteBuffer(modelData));
        UVs.setData(GPUBuffer.vecToByteBuffer(uvData));
        lighting.setData(Lights);
    }
    
    public void drawWithShader(Matrix4f modelViewMat, Matrix4f projMat, ShaderInstance shaderInstance) {
        shaderInstance.apply();
        UpdateShader(modelViewMat, projMat, shaderInstance);
        toDraw.draw();
        shaderInstance.clear();
    }

    public void draw(Matrix4f modelViewMat) {
        drawWithShader(modelViewMat, RenderSystem.getProjectionMatrix(), RenderSystem.getShader());
    }
    public void draw(Matrix4f modelViewMat, int light) {
        updateLight(light);
        draw(modelViewMat);
   }
    int curlight = 0;
    //only a small hint of a curse can be found here
    public void updateLight(int light){
        if(curlight == light) return;
        curlight = light;
        Lights.clear();
        for(int i = 0; i < vertexCount; i++) {
            Lights.putShort((short)(light & '\uffff'));
            Lights.putShort((short)(light >> 16 & '\uffff'));
        }
        Lights.flip(); //i can only blame java for this...
        
        lighting.setData(Lights);
    }

    public void UpdateShader(Matrix4f modelViewMat, Matrix4f projMat, ShaderInstance shaderInstance){
        for(int i = 0; i < 12; ++i) {
            int j = RenderSystem.getShaderTexture(i);
            shaderInstance.setSampler("Sampler" + i, j);
         }
         //why are 90% of these not updated in the ShaderInstance or RenderSystem?... mojank plz?
         
         if (shaderInstance.MODEL_VIEW_MATRIX != null) {
            shaderInstance.MODEL_VIEW_MATRIX.set(modelViewMat);
         }
   
         if (shaderInstance.PROJECTION_MATRIX != null) {
            shaderInstance.PROJECTION_MATRIX.set(projMat);
         }
   
         if (shaderInstance.INVERSE_VIEW_ROTATION_MATRIX != null) {
            shaderInstance.INVERSE_VIEW_ROTATION_MATRIX.set(RenderSystem.getInverseViewRotationMatrix());
         }
   
         if (shaderInstance.COLOR_MODULATOR != null) {
            shaderInstance.COLOR_MODULATOR.set(RenderSystem.getShaderColor());
         }
   
         if (shaderInstance.FOG_START != null) {
            shaderInstance.FOG_START.set(RenderSystem.getShaderFogStart());
         }
   
         if (shaderInstance.FOG_END != null) {
            shaderInstance.FOG_END.set(RenderSystem.getShaderFogEnd());
         }
   
         if (shaderInstance.FOG_COLOR != null) {
            shaderInstance.FOG_COLOR.set(RenderSystem.getShaderFogColor());
         }
   
         if (shaderInstance.FOG_SHAPE != null) {
            shaderInstance.FOG_SHAPE.set(RenderSystem.getShaderFogShape().getIndex());
         }
   
         if (shaderInstance.TEXTURE_MATRIX != null) {
            shaderInstance.TEXTURE_MATRIX.set(RenderSystem.getTextureMatrix());
         }
   
         if (shaderInstance.GAME_TIME != null) {
            shaderInstance.GAME_TIME.set(RenderSystem.getShaderGameTime());
         }
         // and why is this but not the others?
         RenderSystem.setupShaderLights(shaderInstance);
    }
    
    public void close() {
        toDraw.delete();
    }
    
    public record Vector2f(float x, float y)  {}

}
