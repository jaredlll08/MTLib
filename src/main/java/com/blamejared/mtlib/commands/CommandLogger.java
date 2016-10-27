package com.blamejared.mtlib.commands;

import minetweaker.MineTweakerAPI;
import minetweaker.api.player.IPlayer;
import minetweaker.api.server.ICommandFunction;

import java.util.Collection;

import static com.blamejared.mtlib.helpers.LogHelper.logPrinted;

public abstract class CommandLogger implements ICommandFunction {

    @Override
    public void execute(String[] strings, IPlayer player) {
        MineTweakerAPI.logCommand(getList().size() + getName() + ":");
        for(String entry : getList()) {
            if(!entry.equals(""))
                MineTweakerAPI.logCommand(entry);
        }
        logPrinted(player);
    }

    public abstract Collection<? extends String> getList();

    public abstract String getName();
}
