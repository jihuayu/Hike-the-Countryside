package roito.hikethecountryside.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;

public class FlatBasketRecipeManager implements IRecipeManager<IFlatBasketRecipe>
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

	@Nullable
	public IFlatBasketRecipe getRecipe(ItemStack input)
	{
		for (IFlatBasketRecipe recipe : recipes)
		{
			if (recipe.isTheSameInput(input))
			{
				return recipe;
			}
		}
		return null;
	}

	@Nullable
	public IFlatBasketRecipe getRecipe(NonNullList<ItemStack> inputs)
	{
		for (ItemStack input : inputs)
		{
			IFlatBasketRecipe recipe = getRecipe(input);
			if (recipe != null)
			{
				return recipe;
			}
		}
		return null;
	}

	private ArrayList<IFlatBasketRecipe> recipes = new ArrayList<>();
}
