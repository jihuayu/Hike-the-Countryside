package roito.hikethecountryside.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.Collection;

public interface IRecipeManager<R>
{
	boolean equal(R recipe1, R recipe2);

	void add(R recipe);

	void remove(R recipe);

	Collection<R> getRecipes();

	<T> R getRecipe(T... input);

	IFlatBasketRecipe getRecipe(ItemStack input);

	IFlatBasketRecipe getRecipe(NonNullList<ItemStack> inputs);
}
