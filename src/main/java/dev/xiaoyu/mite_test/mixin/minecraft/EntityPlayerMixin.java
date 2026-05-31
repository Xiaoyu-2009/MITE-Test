package dev.xiaoyu.mite_test.mixin.minecraft;

import dev.xiaoyu.mite_test.MITETest;
import net.minecraft.Damage;
import net.minecraft.EntityDamageResult;
import net.minecraft.EntityPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayer.class)
public class EntityPlayerMixin {

	@Final
	@Shadow
	protected String username;

	@Inject(method = "attackEntityFrom", at = @At("HEAD"), cancellable = true)
	private void disableAllDamage(Damage damage, CallbackInfoReturnable<EntityDamageResult> cir) {
		if (MITETest.PLAYER_ID.equals(this.username)) {
			cir.setReturnValue(null);
		}
	}
}