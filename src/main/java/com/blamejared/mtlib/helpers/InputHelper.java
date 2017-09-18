package com.blamejared.mtlib.helpers;

import crafttweaker.api.entity.IEntity;
import crafttweaker.api.item.*;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.oredict.IOreDictEntry;
import crafttweaker.mc1120.item.MCItemStack;
import crafttweaker.mc1120.liquid.MCLiquidStack;
import gnu.trove.set.TCharSet;
import gnu.trove.set.hash.TCharHashSet;
import net.minecraft.entity.Entity;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class InputHelper {
    
    public static boolean isABlock(IItemStack block) {
        if(!(isABlock(toStack(block)))) {
            return false;
        } else
            return true;
    }
    
    public static <T> T[][] getMultiDimensionalArray(Class<T> clazz, T[] array, int height, int width) {
        
        T[][] multiDim = (T[][]) Array.newInstance(clazz, height, width);
        
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                multiDim[y][x] = array[x + (y * width)];
            }
        }
        
        return multiDim;
    }
    
    public static IItemStack[] toStacks(IIngredient[] iIngredient) {
        ArrayList<IItemStack> stacks = new ArrayList<IItemStack>();
        
        for(IIngredient ing : iIngredient) {
            for(IItemStack stack : ing.getItems()) {
                stacks.add(stack);
            }
        }
        
        return stacks.toArray(new IItemStack[stacks.size()]);
    }
    
    public static boolean isABlock(ItemStack block) {
        return block.getItem() instanceof ItemBlock;
    }
    
    public static ItemStack toStack(IItemStack iStack) {
        if(iStack == null) {
            return ItemStack.EMPTY;
        } else {
            Object internal = iStack.getInternal();
            if(!(internal instanceof ItemStack)) {
                LogHelper.logError("Not a valid item stack: " + iStack);
            }
            
            return (ItemStack) internal;
        }
    }
    
    public static IIngredient toIngredient(ItemStack stack) {
        return toIItemStack(stack);
    }
    
    public static IIngredient toIngredient(FluidStack stack) {
        if(stack == null) {
            return null;
        }
        
        return new MCLiquidStack(stack);
    }
    
    public static IItemStack toIItemStack(ItemStack stack) {
        if(stack.isEmpty()) {
            return null;
        }
        
        return new MCItemStack(stack);
    }
    
    public static ILiquidStack toILiquidStack(FluidStack stack) {
        if(stack == null) {
            return null;
        }
        
        return new MCLiquidStack(stack);
    }
    
    public static ItemStack[] toStacks(IItemStack[] iStack) {
        if(iStack == null) {
            return null;
        } else {
            ItemStack[] output = new ItemStack[iStack.length];
            for(int i = 0; i < iStack.length; i++) {
                output[i] = toStack(iStack[i]);
            }
            
            return output;
        }
    }
    
    public static Object toObject(IIngredient iStack) {
        if(iStack == null)
            return null;
        else {
            if(iStack instanceof IOreDictEntry) {
                return toString((IOreDictEntry) iStack);
            } else if(iStack instanceof IItemStack) {
                return toStack((IItemStack) iStack);
            } else if(iStack instanceof IngredientStack) {
                return ((IngredientStack) iStack).getItems();
            } else
                return null;
        }
    }
    
    public static Object[] toObjects(IIngredient[] ingredient) {
        if(ingredient == null)
            return null;
        else {
            Object[] output = new Object[ingredient.length];
            for(int i = 0; i < ingredient.length; i++) {
                if(ingredient[i] != null) {
                    output[i] = toObject(ingredient[i]);
                } else
                    output[i] = "";
            }
            
            return output;
        }
    }
    
    public static String toString(IOreDictEntry entry) {
        return ((IOreDictEntry) entry).getName();
    }
    
    public static FluidStack toFluid(ILiquidStack iStack) {
        if(iStack == null) {
            return null;
        } else
            return FluidRegistry.getFluidStack(iStack.getName(), iStack.getAmount());
    }
    
    public static Fluid getFluid(ILiquidStack iStack) {
        if(iStack == null) {
            return null;
        } else
            return FluidRegistry.getFluid(iStack.getName());
        
    }
    
    public static FluidStack[] toFluids(ILiquidStack[] iStack) {
        FluidStack[] stack = new FluidStack[iStack.length];
        for(int i = 0; i < stack.length; i++)
            stack[i] = toFluid(iStack[i]);
        return stack;
    }

    public static Object[] toShapedObjects(IIngredient[][] ingredients) {
        if(ingredients == null)
            return null;
        else {
            ArrayList<Object> prep = new ArrayList<>();
            TCharSet usedCharSet = new TCharHashSet();

            prep.add("abc");
            prep.add("def");
            prep.add("ghi");
            char[][] map = new char[][]{{'a', 'b', 'c'}, {'d', 'e', 'f'}, {'g', 'h', 'i'}};
            for(int x = 0; x < ingredients.length; x++) {
                if(ingredients[x] != null) {
                    for(int y = 0; y < ingredients[x].length; y++) {
                        if(ingredients[x][y] != null && x < map.length && y < map[x].length) {
                            prep.add(map[x][y]);
                            usedCharSet.add(map[x][y]);
                            prep.add(InputHelper.toObject(ingredients[x][y]));
                        }
                    }
                }
            }

            for (int i = 0; i < 3; i++) {
                StringBuilder sb = new StringBuilder();
                if (prep.get(i) instanceof String){
                    String s = (String) prep.get(i);
                    for (int j = 0; j < 3; j++) {
                        char c = s.charAt(j);
                        if (usedCharSet.contains(c)){
                            sb.append(c);
                        }else {
                            sb.append(" ");
                        }
                    }

                    prep.set(i, sb.toString());
                }
            }


            return prep.toArray();
        }
    }

    public static <R> NonNullList<R> toNonNullList(R[] items){
        NonNullList<R> nonNullList = NonNullList.create();
        Collections.addAll(nonNullList, items);

        return nonNullList;
    }
    
}
