package com.blamejared.mtlib.utils;


import com.blamejared.mtlib.helpers.LogHelper;

import java.util.*;
import java.util.Map.Entry;

public abstract class BaseMapAddition<K, V> extends BaseMapModification<K, V> {
    
    protected final HashMap<K, V> overwritten;
    
    protected BaseMapAddition(String name, Map<K, V> map) {
        super(name, map);
        this.overwritten = new HashMap<K, V>();
    }
    
    protected BaseMapAddition(String name, Map<K, V> map, Map<K, V> recipes) {
        this(name, map);
        this.recipes.putAll(recipes);
    }
    
    @Override
    public void apply() {
        if(recipes.isEmpty())
            return;
        
        for(Entry<K, V> entry : recipes.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            V oldValue = map.put(key, value);
            
            if(oldValue != null) {
                LogHelper.logWarning(String.format("Overwritten %s Recipe for %s", name, getRecipeInfo(new AbstractMap.SimpleEntry<K, V>(entry.getKey(), value))));
                overwritten.put(key, oldValue);
            }
            
            successful.put(key, value);
        }
    }
    
    @Override
    public String describe() {
        return String.format("Adding %d %s Recipe(s) for %s", recipes.size(), name, getRecipeInfo());
    }
}
