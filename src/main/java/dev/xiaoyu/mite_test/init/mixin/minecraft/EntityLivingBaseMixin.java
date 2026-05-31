package dev.xiaoyu.mite_test.init.mixin.minecraft;

import net.minecraft.EntityLivingBase;
import net.minecraft.NBTTagCompound;
import net.minecraft.Potion;
import net.minecraft.PotionEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityLivingBase.class)
public class EntityLivingBaseMixin {

	@Unique
	private boolean miteDebuffImmune;

	@Inject(method = "addPotionEffect", at = @At("HEAD"), cancellable = true)
	private void onAddPotionEffect(PotionEffect par1PotionEffect, CallbackInfo ci) {
		if (this.miteDebuffImmune) {
			Potion potion = Potion.get(par1PotionEffect.getPotionID());
			if (potion != null && potion.isBadEffect()) {
				ci.cancel();
			}
		}
	}

	@Inject(method = "writeEntityToNBT", at = @At("TAIL"))
	private void onWriteNBT(NBTTagCompound par1NBTTagCompound, CallbackInfo ci) {
		par1NBTTagCompound.setBoolean("MITE_DebuffImmune", this.miteDebuffImmune);
	}

	@Inject(method = "readEntityFromNBT", at = @At("TAIL"))
	private void onReadNBT(NBTTagCompound par1NBTTagCompound, CallbackInfo ci) {
		this.miteDebuffImmune = par1NBTTagCompound.getBoolean("MITE_DebuffImmune");
	}
}