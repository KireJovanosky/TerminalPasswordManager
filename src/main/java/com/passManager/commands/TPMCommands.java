package com.passManager.commands;


import com.passManager.commands.sub.CreateNewEntityCommand;
import com.passManager.commands.sub.DeleteEntityCommand;
import com.passManager.commands.sub.GetPasswordEntityCommand;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "TPM",
        version = "1.0.0",
        mixinStandardHelpOptions = true,
        description = "Terminal password manager tool",
        requiredOptionMarker = '*',
        optionListHeading = "%nPlease choose one of the following options:%n",
        commandListHeading = "%nSubcommands are: %n",
        subcommands = {
            CreateNewEntityCommand.class,
            DeleteEntityCommand.class,
            GetPasswordEntityCommand.class
        })
public class TPMCommands implements Callable<Integer> {

    public static void main(String[] args) {
        CommandLine commandLine = new CommandLine(new TPMCommands());
        int exitStatus = commandLine.execute("get", "--id=152");
        System.exit(exitStatus);
    }
    //TODO: retrieve password into clipboard and tests

    @Override
    public Integer call() throws Exception {
        return 0;
    }
}
