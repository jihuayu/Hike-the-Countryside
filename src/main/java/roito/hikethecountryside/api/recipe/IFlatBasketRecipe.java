package roito.hikethecountryside.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public interface IFlatBasketRecipe
{
	NonNullList<ItemStack> getInputs();

	ItemStack getOutput();

	ItemStack getWetOutput();
}
