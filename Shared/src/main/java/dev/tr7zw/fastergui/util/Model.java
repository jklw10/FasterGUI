package dev.tr7zw.fastergui.util;

import org.lwjgl.opengl.GL46;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Matrix4f;

import dev.tr7zw.fastergui.gpuBuffers.DataType;
import dev.tr7zw.fastergui.gpuBuffers.ElementBuffer;
import dev.tr7zw.fastergui.gpuBuffers.VertexArrays;
import dev.tr7zw.fastergui.gpuBuffers.VertexAttributeBuffer;
import dev.tr7zw.fastergui.gpuBuffers.VertexDataFormat;
import dev.tr7zw.fastergui.gpuBuffers.VertexDataFormat.IndexDataComponent;
import dev.tr7zw.fastergui.ecs.Entity;
import dev.tr7zw.fastergui.ecs.GPUEntityComponentContainer;
import dev.tr7zw.fastergui.ecs.IComponent;
import net.minecraft.client.renderer.ShaderInstance;

public class Model {
   VertexArrays vao = new VertexArrays();
   ElementBuffer toDraw;
   ShaderInstance shader;
   GPUEntityComponentContainer componentContainer;
   int vertexCount;
   public Model(GPUEntityComponentContainer container, ShaderInstance shader, VertexDataFormat dataFormat, IComponent[] components){
      //positions = new VertexAttributeBuffer(GL46.GL_STATIC_DRAW, 0, DataType.FLOAT3);
      //UVs = new VertexAttributeBuffer(GL46.GL_STATIC_DRAW, 1, DataType.FLOAT2);
      //yeah uhh... mmm make the shader stuff better ?
      //lighting = new VertexAttributeBuffer(GL46.GL_STATIC_DRAW, 4, DataType.SHORT2,1);

      this.shader = shader;
      componentContainer = container;
      bind();
      componentContainer.createEntity(components);
      dataFormat.enable(componentContainer);
      toDraw = fromComponent(components[0]);
      unbind();
   }
   public Model(ShaderInstance shader, VertexDataFormat dataFormat, IComponent[] components){
      
      this.shader = shader;
      componentContainer = new GPUEntityComponentContainer();
      bind();
      componentContainer.createEntity(components);
      dataFormat.enable(componentContainer);
      toDraw = fromComponent(components[0]);
      unbind();
   }
   public ElementBuffer fromComponent(IComponent component){
      ElementBuffer eb = new ElementBuffer();
      eb.setData(((IndexDataComponent)component).positions());
      return eb;
   }
   public Model instance(){
      return null; //todo make cloning :/
   }
   
   public void setComponent(int id, IComponent component){
      componentContainer.setComponent(id, component);
   }
   public void setFirstComponent(IComponent component){
      componentContainer.setComponent(0,component);
   }
   public void update(Entity... data){
      //vao.bind();
   }
   

   public void drawInstanced(int count){
      bind();
      shader.apply();
      GL46.nglDrawElementsInstanced(GL46.GL_TRIANGLES, toDraw.indexCount, 0, DataType.UINT.GLType, count);
      unbind();
   }
   private void bind(){
      vao.bind();
   }
   private static void unbind(){
      VertexArrays.unbind();
   }
   public void drawWithShader(Matrix4f modelViewMat, Matrix4f projMat, ShaderInstance shaderInstance) {
      shaderInstance.apply();
      UpdateShader(modelViewMat, projMat, shaderInstance);
      bind();
      RenderSystem.drawElements(GL46.GL_TRIANGLES, toDraw.indexCount, DataType.UINT.GLType);
      unbind();
      shaderInstance.clear();
   }
   public void draw(Matrix4f modelViewMat, Matrix4f proj) {
      drawWithShader(modelViewMat, proj, RenderSystem.getShader());
   }
   public void draw(Matrix4f modelViewMat) {
      draw(modelViewMat, RenderSystem.getProjectionMatrix());
   }
   
   
   
   int curlight = 0;
   

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
