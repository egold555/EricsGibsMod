package org.golde.forge.gibs;

import org.apache.logging.log4j.Logger;
import org.golde.forge.gibs.client.ClientEvents;
import org.golde.forge.gibs.client.entity.EntityGib;
import org.golde.forge.gibs.client.render.RenderGib;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = ModGibs.MOD_ID, clientSideOnly = true, name = "Eric's Gibs", version = "1.0.1", useMetadata = true, acceptedMinecraftVersions = "[1.12]", canBeDeactivated = false)
public class ModGibs {

	public static final String MOD_ID = "gibs";

	public static Logger logger;

	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		
		if(event.getSide() == Side.CLIENT) {
			MinecraftForge.EVENT_BUS.register(new ClientEvents());
			
			EntityRegistry.registerModEntity(new ResourceLocation(ModGibs.MOD_ID, "entityGib"), EntityGib.class, "entityGib", 1, this, 64, 10, false); //CLIENT SIDE ENTITY
			RenderingRegistry.registerEntityRenderingHandler(EntityGib.class, RenderFactoryEntityGib.INSTANCE);
			
		}
		
	}

	@SideOnly(Side.CLIENT)
	public static class RenderFactoryEntityGib implements IRenderFactory<EntityGib> {
		public final static RenderFactoryEntityGib INSTANCE = new RenderFactoryEntityGib();

		@Override
		public Render<EntityGib> createRenderFor(RenderManager manager)
		{
			return new RenderGib(manager);
		}
	}

}
