package dev.xiaoyu.mite_test.init.mixin.minecraft;

import net.minecraft.GuiButton;
import net.minecraft.GuiCreateWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiCreateWorld.class)
public class GuiCreateWorldMixin {

	@Shadow
	private String gameMode;

	@Shadow
	private boolean isHardcore;

	@Shadow
	private boolean commandsAllowed;

	@Shadow
	private boolean commandsToggled;

	@Shadow
	private GuiButton buttonAllowCommands;

	@Shadow
	private GuiButton buttonBonusItems;

	@Shadow
	private void updateButtonText() {}

	@Inject(method = "actionPerformed", at = @At("HEAD"), cancellable = true)
	public void handleGameModeButton(GuiButton par1GuiButton, CallbackInfo ci) {
		if (par1GuiButton.id == 2) {
			ci.cancel();
			if (this.gameMode.equals("survival")) {
				if (!this.commandsToggled) {
					this.commandsAllowed = false;
				}
				this.gameMode = "hardcore";
				this.isHardcore = true;
				this.buttonAllowCommands.enabled = false;
				this.buttonBonusItems.enabled = false;
			} else if (this.gameMode.equals("hardcore")) {
				if (!this.commandsToggled) {
					this.commandsAllowed = false;
				}
				this.gameMode = "creative";
				this.isHardcore = false;
				this.buttonAllowCommands.enabled = true;
				this.buttonBonusItems.enabled = true;
			} else {
				if (!this.commandsToggled) {
					this.commandsAllowed = false;
				}
				this.gameMode = "survival";
				this.isHardcore = false;
				this.buttonAllowCommands.enabled = true;
				this.buttonBonusItems.enabled = true;
			}
			this.updateButtonText();
		}
	}
}