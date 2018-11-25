package roito.hikethecountryside.api.block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IBlockStove
{
	boolean isBurning(World worldIn, BlockPos pos);

	float getFuelEfficiency();
}
