package roito.hikethecountryside.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import roito.hikethecountryside.helper.NonNullListHelper;

import javax.annotation.Nonnull;

public class FlatBasketRecipe implements IFlatBasketRecipe
{
	public static final IFlatBasketRecipe EMPTY_RECIPE = new FlatBasketRecipe(NonNullListHelper.createNonNullList(ItemStack.EMPTY), ItemStack.EMPTY);
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

	public boolean isTheSameInput(@Nonnull ItemStack input)
	{
		return !this.output.isEmpty() && OreDictionary.containsMatch(false, inputs, input);
	}
}