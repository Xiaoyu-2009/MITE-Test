package dev.xiaoyu.mite_test.mixin;

import net.minecraft.FoodStats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodStats.class)
public class FoodStatsMixin {

	@Inject(method = "addHunger", at = @At("HEAD"), cancellable = true)
	private void disableHungerConsumption(float hunger, CallbackInfo ci) {
		ci.cancel();
	}
}