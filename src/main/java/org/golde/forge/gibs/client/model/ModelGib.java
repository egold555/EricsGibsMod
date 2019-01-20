package org.golde.forge.gibs.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelGib extends ModelBase {

	public ModelRenderer box;

    public ModelGib() {
        this.box = new ModelRenderer(this, 0, 0);
    }

    public ModelGib(ModelRenderer model, boolean hideChild) {
        ModelModRenderer tempModel = new ModelModRenderer(model, this, !hideChild);

        this.box = tempModel;
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.box.render(f5);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {}
	
}
