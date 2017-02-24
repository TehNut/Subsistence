package com.cyanidex.subsistence.command;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.CommandTreeBase;

public class CommandSubsistence extends CommandTreeBase {

    public CommandSubsistence() {
        addSubcommand(new CommandReloadRecipes());
    }

    @Override
    public String getName() {
        return "subsistence";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        String params = "[";
        for (ICommand command : getSubCommands())
            params += command.getName() + "|";

        if (params.endsWith("|"))
            params = params.substring(0, params.length() - 1);

        return params + "]";
    }
}
