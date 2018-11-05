package roito.hikethecountryside.api.recipe;

import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;

public class FlatBasketDryingRecipeManager implements IRecipeManager<IFlatBasketRecipe>
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

	@Nullable
	@Override
	public <T> IFlatBasketRecipe getRecipe(T... inputs)
	{
		if (inputs[0] == null || !(inputs[0] instanceof ItemStack))
		{
			return null;
		}
		for (IFlatBasketRecipe r : recipes)
		{
			if (r.getOutput().isItemEqual((ItemStack) inputs[0]))
			{
				return r;
			}
		}
		return null;
	}

	private static ArrayList<IFlatBasketRecipe> recipes = new ArrayList<>();
}
