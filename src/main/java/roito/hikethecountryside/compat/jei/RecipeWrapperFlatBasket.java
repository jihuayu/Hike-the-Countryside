package roito.hikethecountryside.compat.jei;

import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import roito.hikethecountryside.api.recipe.IFlatBasketRecipe;

public class RecipeWrapperFlatBasket implements IRecipeWrapperFactory<IFlatBasketRecipe>
{
    @Override
    public IRecipeWrapper getRecipeWrapper(IFlatBasketRecipe recipe)
    {
        return new RecipeFlatBasketDrying(recipe);
    }
}

