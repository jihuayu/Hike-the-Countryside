package roito.hikethecountryside.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import roito.hikethecountryside.HikeTheCountryside;
import roito.hikethecountryside.api.block.IBlockStove;
import roito.hikethecountryside.common.HCBlocksItemsRegistry;
import roito.hikethecountryside.tileentity.TileEntityStove;

import javax.annotation.Nullable;

public class BlockStoveStone extends HCBlockHorizontal implements IBlockStove, ITileEntityProvider
{
	public float efficiency;

	public BlockStoveStone(float lightLevel, float efficiency, String name)
	{
		super(Material.ROCK, SoundType.STONE, name, lightLevel == 0.0F ? HikeTheCountryside.TAB_CRAFT : null, 3.5F, false, false);
		this.setLightLevel(lightLevel);
		this.efficiency = efficiency;
	}

	@Override
	public boolean isBurning(World worldIn, BlockPos pos)
	{
		return worldIn.getBlockState(pos).getBlock().equals(HCBlocksItemsRegistry.BLOCK_LIT_STOVE_STONE);
	}

	@Override
	public float getFuelEfficiency()
	{
		return efficiency;
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityStove();
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		if (worldIn.getTileEntity(pos) != null && (worldIn.getTileEntity(pos) instanceof TileEntityStove))
		{
			TileEntityStove te = (TileEntityStove) worldIn.getTileEntity(pos);
			IItemHandler fuelInventory = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);

			for (int i = fuelInventory.getSlots() - 1; i >= 0; --i)
			{
				if (fuelInventory.getStackInSlot(i) != ItemStack.EMPTY)
				{
					Block.spawnAsEntity(worldIn, pos, fuelInventory.getStackInSlot(i));
					((IItemHandlerModifiable) fuelInventory).setStackInSlot(i, ItemStack.EMPTY);
				}
			}
		}

		super.breakBlock(worldIn, pos, state);
	}

	public static void setState(boolean active, World worldIn, BlockPos pos)
	{
		IBlockState iblockstate = worldIn.getBlockState(pos);
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (active)
		{
			worldIn.setBlockState(pos, HCBlocksItemsRegistry.BLOCK_LIT_STOVE_STONE.getDefaultState().withProperty(BlockHorizontal.FACING, iblockstate.getValue(BlockHorizontal.FACING)));
		}
		else
		{
			worldIn.setBlockState(pos, HCBlocksItemsRegistry.BLOCK_STOVE_STONE.getDefaultState().withProperty(BlockHorizontal.FACING, iblockstate.getValue(BlockHorizontal.FACING)));
		}

		if (tileentity != null)
		{
			tileentity.validate();
			worldIn.setTileEntity(pos, tileentity);
		}
	}
}
