package com.blamejared.mtlib;

import minetweaker.*;
import minetweaker.mc1112.brackets.ItemBracketHandler;
import minetweaker.runtime.providers.ScriptProviderDirectory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;

import java.io.File;

import static com.blamejared.mtlib.reference.Reference.*;

@Mod(modid = MOD_ID, name = MOD_NAME, version = VERSION)
public class MTLib {

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        scriptDirBase = new File(event.getModConfigurationDirectory(), MOD_NAME + File.separator);
        scriptDirPreInit = new File(scriptDirBase, "preInit" + File.separator);
        if(!scriptDirPreInit.exists()) {
            scriptDirPreInit.mkdirs();
            
        }
        MineTweakerAPI.registerBracketHandler(new ItemBracketHandler());
        ItemBracketHandler.rebuildItemRegistry();
        MineTweakerAPI.tweaker.setScriptProvider(new ScriptProviderDirectory(scriptDirPreInit));
        MineTweakerImplementationAPI.reload();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
        scriptDirInit = new File(scriptDirBase, "init" + File.separator);
        System.out.println(">>>");
        System.out.println(scriptDirInit.getAbsolutePath());
        System.out.println(scriptDirInit.exists());
        if(!scriptDirInit.exists()) {
            scriptDirInit.mkdirs();
        }
        MineTweakerAPI.registerBracketHandler(new ItemBracketHandler());
        ItemBracketHandler.rebuildItemRegistry();
        MineTweakerAPI.tweaker.setScriptProvider(new ScriptProviderDirectory(scriptDirInit));
        MineTweakerImplementationAPI.reload();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        scriptDirPostInit = new File(scriptDirBase, "postInit" + File.separator);
        if(!scriptDirPostInit.exists()) {
            scriptDirPostInit.mkdirs();
        }
        MineTweakerAPI.registerBracketHandler(new ItemBracketHandler());
        ItemBracketHandler.rebuildItemRegistry();
        MineTweakerAPI.tweaker.setScriptProvider(new ScriptProviderDirectory(scriptDirPostInit));
        MineTweakerImplementationAPI.reload();
    }
}
