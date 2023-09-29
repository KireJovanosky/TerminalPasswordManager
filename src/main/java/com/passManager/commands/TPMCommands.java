package com.passManager.commands;


import com.passManager.commands.sub.CreateNewEntityCommand;
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
            CreateNewEntityCommand.class
        })
public class TPMCommands implements Callable<Integer> {
    final Integer SUCCESS = 0;
    final Integer FAILURE = 1;


    public static void main(String[] args) {
       int exitStatus = new CommandLine(new TPMCommands()).execute("save", "-s=github.com/login2", "-t=git2", "-c");
       System.exit(exitStatus);
    }


    @Override
    public Integer call() throws Exception {
        System.out.println("[TPM] test");
        return SUCCESS;
    }
}
