package roito.hikethecountryside.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

public class FlatBasketRecipe implements IFlatBasketRecipe
{
	private final NonNullList<ItemStack> inputs;
	private final ItemStack output;

	public FlatBasketRecipe(NonNullList<ItemStack> inputs, ItemStack output)
	{
		this.inputs = inputs;
		this.output = output;
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

	public boolean isTheSameInput(ItemStack input)
	{
		return OreDictionary.containsMatch(false, inputs, input);
	}
}