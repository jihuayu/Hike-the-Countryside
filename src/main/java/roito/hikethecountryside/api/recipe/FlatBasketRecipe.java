package roito.hikethecountryside.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

public class FlatBasketRecipe implements IFlatBasketRecipe
{
	private final NonNullList<ItemStack> inputs;
	private final ItemStack output, wetOutput;

	public FlatBasketRecipe(NonNullList<ItemStack> inputs, ItemStack output, ItemStack wetOutput)
	{
		this.inputs = inputs;
		this.output = output;
		this.wetOutput = wetOutput;
	}

	@Override
	public NonNullList<ItemStack> getInputs()
	{
		return inputs;
	}

	@Override
	public ItemStack getOutput()
	{
		return output.copy();
	}

	@Override
	public ItemStack getWetOutput()
	{
		return wetOutput.copy();
	}

	@Override
	public boolean equals(Object r)
	{
		if(r instanceof IFlatBasketRecipe)
		{
			if(this.inputs.size() >= ((IFlatBasketRecipe) r).getInputs().size())
			{
				return OreDictionary.containsMatch(false, this.inputs, ((FlatBasketRecipe) r).getInputs().get(0));
			}
			else
			{
				return OreDictionary.containsMatch(false, ((FlatBasketRecipe) r).getInputs(), this.inputs.get(0));
			}
		}
		else
		{
			return false;
		}
	}
}