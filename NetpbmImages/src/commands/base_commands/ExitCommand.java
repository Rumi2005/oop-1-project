package commands.base_commands;

import commands.Command;
/**
 * Прекратява изпълнението на приложението.
 * използва се за изход от програмата.
 */
public class ExitCommand implements Command {
    /**
     * Спира работата на приложението.
     */
    @Override
    public String execute() {
        System.exit(0);
        return null;
    }
}
