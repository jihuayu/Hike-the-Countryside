package roito.hikethecountryside.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;

import static roito.hikethecountryside.api.recipe.FlatBasketRecipe.EMPTY_RECIPE;

public class FlatBasketBakeRecipeManager implements IRecipeManager<IFlatBasketRecipe>
{
	@Override
	public boolean equal(IFlatBasketRecipe recipe1, IFlatBasketRecipe recipe2)
	{
		return recipe1.equals(recipe2);
	}

	@Override
	public void add(IFlatBasketRecipe recipe)
	{
		recipes.add(recipe);
	}

	@Override
	public void remove(IFlatBasketRecipe recipe)
	{
		java.util.Iterator<IFlatBasketRecipe> iter = recipes.iterator();
		while (iter.hasNext())
		{
			if (iter.next().equals(recipe))
			{
				iter.remove();
				return;
			}
		}
	}

	@Override
	public Collection getRecipes()
	{
		return recipes;
	}

	@Nonnull
	@Override
	public IFlatBasketRecipe getRecipe(@Nonnull ItemStack input)
	{
		for (IFlatBasketRecipe recipe : recipes)
		{
			if (recipe.isTheSameInput(input))
			{
				return recipe;
			}
		}
		return EMPTY_RECIPE;
	}

	@Nonnull
	@Override
	public IFlatBasketRecipe getRecipe(@Nonnull NonNullList<ItemStack> inputs)
	{
		for (ItemStack input : inputs)
		{
			IFlatBasketRecipe recipe = getRecipe(input);
			if (recipe != null)
			{
				return recipe;
			}
		}
		return EMPTY_RECIPE;
	}

	private static ArrayList<IFlatBasketRecipe> recipes = new ArrayList<>();
}
