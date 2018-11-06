package roito.hikethecountryside.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import roito.hikethecountryside.api.recipe.FlatBasketRecipe;
import roito.hikethecountryside.api.recipe.HCRecipeRegister;
import roito.hikethecountryside.api.recipe.IFlatBasketRecipe;
import roito.hikethecountryside.config.ConfigMain;
import roito.hikethecountryside.helper.NonNullListHelper;

public class TileEntityFlatBasket extends TileEntity implements ITickable
{
	protected int processTicks = 0;
	protected int totalTicks = 0;

	protected ItemStackHandler inputInventory = new ItemStackHandler(1);
	protected ItemStackHandler outputInventory = new ItemStackHandler(1);

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
			if (facing != EnumFacing.DOWN)
			{
				return (T) inputInventory;
			}
			else
			{
				return (T) outputInventory;
			}
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.inputInventory.deserializeNBT(compound.getCompoundTag("InputInventory"));
		this.outputInventory.deserializeNBT(compound.getCompoundTag("OutputInventory"));
		this.processTicks = compound.getInteger("ProcessTick");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		compound.setTag("InputInventory", this.inputInventory.serializeNBT());
		compound.setTag("OutputInventory", this.outputInventory.serializeNBT());
		compound.setInteger("ProcessTick", this.processTicks);
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
		if (!world.isRemote)
		{
			ItemStack input = this.inputInventory.getStackInSlot(0).copy();
			IFlatBasketRecipe recipeIn = new FlatBasketRecipe(NonNullListHelper.createNonNullList(input), ItemStack.EMPTY);
			refreshTotalTicks();
			if (isInRain())
			{
				IFlatBasketRecipe recipeUse = new FlatBasketRecipe(NonNullListHelper.createNonNullList(ItemStack.EMPTY), ItemStack.EMPTY);
				for (IFlatBasketRecipe recipe : HCRecipeRegister.managerFlatBasketWet.getRecipes())
				{
					if (recipe.equals(recipeIn))
					{
						recipeUse = recipe;
						break;
					}
				}
				if (!recipeUse.getOutput().isEmpty())
				{
					ItemStack wetOutput = recipeUse.getOutput().copy();
					wetOutput.setCount(inputInventory.getStackInSlot(0).getCount());
					this.inputInventory.setStackInSlot(0, wetOutput);
					refresh();
				}

				IFlatBasketRecipe recipeOut = new FlatBasketRecipe(NonNullListHelper.createNonNullList(outputInventory.getStackInSlot(0).copy()), ItemStack.EMPTY);
				recipeUse = new FlatBasketRecipe(NonNullListHelper.createNonNullList(ItemStack.EMPTY), ItemStack.EMPTY);
				for (IFlatBasketRecipe recipe : HCRecipeRegister.managerFlatBasketWet.getRecipes())
				{
					if (recipe.equals(recipeOut))
					{
						recipeUse = recipe;
						break;
					}
				}
				if (!recipeUse.getOutput().isEmpty())
				{
					ItemStack wetOutput = recipeUse.getOutput().copy();
					wetOutput.setCount(outputInventory.getStackInSlot(0).getCount());
					this.outputInventory.setStackInSlot(0, wetOutput);
					refresh();
				}
				this.processTicks = 0;
				this.markDirty();
				return;
			}
			if (getMode() == 0 && !isWorldRaining())
			{
				IFlatBasketRecipe recipeUse = new FlatBasketRecipe(NonNullListHelper.createNonNullList(ItemStack.EMPTY), ItemStack.EMPTY);
				for (IFlatBasketRecipe recipe : HCRecipeRegister.managerFlatBasketDrying.getRecipes())
				{
					if (recipe.equals(recipeIn))
					{
						recipeUse = recipe;
						break;
					}
				}
				if (!recipeUse.getOutput().isEmpty())
				{
					ItemStack output = recipeUse.getOutput().copy();
					output.setCount(input.getCount());
					if (judge(output))
					{
						this.outputInventory.insertItem(0, output, false);
						this.inputInventory.extractItem(0, output.getCount(), false);
						refresh();
						return;
					}
					else
					{
						return;
					}
				}
			}
			else if (getMode() == 1)
			{

			}
			else
			{

			}
			this.processTicks = 0;
			this.markDirty();
			return;
		}
	}

	private boolean judge(ItemStack itemStack)
	{
		if (this.outputInventory.insertItem(0, itemStack, true).isEmpty())
		{
			if (++this.processTicks >= this.totalTicks)
			{
				this.markDirty();
				this.processTicks = 0;
				return true;
			}
			this.markDirty();
		}
		return false;
	}

	public int getMode()
	{
		if (this.hasHeat())
		{
			return 2;
		}
		else if (!this.getWorld().canSeeSky(this.getPos()))
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}

	public NonNullList<ItemStack> getContents()
	{
		if (!this.outputInventory.getStackInSlot(0).isEmpty())
		{
			return NonNullListHelper.createNonNullList(this.outputInventory.getStackInSlot(0));
		}
		else
		{
			return NonNullListHelper.createNonNullList(this.inputInventory.getStackInSlot(0));
		}
	}

	public void refreshTotalTicks()
	{
		if (inputInventory.getStackInSlot(0).getCount() >= 32)
		{
			this.totalTicks = 32;
		}
		else if (inputInventory.getStackInSlot(0).getCount() <= 8)
		{
			this.totalTicks = 8;
		}
		else
		{
			this.totalTicks = inputInventory.getStackInSlot(0).getCount();
		}
		this.totalTicks *= ConfigMain.craft.dryingBasicTime;
	}

	public int getTotalTicks()
	{
		return this.totalTicks;
	}

	public boolean isWorldRaining()
	{
		return this.getWorld().isRaining();
	}

	public boolean isInRain()
	{
		return this.getWorld().isRainingAt(pos.up());
	}

	public boolean hasEnoughLight()
	{
		return this.getWorld().getLightFromNeighbors(pos) >= 9;
	}

	public boolean hasHeat()
	{
		return this.getWorld().getBlockState(getPos().down()).getBlock() == Blocks.LIT_FURNACE.getDefaultState().getBlock();
	}

	public int getProcessTicks()
	{
		return this.processTicks;
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
	{
		return oldState.getBlock() != newSate.getBlock();
	}

	void refresh()
	{
		if (hasWorld() && !world.isRemote)
		{
			IBlockState state = world.getBlockState(pos);
			world.markAndNotifyBlock(pos, null, state, state, 11);
		}
	}
}
