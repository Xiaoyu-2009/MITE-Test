package dev.xiaoyu.mite_test.init.mixin.minecraft;

import dev.xiaoyu.mite_test.command.*;
import net.minecraft.ServerCommandManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerCommandManager.class)
public class ServerCommandManagerMixin {

	@Inject(method = "<init>", at = @At("RETURN"))
	private void onInit(CallbackInfo ci) {
		((ServerCommandManager)(Object)this).registerCommand(new CommandFlight());
		((ServerCommandManager)(Object)this).registerCommand(new CommandDebuff());
	}
}