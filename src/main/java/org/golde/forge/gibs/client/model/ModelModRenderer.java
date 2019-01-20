package org.golde.forge.gibs.client.model;

import java.lang.reflect.Field;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;

public class ModelModRenderer extends ModelRenderer {

	public ModelModRenderer(ModelRenderer renderer, ModelBase baseModel, boolean copyChildModels) {
        super(baseModel, renderer.boxName);

        Field e1;
        int texX;

        try {
            this.textureWidth = renderer.textureWidth;
            this.textureHeight = renderer.textureHeight;
            Field e = ModelRenderer.class.getDeclaredField("textureOffsetX");

            e.setAccessible(true);
            e1 = ModelRenderer.class.getDeclaredField("textureOffsetY");
            e1.setAccessible(true);
            int field11 = Integer.valueOf(e.get(renderer).toString()).intValue();

            texX = Integer.valueOf(e1.get(renderer).toString()).intValue();
            this.setTextureOffset(field11, texX);
            this.rotateAngleX = renderer.rotateAngleX;
            this.rotateAngleY = renderer.rotateAngleY;
            this.rotateAngleZ = renderer.rotateAngleZ;
            this.childModels = copyChildModels ? renderer.childModels : null;
            this.cubeList = renderer.cubeList;
            ModelBox texY1 = (ModelBox) renderer.cubeList.get(0);

            this.rotationPointY = 24.0F - texY1.posY2;
        } catch (Exception exception) {
            try {
                this.textureWidth = renderer.textureWidth;
                this.textureHeight = renderer.textureHeight;
                e1 = ModelRenderer.class.getDeclaredField("textureOffsetX");
                e1.setAccessible(true);
                Field field1 = ModelRenderer.class.getDeclaredField("textureOffsetY");

                field1.setAccessible(true);
                texX = Integer.valueOf(e1.get(renderer).toString()).intValue();
                int texY = Integer.valueOf(field1.get(renderer).toString()).intValue();

                this.setTextureOffset(texX, texY);
                this.rotateAngleX = renderer.rotateAngleX;
                this.rotateAngleY = renderer.rotateAngleY;
                this.rotateAngleZ = renderer.rotateAngleZ;
                this.childModels = copyChildModels ? renderer.childModels : null;
                this.cubeList = renderer.cubeList;
                ModelBox box = (ModelBox) renderer.cubeList.get(0);

                this.rotationPointY = 24.0F - box.posY2;
            } catch (Exception exception1) {
                throw new RuntimeException(exception);
            }
        }

    }
	
}

