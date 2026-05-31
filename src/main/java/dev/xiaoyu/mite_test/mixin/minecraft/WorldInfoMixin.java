package dev.xiaoyu.mite_test.mixin.minecraft;

import net.minecraft.EnumGameType;
import net.minecraft.WorldInfo;
import net.minecraft.WorldInfoShared;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldInfo.class)
public class WorldInfoMixin {

	@Shadow
	private WorldInfoShared shared;

	@Inject(method = "setGameType", at = @At("HEAD"), cancellable = true)
	public void setGameTypeUnrestricted(EnumGameType par1EnumGameType, CallbackInfo ci) {
		ci.cancel();
		this.shared.theGameType = par1EnumGameType;
	}

	@Inject(method = "getGameType*", at = @At("HEAD"), cancellable = true)
	public void getGameTypeUnrestricted(CallbackInfoReturnable<EnumGameType> cir) {
		cir.setReturnValue(this.shared.theGameType);
	}
}