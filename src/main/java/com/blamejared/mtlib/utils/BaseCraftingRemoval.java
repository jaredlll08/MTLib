package com.blamejared.mtlib.utils;

import com.blamejared.mtlib.helpers.LogHelper;
import crafttweaker.api.item.IIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

import java.util.*;

import static com.blamejared.mtlib.helpers.InputHelper.toIItemStack;
import static com.blamejared.mtlib.helpers.StackHelper.matches;

public class BaseCraftingRemoval extends BaseListRemoval<IRecipe> {
    
    public BaseCraftingRemoval(String name, List<IRecipe> list, List<IRecipe> recipes) {
        super(name, list, recipes);
    }
    
    public static List<IRecipe> getRecipes(List<IRecipe> list, IIngredient ingredient) {
        List<IRecipe> recipes = new LinkedList<IRecipe>();
        
        for(IRecipe r : list) {
            if(r != null && r.getRecipeOutput() != ItemStack.EMPTY && matches(ingredient, toIItemStack(r.getRecipeOutput()))) {
                recipes.add(r);
            }
        }
        
        return recipes;
    }
    
    @Override
    public String getRecipeInfo(IRecipe recipe) {
        return LogHelper.getStackDescription(recipe.getRecipeOutput());
    }
}
