package roito.hikethecountryside.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import roito.hikethecountryside.HikeTheCountryside;
import roito.hikethecountryside.inventory.HCGuiElementRegistry;
import roito.hikethecountryside.tileentity.TileEntityFlatBasket;

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
		super(Material.WOOD, "flat_basket", HikeTheCountryside.TAB_CRAFT, 0.5F, false, false);
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState)
	{
		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_BOTTOM);
		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_SIDE1);
		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_SIDE2);
		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_SIDE3);
		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_SIDE4);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return AABB;
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return new TileEntityFlatBasket();
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		TileEntity tile = worldIn.getTileEntity(pos);
		if (tile instanceof TileEntityFlatBasket)
		{
			TileEntityFlatBasket te = (TileEntityFlatBasket) tile;
			IItemHandler inputInventory = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
			IItemHandler outputInventory = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);

			for (int i = inputInventory.getSlots() - 1; i >= 0; --i)
			{
				if (inputInventory.getStackInSlot(i) != ItemStack.EMPTY)
				{
					Block.spawnAsEntity(worldIn, pos, inputInventory.getStackInSlot(i));
					((IItemHandlerModifiable) inputInventory).setStackInSlot(i, ItemStack.EMPTY);
				}
			}

			for (int i = outputInventory.getSlots() - 1; i >= 0; --i)
			{
				if (outputInventory.getStackInSlot(i) != ItemStack.EMPTY)
				{
					Block.spawnAsEntity(worldIn, pos, outputInventory.getStackInSlot(i));
					((IItemHandlerModifiable) outputInventory).setStackInSlot(i, ItemStack.EMPTY);
				}
			}
		}

		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (!worldIn.isRemote)
		{
			int id = HCGuiElementRegistry.GUI_FLAT_BASKET;
			playerIn.openGui(HikeTheCountryside.instance, id, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
}
