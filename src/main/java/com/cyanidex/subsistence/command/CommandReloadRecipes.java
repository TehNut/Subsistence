package com.cyanidex.subsistence.command;

import com.cyanidex.subsistence.core.recipe.config.MachineRecipes;
import com.google.common.base.Stopwatch;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandReloadRecipes extends CommandBase {

    @Override
    public String getName() {
        return "reload-recipes";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/subsistence reload-recipes";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        MachineRecipes.initRecipes(true); // FIXME - JEI does not see new recipes after reloading
        sender.sendMessage(new TextComponentTranslation("command.subsistence.reloadRecipes.time", stopwatch.stop().toString()));
    }
}
