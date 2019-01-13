package roito.hikethecountryside.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import roito.hikethecountryside.api.block.IBlockStove;
import roito.hikethecountryside.blocks.BlockStoveStone;
import roito.hikethecountryside.common.HCBlocksItemsRegistry;
import snownee.kiwi.tile.TileInventoryBase;

import static net.minecraft.tileentity.TileEntityFurnace.getItemBurnTime;
import static net.minecraft.tileentity.TileEntityFurnace.isItemFuel;

public class TileEntityStove extends TileInventoryBase implements ITickable
{
	public TileEntityStove() {
		super(1);
	}

	protected int remainTicks = 0;
	protected int fuelTicks = 0;

	protected ItemStackHandler fuelInventory = new ItemStackHandler();

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY == capability)
		{
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY == capability)
		{
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(fuelInventory);
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.fuelInventory.deserializeNBT(compound.getCompoundTag("FuelInventory"));
		this.fuelTicks = compound.getInteger("FuelTicks");
		this.remainTicks = compound.getInteger("RemainTicks");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		compound.setTag("FuelInventory", this.fuelInventory.serializeNBT());
		compound.setInteger("FuelTicks", this.fuelTicks);
		compound.setInteger("RemainTicks", this.remainTicks);
		return super.writeToNBT(compound);
	}

	@Override
	public void update()
	{
		if (!this.getWorld().isRemote)
		{
			if(this.getWorld().getBlockState(pos.up()).getBlock() == HCBlocksItemsRegistry.BLOCK_FLAT_BASKET)
			{
				this.hasFuelOrIsBurning();
			}
			if (this.remainTicks > 0)
			{
				this.remainTicks--;
				this.markDirty();
			}
			else
			{
				BlockStoveStone.setState(false, this.getWorld(), pos);
			}
			refresh();
		}
	}

	public boolean hasFuelOrIsBurning()
	{
		if (this.isBurning())
		{
			return true;
		}
		else
		{
			ItemStack itemFuel = fuelInventory.extractItem(0, 1, true);
			if (isItemFuel(itemFuel))
			{
				float efficiency = 0.0F;
				if (this.getBlockType() instanceof IBlockStove)
				{
					efficiency = ((IBlockStove)this.getBlockType()).getFuelEfficiency();
				}
				this.fuelTicks = (int)(getItemBurnTime(itemFuel) * efficiency);
				this.remainTicks = (int)(getItemBurnTime(itemFuel) * efficiency);
				Item cItem = fuelInventory.getStackInSlot(0).getItem().getContainerItem();
				if (cItem != null)
				{
					fuelInventory.extractItem(0, 1, false);
					fuelInventory.insertItem(0, new ItemStack(cItem, 1), false);
				}
				else
				{
					fuelInventory.extractItem(0, 1, false);
				}
				this.markDirty();
				BlockStoveStone.setState(true, this.getWorld(), pos);
				return true;
			}
			else
			{
				return false;
			}
		}
	}

	public boolean isBurning()
	{
		return this.remainTicks > 0;
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
		return oldState.getBlock() != newState.getBlock();
	}

	public NonNullList<ItemStack> getContents()
	{
		NonNullList<ItemStack> list = NonNullList.create();
		for (int i = this.fuelInventory.getStackInSlot(0).getCount(); i > 0; i -= 4)
		{
			list.add(this.fuelInventory.getStackInSlot(0));
		}
		return list;
	}
}
