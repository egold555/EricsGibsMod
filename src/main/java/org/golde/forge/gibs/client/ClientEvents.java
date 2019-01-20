package org.golde.forge.gibs.client;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.golde.forge.gibs.EntityUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientEvents {

	private HashMap<EntityLivingBase, Integer> dismemberTimeout = new HashMap<EntityLivingBase, Integer>();
	public long clock;
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onLivingDeath(LivingDeathEvent event) {
		if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
			dismemberTimeout.put(event.getEntityLiving(), Integer.valueOf(2));
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void clientWorldTick(ClientTickEvent event) {
		
		Minecraft mc = FMLClientHandler.instance().getClient();
		WorldClient world = mc.world;
		
		if (event.phase == Phase.END && world != null) {

			if (this.clock != world.getWorldTime() || !world.getGameRules().getBoolean("doDaylightCycle")) {
				this.clock = world.getWorldTime();

				for (int ite = 0; ite < world.loadedEntityList.size(); ++ite) {
					if (world.loadedEntityList.get(ite) instanceof EntityLivingBase) {
						EntityLivingBase e = (EntityLivingBase) world.loadedEntityList.get(ite);

						if (!e.isEntityAlive() && !this.dismemberTimeout.containsKey(e)) {
							this.dismemberTimeout.put(e, Integer.valueOf(2));
						}
					}
				}

				Iterator iterator = this.dismemberTimeout.entrySet().iterator();

				if (iterator.hasNext()) {
					Entry entry = (Entry) iterator.next();

					entry.setValue(Integer.valueOf(((Integer) entry.getValue()).intValue() - 1));
					((EntityLivingBase) entry.getKey()).hurtTime = 0;
					((EntityLivingBase) entry.getKey()).deathTime = 0;
					if (((Integer) entry.getValue()).intValue() <= 0) {
						EntityUtil.dismember(((EntityLivingBase) entry.getKey()).world, (EntityLivingBase) entry.getKey());
						int bloodCount = (int) ((EntityLivingBase) entry.getKey()).getMaxHealth() * 10;

						bloodCount = bloodCount > 400 ? 400 : (bloodCount < 20 ? 20 : bloodCount);
						EntityUtil.addBlood((EntityLivingBase) entry.getKey(), bloodCount);
						((EntityLivingBase) entry.getKey()).setDead();
						iterator.remove();
					}
				}
			}
		}

	}
}
