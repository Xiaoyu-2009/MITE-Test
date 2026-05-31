package dev.xiaoyu.mite_test.mixin.minecraft;

import net.minecraft.Packet70GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Packet70GameEvent.class)
public class Packet70GameEventMixin {

	@Redirect(method = "<init>(II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/Minecraft;inDevMode()Z"))
	public boolean redirectInDevMode() {
		return true;
	}
}