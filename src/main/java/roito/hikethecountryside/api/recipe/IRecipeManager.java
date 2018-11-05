package roito.hikethecountryside.api.recipe;

import java.util.Collection;

public interface IRecipeManager<R>
{
	boolean equal(R recipe1, R recipe2);

	void add(R recipe);

	void remove(R recipe);

	Collection<R> getRecipes();

	<T> R getRecipe(T... input);
}
