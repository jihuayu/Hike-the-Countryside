package roito.hikethecountryside.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import roito.hikethecountryside.api.block.IBlockStove;
import roito.hikethecountryside.blocks.BlockStoveStone;
import roito.hikethecountryside.common.HCBlocksItemsRegistry;

import static net.minecraft.tileentity.TileEntityFurnace.getItemBurnTime;
import static net.minecraft.tileentity.TileEntityFurnace.isItemFuel;

public class TileEntityStove extends TileEntity implements ITickable
{
	protected int remainTicks = 0;
	protected int fuelTicks = 0;

	protected ItemStackHandler fuelInventory = new ItemStackHandler();

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability))
		{
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability))
		{
			return (T) fuelInventory;
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
	public NBTTagCompound getUpdateTag()
	{
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		NBTTagCompound nbtTag = new NBTTagCompound();
		this.writeToNBT(nbtTag);
		return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet)
	{
		this.readFromNBT(packet.getNbtCompound());
	}

	@Override
	public void update()
	{
		if (!this.getWorld().isRemote)
		{
			if(this.getWorld().getBlockState(pos.up()).getBlock().equals(HCBlocksItemsRegistry.BLOCK_FLAT_BASKET))
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
		if (this.remainTicks > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
	{
		return oldState.getBlock() != newSate.getBlock();
	}
}
