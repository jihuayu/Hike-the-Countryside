package roito.hikethecountryside.compat.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import roito.hikethecountryside.common.HCRecipeRegistry;
import roito.hikethecountryside.api.recipe.IFlatBasketRecipe;
import roito.hikethecountryside.helper.NonNullListHelper;

import java.util.ArrayList;
import java.util.List;

public class RecipeFlatBasketInRain implements IRecipeWrapper
{
	public static List<RecipeFlatBasketInRain> getWrappedRecipeList()
	{
		List<RecipeFlatBasketInRain> recipesToReturn = new ArrayList<>();
		for (IFlatBasketRecipe recipe : HCRecipeRegistry.managerFlatBasketWet.getRecipes())
		{
			recipesToReturn.add(new RecipeFlatBasketInRain(recipe));
		}
		return recipesToReturn;
	}

	private final IFlatBasketRecipe recipe;

	public RecipeFlatBasketInRain(IFlatBasketRecipe recipe)
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
