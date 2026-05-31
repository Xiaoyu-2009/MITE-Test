package dev.xiaoyu.mite_test.init.mixin.minecraft;

import net.minecraft.Packet70GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Packet70GameEvent.class)
public class Packet70GameEventMixin {

	@Unique
    private static final ThreadLocal<Integer> originalGameMode = new ThreadLocal<>();

	@ModifyVariable(method = "<init>(II)V", at = @At("HEAD"), argsOnly = true, index = 2)
	private static int captureOriginalGameMode(int par2) {
		originalGameMode.set(par2);
		return par2;
	}

	@Inject(method = "<init>(II)V", at = @At("RETURN"))
	public void restoreOriginalGameMode(int par1, int par2, CallbackInfo ci) {
		Integer original = originalGameMode.get();
		if (par1 == 3 && original != null) {
			((Packet70GameEvent)(Object)this).gameMode = original;
		}
		originalGameMode.remove();
	}
}