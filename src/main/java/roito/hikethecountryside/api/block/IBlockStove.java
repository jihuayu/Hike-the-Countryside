package roito.hikethecountryside.api.block;

import net.minecraft.block.state.IBlockState;

public interface IBlockStove
{
	boolean isBurning(IBlockState state);

	float getFuelEfficiency();
}
