package roito.hikethecountryside.compat.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import roito.hikethecountryside.HikeTheCountryside;

public class CategoryFlatBasketBake implements IRecipeCategory<IRecipeWrapper>
{

	protected final IDrawable background;
	protected final IDrawableAnimated progressBar;
	protected final IDrawableStatic fire;

	public CategoryFlatBasketBake(IGuiHelper helper)
	{
		ResourceLocation backgroundTexture = new ResourceLocation(HikeTheCountryside.MODID, "textures/gui/container/gui_flat_basket.png");
		background = helper.createDrawable(backgroundTexture, 50, 28, 76, 38);
		IDrawableStatic progressBarOverlay = helper.createDrawable(backgroundTexture, 176, 0, 24, 17);
		progressBar = helper.createAnimatedDrawable(progressBarOverlay, 300, IDrawableAnimated.StartDirection.LEFT, false);
		fire = helper.createDrawable(backgroundTexture, 176, 16, 14, 14);
	}

	@Override
	public String getUid()
	{
		return "hikethecountryside.flat_basket.bake";
	}

	@Override
	public String getTitle()
	{
		return I18n.format("jei.hikethecountryside.category.flat_basket.bake");
	}

	@Override
	public IDrawable getBackground()
	{
		return background;
	}

	@Override
	public void drawExtras(Minecraft minecraft)
	{
		progressBar.draw(minecraft, 26, 3);
		fire.draw(minecraft, 30, 21);
	}

	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper)
	{
		IGuiItemStackGroup items = recipeLayout.getItemStacks();
		items.init(0, true, 2, 2);
		items.set(0, ((RecipeFlatBasketBake) recipeWrapper).getInputs());
		items.init(1, false, 56, 2);
		items.set(1, ((RecipeFlatBasketBake) recipeWrapper).getOutputs());
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients)
	{
		setRecipe(recipeLayout, recipeWrapper);
	}

	@Override
	public String getModName()
	{
		return "HikeTheCountry";
	}
}
