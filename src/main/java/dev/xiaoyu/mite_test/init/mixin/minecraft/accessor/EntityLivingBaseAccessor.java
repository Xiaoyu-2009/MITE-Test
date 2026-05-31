package dev.xiaoyu.mite_test.init.mixin.minecraft.accessor;

import net.minecraft.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityLivingBase.class)
public interface EntityLivingBaseAccessor {

	@Accessor
	boolean getMiteDebuffImmune();

	@Accessor
	void setMiteDebuffImmune(boolean value);
}