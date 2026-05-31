package dev.xiaoyu.mite_test.init.mixin.minecraft;

import net.minecraft.EnumGameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnumGameType.class)
public class EnumGameTypeMixin {

	@Inject(method = "isCreative", at = @At("HEAD"), cancellable = true)
	public void isCreativeUnrestricted(CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue((Object)this == EnumGameType.CREATIVE);
	}

	@Inject(method = "isSurvivalOrAdventure", at = @At("HEAD"), cancellable = true)
	public void isSurvivalOrAdventureNormal(CallbackInfoReturnable<Boolean> cir) {
		EnumGameType self = (EnumGameType)(Object)this;
		cir.setReturnValue(self == EnumGameType.SURVIVAL || self == EnumGameType.ADVENTURE);
	}

	@Inject(method = "getByID", at = @At("HEAD"), cancellable = true)
	private static void getByIDUnrestricted(int par0, CallbackInfoReturnable<EnumGameType> cir) {
		for (EnumGameType type : EnumGameType.values()) {
			if (type.getID() == par0) {
				cir.setReturnValue(type);
				return;
			}
		}
		cir.setReturnValue(EnumGameType.SURVIVAL);
	}

	@Inject(method = "getByName", at = @At("HEAD"), cancellable = true)
	private static void getByNameUnrestricted(String par0Str, CallbackInfoReturnable<EnumGameType> cir) {
		for (EnumGameType type : EnumGameType.values()) {
			if (type.getName().equals(par0Str)) {
				cir.setReturnValue(type);
				return;
			}
		}
		cir.setReturnValue(EnumGameType.SURVIVAL);
	}
}