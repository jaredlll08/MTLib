package com.blamejared.mtlib.utils;


import crafttweaker.IAction;

import java.util.LinkedList;

public class BaseMultiModification extends BaseAction {
    
    protected final LinkedList<IAction> actions;
    
    protected BaseMultiModification(String name) {
        super(name);
        this.actions = new LinkedList<>();
    }
    
    @Override
    public void apply() {
        for(IAction action : actions) {
            action.apply();
        }
    }
    
    @Override
    public String describe() {
        return String.format("Applying %d actions for %s Recipe change", this.actions.size(), this.name);
    }
    
}
