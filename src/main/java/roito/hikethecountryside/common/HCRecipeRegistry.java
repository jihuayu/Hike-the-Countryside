package roito.hikethecountryside.common;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import roito.hikethecountryside.api.recipe.*;
import roito.hikethecountryside.common.HCBlocksItemsRegistry;
import roito.hikethecountryside.helper.NonNullListHelper;

import javax.annotation.Nonnull;

public class HCRecipeRegistry
{
	@Nonnull
	public static final IRecipeManager<IFlatBasketRecipe> managerFlatBasketDrying, managerFlatBasketWet, managerFlatBasketFermentation, managerFlatBasketBake;

	static
	{
		managerFlatBasketDrying = new FlatBasketDryingRecipeManager();
		managerFlatBasketWet = new FlatBasketWetRecipeManager();
		managerFlatBasketFermentation = new FlatBasketFermentationRecipeManager();
		managerFlatBasketBake = new FlatBasketBakeRecipeManager();

		addFlatBasketDryingRecipes();
		addFlatBasketWetRecipes();
		addFlatBasketFermentationRecipes();
		addFlatBasketBakeRecipes();
	}

	private static void addFlatBasketDryingRecipes()
	{
		addFlatBasketRecipe(managerFlatBasketDrying, NonNullListHelper.createNonNullList(new ItemStack(Items.ROTTEN_FLESH)), new ItemStack(Items.LEATHER));
		addFlatBasketRecipe(managerFlatBasketDrying, NonNullListHelper.createNonNullList(new ItemStack(Items.RABBIT)), new ItemStack(HCBlocksItemsRegistry.RABBIT_JERKY));
		addFlatBasketRecipe(managerFlatBasketDrying, NonNullListHelper.createNonNullList(new ItemStack(Items.PORKCHOP)), new ItemStack(HCBlocksItemsRegistry.PORK_JERKY));
		addFlatBasketRecipe(managerFlatBasketDrying, NonNullListHelper.createNonNullList(new ItemStack(Items.BEEF)), new ItemStack(HCBlocksItemsRegistry.BEEF_JERKY));
		addFlatBasketRecipe(managerFlatBasketDrying, NonNullListHelper.createNonNullList(new ItemStack(Items.MUTTON)), new ItemStack(HCBlocksItemsRegistry.MUTTON_JERKY));
		addFlatBasketRecipe(managerFlatBasketDrying, NonNullListHelper.createNonNullList(new ItemStack(Items.CHICKEN)), new ItemStack(HCBlocksItemsRegistry.CHICKEN_JERKY));
		addFlatBasketRecipe(managerFlatBasketDrying, NonNullListHelper.createNonNullList(new ItemStack(Items.BEETROOT)), new ItemStack(HCBlocksItemsRegistry.DRIED_BEETROOT));
		addFlatBasketRecipe(managerFlatBasketDrying, NonNullListHelper.createNonNullList(new ItemStack(Items.CARROT)), new ItemStack(HCBlocksItemsRegistry.DRIED_CARROT));
		addFlatBasketRecipe(managerFlatBasketDrying, OreDictionary.getOres("foodJerky"), new ItemStack(Items.LEATHER));
	}

	private static void addFlatBasketWetRecipes()
	{
		addFlatBasketRecipe(managerFlatBasketWet, NonNullListHelper.createNonNullList(new ItemStack(HCBlocksItemsRegistry.RABBIT_JERKY)), new ItemStack(Items.RABBIT));
		addFlatBasketRecipe(managerFlatBasketWet, NonNullListHelper.createNonNullList(new ItemStack(HCBlocksItemsRegistry.PORK_JERKY)), new ItemStack(Items.PORKCHOP));
		addFlatBasketRecipe(managerFlatBasketWet, NonNullListHelper.createNonNullList(new ItemStack(HCBlocksItemsRegistry.BEEF_JERKY)), new ItemStack(Items.BEEF));
		addFlatBasketRecipe(managerFlatBasketWet, NonNullListHelper.createNonNullList(new ItemStack(HCBlocksItemsRegistry.MUTTON_JERKY)), new ItemStack(Items.MUTTON));
		addFlatBasketRecipe(managerFlatBasketWet, NonNullListHelper.createNonNullList(new ItemStack(HCBlocksItemsRegistry.CHICKEN_JERKY)), new ItemStack(Items.CHICKEN));
		addFlatBasketRecipe(managerFlatBasketWet, NonNullListHelper.createNonNullList(new ItemStack(HCBlocksItemsRegistry.DRIED_CARROT)), new ItemStack(Items.CARROT));
		addFlatBasketRecipe(managerFlatBasketWet, NonNullListHelper.createNonNullList(new ItemStack(HCBlocksItemsRegistry.DRIED_BEETROOT)), new ItemStack(Items.BEETROOT));
	}

	private static void addFlatBasketFermentationRecipes()
	{
		addFlatBasketRecipe(managerFlatBasketFermentation, OreDictionary.getOres("listAllmeatraw"), new ItemStack(Items.ROTTEN_FLESH));
		addFlatBasketRecipe(managerFlatBasketFermentation, NonNullListHelper.createNonNullList(new ItemStack(Items.SPIDER_EYE)), new ItemStack(Items.FERMENTED_SPIDER_EYE));
	}

	private static void addFlatBasketBakeRecipes()
	{
		addFlatBasketRecipe(managerFlatBasketBake, NonNullListHelper.createNonNullList(new ItemStack(Items.ROTTEN_FLESH)), new ItemStack(Items.LEATHER));
		addFlatBasketRecipe(managerFlatBasketBake, NonNullListHelper.createNonNullList(new ItemStack(Items.RABBIT)), new ItemStack(HCBlocksItemsRegistry.RABBIT_JERKY));
		addFlatBasketRecipe(managerFlatBasketBake, NonNullListHelper.createNonNullList(new ItemStack(Items.PORKCHOP)), new ItemStack(HCBlocksItemsRegistry.PORK_JERKY));
		addFlatBasketRecipe(managerFlatBasketBake, NonNullListHelper.createNonNullList(new ItemStack(Items.BEEF)), new ItemStack(HCBlocksItemsRegistry.BEEF_JERKY));
		addFlatBasketRecipe(managerFlatBasketBake, NonNullListHelper.createNonNullList(new ItemStack(Items.MUTTON)), new ItemStack(HCBlocksItemsRegistry.MUTTON_JERKY));
		addFlatBasketRecipe(managerFlatBasketBake, NonNullListHelper.createNonNullList(new ItemStack(Items.CHICKEN)), new ItemStack(HCBlocksItemsRegistry.CHICKEN_JERKY));
		addFlatBasketRecipe(managerFlatBasketBake, NonNullListHelper.createNonNullList(new ItemStack(Items.BEETROOT)), new ItemStack(HCBlocksItemsRegistry.DRIED_BEETROOT));
		addFlatBasketRecipe(managerFlatBasketBake, NonNullListHelper.createNonNullList(new ItemStack(Items.CARROT)), new ItemStack(HCBlocksItemsRegistry.DRIED_CARROT));
		addFlatBasketRecipe(managerFlatBasketBake, OreDictionary.getOres("foodJerky"), new ItemStack(Items.LEATHER));
	}

	public static void addFlatBasketRecipe(IRecipeManager<IFlatBasketRecipe> recipeManager, NonNullList<ItemStack> inputs, ItemStack output)
	{
		recipeManager.add(new FlatBasketRecipe(inputs, output));
	}
}
