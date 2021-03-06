package roito.hikethecountryside.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import roito.hikethecountryside.api.block.IBlockStove;
import roito.hikethecountryside.api.recipe.*;
import roito.hikethecountryside.common.HCRecipeRegistry;
import roito.hikethecountryside.helper.EntironmentHelper;
import snownee.kiwi.tile.TileBase;

import static roito.hikethecountryside.api.recipe.FlatBasketRecipe.EMPTY_RECIPE;

public class TileEntityFlatBasket extends TileBase implements ITickable
{
	protected int processTicks = 0;
	protected int totalTicks = 0;
	protected IFlatBasketRecipe usedRecipe = EMPTY_RECIPE;

	protected ItemStackHandler inputInventory = new ItemStackHandler();
	protected ItemStackHandler outputInventory = new ItemStackHandler();

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
			return facing == EnumFacing.DOWN ?
					CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(outputInventory) :
					CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inputInventory);
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
	protected void readPacketData(NBTTagCompound data) {
		readFromNBT(data);
	}

	@Override
	protected NBTTagCompound writePacketData(NBTTagCompound data) {
		return writeToNBT(data);
	}

	@Override
	public void update()
	{
		if (!world.isRemote)
		{
			ItemStack input = this.inputInventory.getStackInSlot(0);
			if (input.isEmpty())
			{
				return;
			}
			Biome biome = this.getWorld().getBiome(pos);
			switch (this.getMode())
			{
				case -1:
				{
					refreshTotalTicks(0);
					getWet();
					return;
				}
				case 1:
				{
					refreshTotalTicks(EntironmentHelper.getFermentationTicks(biome.getRainfall(), biome.getTemperature(pos)));
					process(HCRecipeRegistry.managerFlatBasketFermentation);
					return;
				}
				case 2:
				{
					refreshTotalTicks(200);
					process(HCRecipeRegistry.managerFlatBasketBake);
					return;
				}
				default:
				{
					if (!this.isWorldRaining())
					{
						refreshTotalTicks(EntironmentHelper.getDryingTicks(biome.getRainfall(), biome.getTemperature(pos)));
						process(HCRecipeRegistry.managerFlatBasketDrying);
					}
					return;
				}
			}
		}
	}

	private boolean process(IRecipeManager<IFlatBasketRecipe> recipeManager)
	{
		ItemStack input = this.inputInventory.getStackInSlot(0);
		if (input.isEmpty())
		{
			return false;
		}
		if (!usedRecipe.isTheSameInput(input))
		{
			usedRecipe = recipeManager.getRecipe(input);
		}
		if (usedRecipe != EMPTY_RECIPE)
		{
			ItemStack output = usedRecipe.getOutput().copy();
			output.setCount(input.getCount());
			if (this.outputInventory.insertItem(0, output, true).isEmpty())
			{
				if (++this.processTicks >= this.totalTicks)
				{
					this.outputInventory.insertItem(0, output, false);
					this.inputInventory.extractItem(0, output.getCount(), false);
					refresh();
					this.processTicks = 0;
				}
				this.markDirty();
				return true;
			}
		}
		this.processTicks = 0;
		this.markDirty();
		return false;
	}

	private void getWet()
	{
		this.processTicks = 0;
		if (!usedRecipe.isTheSameInput(this.inputInventory.getStackInSlot(0)))
		{
			usedRecipe = HCRecipeRegistry.managerFlatBasketWet.getRecipe(this.inputInventory.getStackInSlot(0));
		}
		if (usedRecipe != EMPTY_RECIPE)
		{
			ItemStack wetOutput = usedRecipe.getOutput().copy();
			wetOutput.setCount(inputInventory.getStackInSlot(0).getCount());
			this.inputInventory.setStackInSlot(0, wetOutput);
			refresh();
		}
		this.markDirty();
	}

	/*
		-1 for in rain, 0 for drying, 1 for fermentation, 2 for bake.
	 */
	public int getMode()
	{
		if (this.isInRain())
		{
			return -1;
		}
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
		NonNullList<ItemStack> list = NonNullList.create();
		for (int i = this.outputInventory.getStackInSlot(0).getCount(); i > 0; i -= 16)
		{
			list.add(this.outputInventory.getStackInSlot(0));
		}
		for (int i = this.inputInventory.getStackInSlot(0).getCount(); i > 0; i -= 16)
		{
			list.add(this.inputInventory.getStackInSlot(0));
		}
		return list;
	}

	public void refreshTotalTicks(int basicTime)
	{
		if (inputInventory.getStackInSlot(0).getCount() >= 32)
		{
			this.totalTicks = 32;
		}
		else if (inputInventory.getStackInSlot(0).getCount() > 0 && inputInventory.getStackInSlot(0).getCount() <= 8)
		{
			this.totalTicks = 8;
		}
		else
		{
			this.totalTicks = inputInventory.getStackInSlot(0).getCount();
		}
		this.totalTicks *= basicTime;
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
		if (hasWorld()) {
			IBlockState state = getWorld().getBlockState(getPos().down());
			if (state.getBlock() instanceof IBlockStove)
			{
				return ((IBlockStove) state.getBlock()).isBurning(state);
			}
		}
		return false;
	}

	public int getProcessTicks()
	{
		return this.processTicks;
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
		return oldState.getBlock() != newState.getBlock();
	}
}
