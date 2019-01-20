package org.golde.forge.gibs;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

import org.golde.forge.gibs.client.entity.EntityGib;
import org.golde.forge.gibs.client.particle.BloodFX;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityUtil {

	@SideOnly(Side.CLIENT)
	public static void dismember(World world, EntityLivingBase living) {
		
		if(living == null) {
			ModGibs.logger.warn("entity is null when trying to dismember entity!");
			return;
		}
		
		ModelBase model = getEntityModel(living);
		
		if(model == null) {
			ModGibs.logger.warn("model is null when trying to dismember entity!");
			return;
		}
		
		ArrayList childs = new ArrayList();
		Iterator i$ = model.boxList.iterator();

		Object m;

		while (i$.hasNext()) {
			m = i$.next();
			if (((ModelRenderer) m).childModels != null) {
				childs.addAll(((ModelRenderer) m).childModels);
			}
		}

		i$ = model.boxList.iterator();

		while (i$.hasNext()) {
			m = i$.next();
			if (!childs.contains((ModelRenderer) m)) {
				ModelRenderer temp = (ModelRenderer) m;

				world.spawnEntity(new EntityGib(world, living, temp, true));
				if (((ModelRenderer) m).childModels != null) {
					Iterator i$1 = temp.childModels.iterator();

					while (i$1.hasNext()) {
						Object n = i$1.next();
						ModelRenderer temp1 = (ModelRenderer) n;
						world.spawnEntity(new EntityGib(world, living, temp1, false));
					}
				}
			}
		}

	}

	@SideOnly(Side.CLIENT)
	private static ModelBase getEntityModel(EntityLivingBase entity) {
		
		if(entity instanceof EntityPlayer) {
			return null;
		}
		
		RenderLivingBase renderer = (RenderLivingBase) Minecraft.getMinecraft().getRenderManager().entityRenderMap.get(entity.getClass());

		renderer.doRender(entity, entity.posX, entity.posY, entity.posZ, 0.0F, 1.0F);
		ModelBase model = null;

		try {
			
			Field theField = null;
			
			for(Field f : RenderLivingBase.class.getDeclaredFields()) {
				if(f.getType().isAssignableFrom(ModelBase.class)) {
					theField = f;
					ModGibs.logger.info("Found ModelBase field: " + f.getName());
				}
			}
			
			if(theField != null) {
				theField.setAccessible(true);
				model = (ModelBase) theField.get(renderer);
			}
			else {
				ModGibs.logger.error("Found not find the obfuscated field of 'mainModel' with the type of ModeBase. You should not see this message.");
			}
			
			
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}

		return model;
	}
	
	@SideOnly(Side.CLIENT)
	public static void addBlood(EntityLivingBase living, int amount) {
		for (int k = 0; k < amount; ++k) {
			float f = 0.3F;
			double mX = (double) (-MathHelper.sin(living.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(living.rotationPitch / 180.0F * 3.1415927F) * f);
			double mZ = (double) (MathHelper.cos(living.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(living.rotationPitch / 180.0F * 3.1415927F) * f);
			double mY = (double) (-MathHelper.sin(living.rotationPitch / 180.0F * 3.1415927F) * f + 0.1F);

			f = 0.02F;
			float f1 = living.getRNG().nextFloat() * 3.1415927F * 2.0F;

			f *= living.getRNG().nextFloat();
			mX += Math.cos((double) f1) * (double) f;
			mY += (double) ((living.getRNG().nextFloat() - living.getRNG().nextFloat()) * 0.1F);
			mZ += Math.sin((double) f1) * (double) f;
			BloodFX blood = new BloodFX(living.world, living.posX, living.posY + 0.5D + living.getRNG().nextDouble() * 0.7D, living.posZ, living.motionX * 2.0D + mX, living.motionY + mY, living.motionZ * 2.0D + mZ, 1.5D);

			Minecraft.getMinecraft().effectRenderer.addEffect(blood);
		}

	}
	
	public static float interpolateRotation(float prevRotation, float nextRotation, float partialTick) {
		float f3;

		for (f3 = nextRotation - prevRotation; f3 < -180.0F; f3 += 360.0F) {
			;
		}

		while (f3 >= 180.0F) {
			f3 -= 360.0F;
		}

		return prevRotation + partialTick * f3;
	}
	
}
