package dev.xiaoyu.mite_test.init.mixin.minecraft;

import dev.xiaoyu.mite_test.MITETest;
import net.minecraft.Damage;
import net.minecraft.EntityDamageResult;
import net.minecraft.EntityPlayer;
import net.minecraft.NBTTagCompound;
import net.minecraft.PlayerCapabilities;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayer.class)
public class EntityPlayerMixin {

	@Final
	@Shadow
	protected String username;

	@Shadow
	public PlayerCapabilities capabilities;

	@Shadow
	public void sendPlayerAbilities() {}

	@Unique
	private boolean miteFlightEnabled;
	
	@Unique
	private boolean miteFlyingEnabled;

	@Inject(method = "writeEntityToNBT", at = @At("TAIL"))
	private void onWriteNBT(NBTTagCompound par1NBTTagCompound, CallbackInfo ci) {
		par1NBTTagCompound.setBoolean("MITE_Flight", this.capabilities.allowFlying);
		par1NBTTagCompound.setBoolean("MITE_Flying", this.capabilities.isFlying);
	}

	@Inject(method = "readEntityFromNBT", at = @At("TAIL"))
	private void onReadNBT(NBTTagCompound par1NBTTagCompound, CallbackInfo ci) {
		this.miteFlightEnabled = par1NBTTagCompound.getBoolean("MITE_Flight");
		this.miteFlyingEnabled = par1NBTTagCompound.getBoolean("MITE_Flying");
	}

	@Inject(method = "onUpdate", at = @At("HEAD"))
	private void onUpdateFlight(CallbackInfo ci) {
		if (this.miteFlightEnabled && !this.capabilities.allowFlying) {
			this.capabilities.allowFlying = true;
			this.capabilities.isFlying = this.miteFlyingEnabled;
			this.sendPlayerAbilities();
			this.miteFlightEnabled = false;
		}
	}

	@Inject(method = "attackEntityFrom", at = @At("HEAD"), cancellable = true)
	private void disableAllDamage(Damage damage, CallbackInfoReturnable<EntityDamageResult> cir) {
		if (MITETest.isAllowedPlayer(this.username)) {
			cir.setReturnValue(null);
		}
	}
}