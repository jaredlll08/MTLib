package com.blamejared.mtlib.utils;


import com.blamejared.mtlib.helpers.LogHelper;

import java.util.Map;

public abstract class BaseMapRemoval<K, V> extends BaseMapModification<K, V> {
    
    protected BaseMapRemoval(String name, Map<K, V> map) {
        super(name, map);
    }
    
    protected BaseMapRemoval(String name, Map<K, V> map, Map<K, V> recipes) {
        this(name, map);
        
        if(recipes != null) {
            this.recipes.putAll(recipes);
        }
    }
    
    @Override
    public void apply() {
        if(recipes.isEmpty())
            return;
        
        for(K key : recipes.keySet()) {
            V oldValue = map.remove(key);
            
            if(oldValue != null) {
                successful.put(key, oldValue);
            } else {
                LogHelper.logError(String.format("Error removing %s Recipe : null object", name));
            }
        }
    }
    
    @Override
    public String describe() {
        return String.format("Removing %d %s Recipe(s) for %s", recipes.size(), name, getRecipeInfo());
    }
    
}
