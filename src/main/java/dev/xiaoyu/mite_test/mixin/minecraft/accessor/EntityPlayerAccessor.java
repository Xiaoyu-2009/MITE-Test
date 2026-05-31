package dev.xiaoyu.mite_test.mixin.minecraft.accessor;

import net.minecraft.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityPlayer.class)
public interface EntityPlayerAccessor {
	@Accessor
	String getUsername();
}