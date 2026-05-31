package dev.xiaoyu.mite_test.mixin.minecraft;

import net.minecraft.EnumGameType;
import net.minecraft.ServerConfigurationManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerConfigurationManager.class)
public class ServerConfigurationManagerMixin {

	@Shadow
	private EnumGameType gameType;

	@Inject(method = "setGameType", at = @At("HEAD"), cancellable = true)
	public void setGameTypeUnrestricted(EnumGameType par1EnumGameType, CallbackInfo ci) {
		ci.cancel();
		this.gameType = par1EnumGameType;
	}
}