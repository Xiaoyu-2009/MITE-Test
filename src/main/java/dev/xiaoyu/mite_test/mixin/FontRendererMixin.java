package dev.xiaoyu.mite_test.mixin;

import net.minecraft.ChatAllowedCharacters;
import net.minecraft.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FontRenderer.class)
public class FontRendererMixin {

	@Shadow
	private int[] charWidth;

	@Shadow
	private byte[] glyphWidth;

	@Shadow
	private boolean unicodeFlag;

	@Inject(method = "getCharWidth", at = @At("HEAD"), cancellable = true)
	private void safeGetCharWidth(char par1, CallbackInfoReturnable<Integer> cir) {
		if (par1 == '§') {
			cir.setReturnValue(-1);
		}
		if (par1 == ' ') {
			cir.setReturnValue(4);
		}
		int var2 = ChatAllowedCharacters.allowedCharacters.indexOf(par1);
		if (var2 >= 0 && !this.unicodeFlag) {
			int index = var2 + 32;
			if (index >= 0 && index < this.charWidth.length) {
				return;
			}
			if (par1 < this.glyphWidth.length && this.glyphWidth[par1] != 0) {
				int var3 = this.glyphWidth[par1] >>> 4;
				int var4 = this.glyphWidth[par1] & 0xF;
				if (var4 > 7) {
					var4 = 15;
					var3 = 0;
				}
				cir.setReturnValue((++var4 - var3) / 2 + 1);
			} else {
				cir.setReturnValue(0);
			}
		}
	}

	@Inject(method = "renderCharAtPos", at = @At("HEAD"), cancellable = true)
	private void safeRenderCharAtPos(int par1, char par2, boolean par3, CallbackInfoReturnable<Float> cir) {
		if (par1 > 0 && !this.unicodeFlag && par1 + 32 >= this.charWidth.length) {
			cir.setReturnValue(this.renderUnicodeChar(par2, par3));
		}
	}

	@Inject(method = "renderDefaultChar", at = @At("HEAD"), cancellable = true)
	private void safeRenderDefaultChar(int par1, boolean par2, CallbackInfoReturnable<Float> cir) {
		if (par1 < 0 || par1 >= this.charWidth.length) {
			cir.setReturnValue(0.0F);
		}
	}

	@Shadow
	private float renderUnicodeChar(char par1, boolean par2) {
		throw new AbstractMethodError();
	}
}