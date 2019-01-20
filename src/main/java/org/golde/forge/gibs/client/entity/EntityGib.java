package org.golde.forge.gibs.client.entity;

import org.golde.forge.gibs.ModGibs;
import org.golde.forge.gibs.client.model.ModelGib;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

//This entity is a client side entity, things are strange
public class EntityGib extends Entity {

	private float pitchSpin;
    private EntityLivingBase parent;
    public int liveTime = 0;
    public ModelGib modelGib;
    private float yawSpin;
	
	public EntityGib(World worldIn) {
		super(worldIn);
		//this.renderDistanceWeight = 10.0D;
        this.ignoreFrustumCheck = true;
	}

	public EntityGib(World world, EntityLivingBase parent, ModelRenderer model, boolean hideChild) {
		super(world);
		this.parent = parent;
        this.modelGib = new ModelGib(model, hideChild);
        this.setSize(0.4F, 1.0F);
        
        if(parent == null) {
        	ModGibs.logger.warn("parent is null when trying to spawn dismembered gib!");
        	setDead();
        	//return;
        }
        
        if(parent.getEntityBoundingBox() == null) {
        	ModGibs.logger.warn("parent.getEntityBoundingBox() is null when trying to spawn dismembered gib!");
        	setDead();
        	//return;
        }
        
        if(model == null) {
        	ModGibs.logger.warn("model is null when trying to spawn dismembered gib!");
        	setDead();
        	//return;
        }
        
        this.setLocationAndAngles(parent.posX, parent.getEntityBoundingBox().minY + (double) ((24.0F - model.rotationPointY) / 16.0F), parent.posZ, parent.rotationYaw, parent.rotationPitch);
        this.motionX = parent.motionX + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.25D;
        this.motionY = parent.motionY;
        this.motionZ = parent.motionZ + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.25D;
        float i = (float) this.rand.nextInt(45) + 5.0F + this.rand.nextFloat();

        if (this.rand.nextInt(2) == 0) {
            i *= -1.0F;
        }

        float j = (float) this.rand.nextInt(45) + 5.0F + this.rand.nextFloat();

        if (this.rand.nextInt(2) == 0) {
            j *= -1.0F;
        }

        this.pitchSpin = i * (float) (this.motionY + 0.3D);
        this.yawSpin = j * (float) (Math.sqrt(this.motionX * this.motionZ) + 0.3D);
        this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
	}
	
	@Override
	public void onUpdate() {
        super.onUpdate();
        this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        this.motionY -= 0.08D;
        this.motionY *= 0.98D;
        this.motionX *= 0.91D;
        this.motionZ *= 0.91D;
        if (!this.onGround && !this.handleWaterMovement()) {
            this.rotationPitch += this.pitchSpin;
            this.rotationYaw += this.yawSpin;
            this.pitchSpin *= 0.98F;
            this.yawSpin *= 0.98F;
        } else {
            this.rotationPitch += (-90.0F - this.rotationPitch % 360.0F) / 2.0F;
            this.motionY *= 0.8D;
            this.motionX *= 0.8D;
            this.motionZ *= 0.8D;
        }

        if (++this.liveTime > 600) {
            this.setDead();
        }

    }
	
	public EntityLivingBase getParent() {
        return this.parent;
    }
	
	@Override
	public boolean writeToNBTOptional(NBTTagCompound compound) {
		return false;
	}
	
	@Override
	public void fall(float distance, float damageMultiplier) {}

	@Override
	protected void entityInit() {}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {}

}

