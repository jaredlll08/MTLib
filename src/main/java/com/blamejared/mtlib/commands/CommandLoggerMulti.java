package com.blamejared.mtlib.commands;

import com.blamejared.mtlib.helpers.StringHelper;
import minetweaker.MineTweakerImplementationAPI;
import minetweaker.api.player.IPlayer;
import minetweaker.api.server.ICommandFunction;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class CommandLoggerMulti implements ICommandFunction {

    @Override
    public void execute(String[] arguments, IPlayer player) {
        List<String> args = StringHelper.toLowerCase(Arrays.asList(arguments));
        if(!getLists().keySet().containsAll(args)) {
            if(player != null) {
                player.sendChat(MineTweakerImplementationAPI.platform.getMessage("Invalid arguments for command. Valid arguments: " + StringHelper.join(getLists().keySet(), ", ")));
            }
        } else {
            getLists().get(args.get(0)).execute(arguments, player);
            if(player != null) {
                player.sendChat(MineTweakerImplementationAPI.platform.getMessage("List generated; see minetweaker.log in your minecraft dir"));
            }
        }
    }

    public abstract Map<String, ICommandFunction> getLists();

}
