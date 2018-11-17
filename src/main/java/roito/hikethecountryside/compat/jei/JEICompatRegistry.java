package roito.hikethecountryside.compat.jei;

import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import roito.hikethecountryside.api.recipe.IFlatBasketRecipe;
import roito.hikethecountryside.client.gui.GuiContainerFlatBasket;
import roito.hikethecountryside.common.HCBlocksItemsRegistry;

@JEIPlugin
public class JEICompatRegistry implements IModPlugin
{

    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry)
    {

    }

    @Override
    public void registerIngredients(IModIngredientRegistration registry)
    {

    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry)
    {
        registry.addRecipeCategories(
                new CategoryFlatBasketDrying(registry.getJeiHelpers().getGuiHelper()),
		        new CategoryFlatBasketInRain(registry.getJeiHelpers().getGuiHelper()),
		        new CategoryFlatBasketFermentation(registry.getJeiHelpers().getGuiHelper()),
		        new CategoryFlatBasketBake(registry.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void register(IModRegistry registry)
    {
        addIngredientToBlacklist(registry);

        registry.handleRecipes(IFlatBasketRecipe.class, new RecipeWrapperFlatBasket(), "hikethecountryside.flat_basket.drying");
	    registry.handleRecipes(IFlatBasketRecipe.class, new RecipeWrapperFlatBasket(), "hikethecountryside.flat_basket.in_rain");
	    registry.handleRecipes(IFlatBasketRecipe.class, new RecipeWrapperFlatBasket(), "hikethecountryside.flat_basket.fermentation");
	    registry.handleRecipes(IFlatBasketRecipe.class, new RecipeWrapperFlatBasket(), "hikethecountryside.flat_basket.bake");

        registry.addRecipeCatalyst(new ItemStack(HCBlocksItemsRegistry.BLOCK_FLAT_BASKET), "hikethecountryside.flat_basket.drying", "hikethecountryside.flat_basket.in_rain", "hikethecountryside.flat_basket.fermentation", "hikethecountryside.flat_basket.bake");

        registry.addRecipes(RecipeFlatBasketDrying.getWrappedRecipeList(), "hikethecountryside.flat_basket.drying");
        registry.addRecipes(RecipeFlatBasketInRain.getWrappedRecipeList(), "hikethecountryside.flat_basket.in_rain");
	    registry.addRecipes(RecipeFlatBasketFermentation.getWrappedRecipeList(), "hikethecountryside.flat_basket.fermentation");
	    registry.addRecipes(RecipeFlatBasketBake.getWrappedRecipeList(), "hikethecountryside.flat_basket.bake");

        registry.addRecipeClickArea(GuiContainerFlatBasket.class, 76, 31, 24, 17, "hikethecountryside.flat_basket.drying", "hikethecountryside.flat_basket.in_rain", "hikethecountryside.flat_basket.fermentation", "hikethecountryside.flat_basket.bake");
    }

    public static void addIngredientToBlacklist(IModRegistry registry)
    {
        IIngredientBlacklist blacklist = registry.getJeiHelpers().getIngredientBlacklist();
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime)
    {

    }
}
