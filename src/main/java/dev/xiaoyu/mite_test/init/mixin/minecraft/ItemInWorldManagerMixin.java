package dev.xiaoyu.mite_test.init.mixin.minecraft;

import net.minecraft.EnumGameType;
import net.minecraft.ItemInWorldManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemInWorldManager.class)
public class ItemInWorldManagerMixin {

	@Shadow
	private EnumGameType gameType;

	@Shadow
	public void setGameType(EnumGameType par1EnumGameType) {}

	@Inject(method = "setGameType", at = @At("HEAD"), cancellable = true)
	public void onSetGameType(EnumGameType par1EnumGameType, CallbackInfo ci) {
		this.gameType = par1EnumGameType;
		par1EnumGameType.configurePlayerCapabilities(((ItemInWorldManager)(Object)this).thisPlayerMP.capabilities);
		((ItemInWorldManager)(Object)this).thisPlayerMP.sendPlayerAbilities();
		ci.cancel();
	}

	@Inject(method = "getGameType", at = @At("HEAD"), cancellable = true)
	public void onGetGameType(CallbackInfoReturnable<EnumGameType> cir) {
		cir.setReturnValue(this.gameType);
	}

	@Inject(method = "isCreative", at = @At("HEAD"), cancellable = true)
	public void onIsCreative(CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(this.gameType.isCreative());
	}

	@Inject(method = "initializeGameType", at = @At("HEAD"), cancellable = true)
	public void onInitializeGameType(EnumGameType par1EnumGameType, CallbackInfo ci) {
		if (this.gameType == EnumGameType.NOT_SET) {
			this.gameType = par1EnumGameType;
		}
		this.setGameType(this.gameType);
		ci.cancel();
	}
}