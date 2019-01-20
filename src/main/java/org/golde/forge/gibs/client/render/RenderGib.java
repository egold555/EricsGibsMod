package org.golde.forge.gibs.client.render;


import java.lang.reflect.Method;

import org.golde.forge.gibs.EntityUtil;
import org.golde.forge.gibs.ModGibs;
import org.golde.forge.gibs.client.entity.EntityGib;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderGib extends Render {

	public RenderGib(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return this.getEntityTexture2((EntityGib)entity);
	}
	
	@Override
	public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
		doRender2((EntityGib)entity, x, y, z, entityYaw, partialTicks);
	}

	private void doRender2(EntityGib entity, double x, double y, double z, float entityYaw, float partialTicks) {
		
		if(entity == null) {
			ModGibs.logger.warn("entity is null when trying to render dismembered gib!");
			return;
		}
		
		if(entity.modelGib == null) {
			ModGibs.logger.warn("model is null when trying to render dismembered gib!");
			return;
		}
		
        GL11.glPushMatrix();
        this.bindEntityTexture(entity);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        float alpha = 1.0F;

        if (entity.liveTime >= 400) {
            alpha = 1.0F - ((float) (entity.liveTime - 400) + partialTicks) / 20.0F;
        }

        if (alpha < 0.0F) {
            alpha = 0.0F;
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
        GL11.glAlphaFunc(516, 0.003921569F);
        GL11.glTranslatef((float) x, (float) y, (float) z);
        GL11.glRotatef(EntityUtil.interpolateRotation(entity.prevRotationYaw, entity.rotationYaw, partialTicks), 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(EntityUtil.interpolateRotation(entity.prevRotationPitch, entity.rotationPitch, partialTicks), -1.0F, 0.0F, 0.0F);
        GL11.glTranslatef(0.0F, 1.5F - entity.height * 0.5F, 0.0F);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        entity.modelGib.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glAlphaFunc(516, 0.1F);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    public ResourceLocation getEntityTexture2(EntityGib entity) {
        EntityLivingBase parent = entity.getParent();

        try {
            Method e = Minecraft.getMinecraft().getRenderManager().entityRenderMap.get(parent.getClass()).getClass().getDeclaredMethod("getEntityTexture", new Class[] { parent instanceof EntityPlayer ? AbstractClientPlayer.class : Entity.class});

            e.setAccessible(true);
            return (ResourceLocation) e.invoke(Minecraft.getMinecraft().getRenderManager().entityRenderMap.get(parent.getClass()), new Object[] { parent});
        } catch (Exception ex) {
           ex.printStackTrace();
        }
        return new ResourceLocation("missingno");
    }
	
}
