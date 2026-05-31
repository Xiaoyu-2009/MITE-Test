package dev.xiaoyu.mite_test.init.mixin.minecraft;

import net.minecraft.server.MinecraftServer;
import net.minecraft.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

	@Shadow
	public MinecraftServer mcServer;

	@Inject(method = "canCommandSenderUseCommand", at = @At("HEAD"), cancellable = true)
	public void onCanCommandSenderUseCommand(int par1, String par2Str, CallbackInfoReturnable<Boolean> cir) {
		if (par1 <= 0) {
			cir.setReturnValue(true);
		} else {
			cir.setReturnValue(this.mcServer.getConfigurationManager().isPlayerOpped(((ServerPlayer)(Object)this).getCommandSenderName()) && this.mcServer.func_110455_j() >= par1);
		}
	}
}