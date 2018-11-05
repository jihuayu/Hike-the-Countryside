package roito.hikethecountryside.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import roito.hikethecountryside.common.CreativeTabsRegister;

import javax.annotation.Nullable;
import java.util.List;

public class BlockFlatBasket extends HCBlock
{
	protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125D, 1.0D);
	protected static final AxisAlignedBB AABB_BOTTOM = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D);
	protected static final AxisAlignedBB AABB_SIDE1 = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125D, 0.0625D);
	protected static final AxisAlignedBB AABB_SIDE2 = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0625D, 0.3125D, 1.0D);
	protected static final AxisAlignedBB AABB_SIDE3 = new AxisAlignedBB(0.9375D, 0.0D, 0.0D, 1.0D, 0.3125D, 1.0D);
	protected static final AxisAlignedBB AABB_SIDE4 = new AxisAlignedBB(0.0D, 0.0D, 0.9375D, 1.0D, 0.3125D, 1.0D);

	public BlockFlatBasket()
	{
		super(Material.WOOD, SoundType.WOOD, "flat_basket", CreativeTabsRegister.tabCraft, 0.5F, false, false);
	}

	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState)
	{
		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_BOTTOM);
		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_SIDE1);
		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_SIDE2);
		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_SIDE3);
		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_SIDE4);
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return AABB;
	}

}
