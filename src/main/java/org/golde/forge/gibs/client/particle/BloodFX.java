package org.golde.forge.gibs.client.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class BloodFX extends Particle {

	public BloodFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, double mult) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
		
		this.particleGravity = 1.2F;
        this.particleRed = 1.0F;
        this.particleBlue = 0.0F;
        this.particleGreen = 0.0F;
        double scale = 1.5D * ((1.0D + mult) / 2.0D);

        this.multipleParticleScaleBy((float) scale);
        double expandBB = this.getBoundingBox().getAverageEdgeLength() * (scale - 1.0D);

        this.getBoundingBox().expand(expandBB, expandBB, expandBB);
        this.multiplyVelocity(1.2F);
        this.motionY += (double) (this.rand.nextFloat() * 0.15F);
        this.motionZ *= (double) (0.4F / (this.rand.nextFloat() * 0.9F + 0.1F));
        this.motionX *= (double) (0.4F / (this.rand.nextFloat() * 0.9F + 0.1F));
        this.particleMaxAge = (int) (200.0F + 20.0F / (this.rand.nextFloat() * 0.9F + 0.1F));
        this.setParticleTextureIndex(19 + this.rand.nextInt(4));
       // this.renderDistanceWeight = 10.0D;
		
	}
	
	@Override
	public int getBrightnessForRender(float p_189214_1_) {
		float f1 = super.getBrightnessForRender(p_189214_1_);
        float f2 = (float) (this.particleAge / this.particleMaxAge);

        f2 *= f2;
        f2 *= f2;
        return (int) (f1 * (1.0F - f2) + f2);
	}

	
	@Override
	public void onUpdate() { //not needed?
		

		if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setExpired();
        }

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (!this.onGround) {
            this.motionY -= 0.04D * (double) this.particleGravity;
            this.move(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.9800000190734863D;
            this.motionY *= 0.9800000190734863D;
            this.motionZ *= 0.9800000190734863D;
            if (this.onGround) {
                this.motionX *= 0.699999988079071D;
                this.motionZ *= 0.699999988079071D;
                this.posY += 0.1D;
            }
        }
	}
	
	
}

