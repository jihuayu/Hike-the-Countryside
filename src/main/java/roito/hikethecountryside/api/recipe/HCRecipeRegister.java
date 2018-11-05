package roito.hikethecountryside.api.recipe;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import roito.hikethecountryside.helper.NonNullListHelper;

import javax.annotation.Nonnull;

public class HCRecipeRegister
{
	@Nonnull
	public static IRecipeManager<IFlatBasketRecipe> managerFlatBasketDrying;

	public HCRecipeRegister()
	{
		managerFlatBasketDrying = new FlatBasketDryingRecipeManager();

		addFlatBasketDryingRecipe();
	}

	private static void addFlatBasketDryingRecipe()
	{
		managerFlatBasketDrying.add(new FlatBasketRecipe(NonNullListHelper.createNonNullList(new ItemStack(Items.ROTTEN_FLESH)), new ItemStack(Items.LEATHER), new ItemStack(Items.ROTTEN_FLESH)));
	}
}
