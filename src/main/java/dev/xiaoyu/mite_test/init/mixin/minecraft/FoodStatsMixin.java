package dev.xiaoyu.mite_test.init.mixin.minecraft;

import dev.xiaoyu.mite_test.MITETest;
import dev.xiaoyu.mite_test.init.mixin.minecraft.accessor.EntityPlayerAccessor;
import net.minecraft.EntityPlayer;
import net.minecraft.FoodStats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodStats.class)
public class FoodStatsMixin {

	@Shadow
	private EntityPlayer player;

	@Inject(method = "addHunger", at = @At("HEAD"), cancellable = true)
	private void disableHungerConsumption(float hunger, CallbackInfo ci) {
		if (MITETest.isAllowedPlayer(((EntityPlayerAccessor)this.player).getUsername())) {
			ci.cancel();
		}
	}
}