package commands.base_commands;

import commands.Command;

public class ExitCommand implements Command {
    @Override
    public String execute() {
        System.exit(0);
        return null;
    }
}
