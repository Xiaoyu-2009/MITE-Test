package dev.xiaoyu.mite_test.init.mixin.minecraft;

import net.minecraft.CommandGameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CommandGameMode.class)
public class CommandGameModeMixin {

	@Redirect(method = "processCommand", at = @At(value = "INVOKE", target = "Lnet/minecraft/Minecraft;inDevMode()Z"))
	public boolean redirectInDevMode() {
		return true;
	}
}