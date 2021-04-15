package com.blamejared.mtlib.helpers;

import crafttweaker.*;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.oredict.IOreDictEntry;
import crafttweaker.api.player.IPlayer;
import crafttweaker.mc1120.item.MCItemStack;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.*;

import java.util.*;

public class LogHelper {
    
    public static void logPrinted(IPlayer player) {
        if(player != null) {
            player.sendChat(CrafttweakerImplementationAPI.platform.getMessage("List generated; see minetweaker.log in your minecraft dir"));
        }
    }
    
    public static void log(IPlayer player, String message) {
        if(player != null) {
            player.sendChat(CrafttweakerImplementationAPI.platform.getMessage(message));
        }
    }
    
    public static void print(String string) {
        System.out.println(string);
        CraftTweakerAPI.logCommand(string);
    }
    
    public static void logError(String message) {
        CraftTweakerAPI.logError(message);
    }
    
    public static void logError(String message, Throwable exception) {
        CraftTweakerAPI.logError(message, exception);
    }
    
    public static void logWarning(String message) {
        CraftTweakerAPI.logWarning(message);
    }
    
    public static void logInfo(String message) {
        CraftTweakerAPI.logInfo(message);
    }
    
    /**
     * Returns a string representation of the item which can also be used in scripts
     */
    @SuppressWarnings("rawtypes")
    public static String getStackDescription(Object object) {
        if(object instanceof IIngredient) {
            return getStackDescription((IIngredient) object);
        } else if(object instanceof ItemStack) {
            return new MCItemStack((ItemStack) object).toString();
        } else if(object instanceof FluidStack) {
            return getStackDescription((FluidStack) object);
        } else if(object instanceof Block) {
            return new MCItemStack(new ItemStack((Block) object, 1, 0)).toString();
        } else if(object instanceof String) {
            // Check if string specifies an oredict entry
            List<ItemStack> ores = OreDictionary.getOres((String) object);
            
            if(!ores.isEmpty()) {
                return "<ore:" + (String) object + ">";
            } else {
                return "\"" + (String) object + "\"";
            }
        } else if(object instanceof List) {
            return getListDescription((List) object);
        } else if(object instanceof Object[]) {
            return getListDescription(Arrays.asList((Object[]) object));
        } else if(object instanceof Ingredient && !((Ingredient) object).apply(ItemStack.EMPTY) && ((Ingredient) object).getMatchingStacks().length > 0) {
            return getStackDescription(((Ingredient) object).getMatchingStacks()[0]);
        } else if(object != null) {
            return "\"" + object.toString() + "\"";
        } else {
            return "null";
        }
    }
    
    public static String getStackDescription(IIngredient stack) {
        if(stack == null)
            return "null";
        
        Object internalObject = stack.getInternal();
        
        if(internalObject instanceof ItemStack) {
            return getStackDescription((ItemStack) internalObject);
        } else if(internalObject instanceof FluidStack) {
            return getStackDescription((FluidStack) internalObject);
        } else if(stack instanceof IOreDictEntry) {
            return getStackDescription(((IOreDictEntry) stack).getName());
        } else {
            return "null";
        }
    }
    
    public static String getStackDescription(FluidStack stack) {
        
        if(stack == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        
        sb.append("<liquid:").append(stack.getFluid().getName()).append('>');
        
        if(stack.amount > 1) {
            sb.append(" * ").append(stack.amount);
        }
        
        return sb.toString();
    }
    
    public static String getListDescription(List<?> objects) {
        if(objects == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        
        if(objects.isEmpty()) {
            sb.append("[]");
        } else {
            sb.append('[');
            for(Object object : objects) {
                if(object instanceof List) {
                    sb.append(getListDescription((List) object)).append(", ");
                } else if(object instanceof Object[]) {
                    sb.append(getListDescription(Arrays.asList((Object[]) object))).append(", ");
                } else {
                    sb.append(getStackDescription(object)).append(", ");
                }
            }
            sb.setLength(sb.length() - 2);
            sb.append(']');
        }
        
        return sb.toString();
    }
    
    public static String getCraftingDescription(IRecipe recipe) {
        if(recipe instanceof ShapelessOreRecipe)
            return LogHelper.getCraftingDescription((ShapelessOreRecipe) recipe);
        else if(recipe instanceof ShapedOreRecipe)
            return LogHelper.getCraftingDescription((ShapedOreRecipe) recipe);
        else if(recipe instanceof ShapelessRecipes)
            return LogHelper.getCraftingDescription((ShapelessRecipes) recipe);
        else if(recipe instanceof ShapedRecipes)
            return LogHelper.getCraftingDescription((ShapedRecipes) recipe);
        
        return recipe.toString();
    }
    
    public static String getCraftingDescription(ShapelessOreRecipe recipe) {
        return getListDescription(recipe.getIngredients());
    }
    
    public static String getCraftingDescription(ShapelessRecipes recipe) {
        return getListDescription(recipe.recipeItems);
    }
    
    public static String getCraftingDescription(ShapedOreRecipe recipe) {
        int height = recipe.getWidth();
        int width = recipe.getHeight();
        
        Ingredient[][] recipes = InputHelper.getMultiDimensionalArray(Ingredient.class, recipe.getIngredients().toArray(new Ingredient[0]), height, width);
        return getListDescription(Arrays.asList(recipes));
    }
    
    public static String getCraftingDescription(ShapedRecipes recipe) {
        Ingredient[][] recipes = InputHelper.getMultiDimensionalArray(Ingredient.class, recipe.getIngredients().toArray(new Ingredient[0]), recipe.recipeHeight, recipe.recipeWidth);
        return getListDescription(Arrays.asList(recipes));
    }
}
