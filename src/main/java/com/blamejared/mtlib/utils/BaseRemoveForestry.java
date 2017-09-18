package com.blamejared.mtlib.utils;

import forestry.api.recipes.ICraftingProvider;
import forestry.api.recipes.IForestryRecipe;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRemoveForestry<R extends IForestryRecipe> extends BaseUndoable {
    protected ICraftingProvider<R> provider;

    protected BaseRemoveForestry(String name, ICraftingProvider<R> provider) {
        super(name);
        this.provider = provider;
    }

    @Override
    public void apply() {
        List<R> toRemove = new ArrayList<>();

        for (R recipe : provider.recipes()) {
            if (checkIsRecipe(recipe)) toRemove.add(recipe);
        }

        for (R recipe : toRemove) {
            provider.removeRecipe(recipe);
        }
    }

    public abstract boolean checkIsRecipe(R recipe);
}
