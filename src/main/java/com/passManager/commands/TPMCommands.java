package com.passManager.commands;


import com.passManager.commands.sub.CreateNewEntityCommand;
import com.passManager.commands.sub.DeleteEntityCommand;
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
            DeleteEntityCommand.class
        })
public class TPMCommands implements Callable<Integer> {
    final Integer SUCCESS = 0;
    final Integer FAILURE = 1;
    private String method;


    public static void main(String[] args) {
        CommandLine commandLine = new CommandLine(new TPMCommands());
        int exitStatus = commandLine.execute("delete", "-t=git");
        System.exit(exitStatus);
    }


    @Override
    public Integer call() throws Exception {
        System.out.println("[TPM] test");
        return SUCCESS;
    }
}
