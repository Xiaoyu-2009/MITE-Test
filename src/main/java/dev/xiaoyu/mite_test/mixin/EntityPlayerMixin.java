package dev.xiaoyu.mite_test.mixin;

import net.minecraft.Damage;
import net.minecraft.EntityDamageResult;
import net.minecraft.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayer.class)
public class EntityPlayerMixin {

	@Inject(method = "attackEntityFrom", at = @At("HEAD"), cancellable = true)
	private void disableAllDamage(Damage damage, CallbackInfoReturnable<EntityDamageResult> cir) {
		cir.setReturnValue(null);
	}
}