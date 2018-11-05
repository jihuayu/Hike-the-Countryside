package roito.hikethecountryside.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
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
	protected boolean hasHeat = false;

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
	public void update()
	{
		if (!world.isRemote)
		{
			ItemStack input = this.inputInventory.getStackInSlot(0).copy();
			IFlatBasketRecipe recipeIn = new FlatBasketRecipe(NonNullListHelper.createNonNullList(input), ItemStack.EMPTY, ItemStack.EMPTY);
			IFlatBasketRecipe recipeUse = new FlatBasketRecipe(NonNullListHelper.createNonNullList(ItemStack.EMPTY), ItemStack.EMPTY, ItemStack.EMPTY);
			refreshTotalTicks();
			if (getMode() == 0 && isWorldRaining())
			{
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
					if (isItInRain())
					{
						ItemStack wetOutput = recipeUse.getWetOutput().copy();
						wetOutput.setCount(inputInventory.getStackInSlot(0).getCount() + outputInventory.getStackInSlot(0).getCount());
						this.inputInventory.setStackInSlot(0, wetOutput);
						this.outputInventory.setStackInSlot(0, ItemStack.EMPTY);
					}
					else
					{
						ItemStack output = recipeUse.getOutput().copy();
						output.setCount(input.getCount());
						if (jugde(output))
						{
							this.outputInventory.insertItem(0, output, false);
							return;
						}
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

	private boolean jugde(ItemStack itemStack)
	{
		if (this.outputInventory.insertItem(0, itemStack, true).isEmpty())
		{
			this.processTicks++;
			this.markDirty();
			if (this.processTicks >= this.totalTicks)
			{
				this.processTicks = 0;
				return true;
			}
		}
		return false;
	}

	public int getMode()
	{
		if (this.getWorld().canSeeSky(this.getPos()))
		{
			return 0;
		}
		else
		{
			return 1;
		}
	}

	public void refreshTotalTicks()
	{
		this.totalTicks = ConfigMain.craft.dryingBasicTime * inputInventory.getStackInSlot(0).getCount();
	}

	public int getTotalTicks()
	{
		return this.totalTicks;
	}

	public boolean isWorldRaining()
	{
		return this.getWorld().isRaining();
	}

	public boolean isItInRain()
	{
		return this.getWorld().isRainingAt(this.getPos());
	}

	public boolean hasEnoughLight()
	{
		return this.getWorld().getLightFromNeighbors(pos) >= 9;
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
}
