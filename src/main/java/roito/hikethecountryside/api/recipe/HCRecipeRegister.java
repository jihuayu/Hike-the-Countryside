package roito.hikethecountryside.api.recipe;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import roito.hikethecountryside.common.HCBlocksItemsRegistry;
import roito.hikethecountryside.helper.NonNullListHelper;

import javax.annotation.Nonnull;

public class HCRecipeRegister
{
	@Nonnull
	public static IRecipeManager<IFlatBasketRecipe> managerFlatBasketDrying;
	@Nonnull
	public static IRecipeManager<IFlatBasketRecipe> managerFlatBasketWet;

	public HCRecipeRegister()
	{
		managerFlatBasketDrying = new FlatBasketRecipeManager();
		managerFlatBasketWet = new FlatBasketRecipeManager();

		addFlatBasketDryingRecipes();
		addFlatBasketWetRecipes();
	}

	private static void addFlatBasketDryingRecipes()
	{
		addFlatBasketDryingRecipe(NonNullListHelper.createNonNullList(new ItemStack(Items.ROTTEN_FLESH)), new ItemStack(Items.LEATHER));
		addFlatBasketDryingRecipe(NonNullListHelper.createNonNullList(new ItemStack(Items.RABBIT)), new ItemStack(HCBlocksItemsRegistry.RABBIT_JERKY));
		addFlatBasketDryingRecipe(NonNullListHelper.createNonNullList(new ItemStack(Items.PORKCHOP)), new ItemStack(HCBlocksItemsRegistry.PORK_JERKY));
		addFlatBasketDryingRecipe(NonNullListHelper.createNonNullList(new ItemStack(Items.BEEF)), new ItemStack(HCBlocksItemsRegistry.BEEF_JERKY));
		addFlatBasketDryingRecipe(NonNullListHelper.createNonNullList(new ItemStack(Items.MUTTON)), new ItemStack(HCBlocksItemsRegistry.MUTTON_JERKY));
		addFlatBasketDryingRecipe(NonNullListHelper.createNonNullList(new ItemStack(Items.CHICKEN)), new ItemStack(HCBlocksItemsRegistry.CHICKEN_JERKY));
		addFlatBasketDryingRecipe(NonNullListHelper.createNonNullList(new ItemStack(Items.BEETROOT)), new ItemStack(HCBlocksItemsRegistry.DRIED_BEETROOT));
		addFlatBasketDryingRecipe(NonNullListHelper.createNonNullList(new ItemStack(Items.CARROT)), new ItemStack(HCBlocksItemsRegistry.DRIED_CARROT));
		addFlatBasketDryingRecipe(OreDictionary.getOres("foodJerky"), new ItemStack(Items.LEATHER));
	}

	private static void addFlatBasketWetRecipes()
	{
		addFlatBasketWetRecipe(NonNullListHelper.createNonNullList(new ItemStack(HCBlocksItemsRegistry.RABBIT_JERKY)), new ItemStack(Items.RABBIT));
		addFlatBasketWetRecipe(NonNullListHelper.createNonNullList(new ItemStack(HCBlocksItemsRegistry.PORK_JERKY)), new ItemStack(Items.PORKCHOP));
		addFlatBasketWetRecipe(NonNullListHelper.createNonNullList(new ItemStack(HCBlocksItemsRegistry.BEEF_JERKY)), new ItemStack(Items.BEEF));
		addFlatBasketWetRecipe(NonNullListHelper.createNonNullList(new ItemStack(HCBlocksItemsRegistry.MUTTON_JERKY)), new ItemStack(Items.MUTTON));
		addFlatBasketWetRecipe(NonNullListHelper.createNonNullList(new ItemStack(HCBlocksItemsRegistry.CHICKEN_JERKY)), new ItemStack(Items.CHICKEN));
		addFlatBasketWetRecipe(NonNullListHelper.createNonNullList(new ItemStack(HCBlocksItemsRegistry.DRIED_CARROT)), new ItemStack(Items.CARROT));
		addFlatBasketWetRecipe(NonNullListHelper.createNonNullList(new ItemStack(HCBlocksItemsRegistry.DRIED_BEETROOT)), new ItemStack(Items.BEETROOT));
	}

	public static void addFlatBasketDryingRecipe(NonNullList<ItemStack> inputs, ItemStack output)
	{
		managerFlatBasketDrying.add(new FlatBasketRecipe(inputs, output));
	}

	public static void addFlatBasketWetRecipe(NonNullList<ItemStack> inputs, ItemStack output)
	{
		managerFlatBasketWet.add(new FlatBasketRecipe(inputs, output));
	}
}
