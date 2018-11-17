package roito.hikethecountryside.compat.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import roito.hikethecountryside.api.recipe.HCRecipeRegistry;
import roito.hikethecountryside.api.recipe.IFlatBasketRecipe;
import roito.hikethecountryside.helper.NonNullListHelper;

import java.util.ArrayList;
import java.util.List;

public class RecipeFlatBasketBake implements IRecipeWrapper
{
	public static List<RecipeFlatBasketBake> getWrappedRecipeList()
	{
		List<RecipeFlatBasketBake> recipesToReturn = new ArrayList<>();
		for (IFlatBasketRecipe recipe : HCRecipeRegistry.managerFlatBasketBake.getRecipes())
		{
			recipesToReturn.add(new RecipeFlatBasketBake(recipe));
		}
		return recipesToReturn;
	}

	private final IFlatBasketRecipe recipe;

	public RecipeFlatBasketBake(IFlatBasketRecipe recipe)
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
