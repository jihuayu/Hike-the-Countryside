package roito.hikethecountryside.compat.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import roito.hikethecountryside.api.recipe.HCRecipeRegistry;
import roito.hikethecountryside.api.recipe.IFlatBasketRecipe;
import roito.hikethecountryside.helper.NonNullListHelper;

import java.util.ArrayList;
import java.util.List;

public class RecipeFlatBasketDrying implements IRecipeWrapper
{
	public static List<RecipeFlatBasketDrying> getWrappedRecipeList()
	{
		List<RecipeFlatBasketDrying> recipesToReturn = new ArrayList<>();
		for (IFlatBasketRecipe recipe : HCRecipeRegistry.managerFlatBasketDrying.getRecipes())
		{
			recipesToReturn.add(new RecipeFlatBasketDrying(recipe));
		}
		return recipesToReturn;
	}

	private final IFlatBasketRecipe recipe;

	public RecipeFlatBasketDrying(IFlatBasketRecipe recipe)
	{
		this.recipe = recipe;
	}

	@Override
	public void getIngredients(IIngredients ingredients)
	{
		ingredients.setInputs(ItemStack.class, getInputs());
		ingredients.setOutputs(ItemStack.class, getOutputs());
	}

	public List<ItemStack> getInputs()
	{
		return NonNullListHelper.createNonNullList(recipe.getInputs());
	}

	public List<ItemStack> getOutputs()
	{
		return NonNullListHelper.createNonNullList(recipe.getOutput());
	}
}
