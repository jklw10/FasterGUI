package dev.tr7zw.fastergui.util;

import com.mojang.math.Vector3f;

import dev.tr7zw.fastergui.util.Model.Vector2f;

public class DefaultModel {
    public static Model QUAD = makeModel();
    private static Model makeModel(){
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
        return new Model(modelData, uvData, indices);
    }
}
