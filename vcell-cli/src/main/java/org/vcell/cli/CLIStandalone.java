package org.vcell.cli;

import org.vcell.cli.run.ExecuteImplementation;
import org.vcell.cli.vcml.ConvertImplementation;
import org.vcell.cli.commands.*;
import picocli.CommandLine;
import picocli.CommandLine.Command;


@Command(name = "CLIStandalone", subcommands = {ConvertImplementation.class, ExecuteImplementation.class, VersionCommand.class, CommandLine.HelpCommand.class})
public class CLIStandalone {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new CLIStandalone()).execute(args);
        System.exit(exitCode);
    }

}




