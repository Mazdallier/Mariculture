package joshie.mariculture.api.core.handlers;

import java.util.HashMap;

import joshie.mariculture.api.core.recipes.RecipeCasting;
import net.minecraftforge.fluids.FluidStack;

public interface ICastingHandler {

    /** Add a caster recipe **/
    public void addRecipe(RecipeCasting recipe);

    /** Caster Recipes **/
    public RecipeCasting getNuggetResult(FluidStack fluid);

    public RecipeCasting getIngotResult(FluidStack fluid);

    public RecipeCasting getBlockResult(FluidStack fluid);

    /** Recipe lists **/
    public HashMap<String, RecipeCasting> getNuggetRecipes();

    public HashMap<String, RecipeCasting> getIngotRecipes();

    public HashMap<String, RecipeCasting> getBlockRecipes();
}
