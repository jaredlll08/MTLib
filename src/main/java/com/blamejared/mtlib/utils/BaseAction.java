package com.blamejared.mtlib.utils;


import crafttweaker.IAction;

public abstract class BaseAction implements IAction {
    
    // Holds the name of the mod / machine this action manipulates
    protected final String name;
    
    // Basic indicator, if the action was successful and can be reverted
    protected boolean success = false;
    
    protected BaseAction(String name) {
        this.name = name;
    }
    
    protected String getRecipeInfo() {
        return "Unknown item";
    }
    
    
    @Override
    public String describe() {
        return String.format("Altering %s Recipe(s) for %s", this.name, this.getRecipeInfo());
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        
        if(!(obj instanceof BaseAction)) {
            return false;
        }
        
        BaseAction u = (BaseAction) obj;
        
        return name.equals(u.name);
    }
    
    @Override
    public int hashCode() {
        return name.hashCode();
    }
    
}
