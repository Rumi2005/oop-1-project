package commands.app_specific_commands;

import commands.Command;
import models.session.Session;
import models.session.SessionManager;

public class UndoCommand implements Command {
    @Override
    public String execute() {
        Session session = SessionManager.getCurrentSession();
        if (session == null)
            return "No active session.";
        session.undoTransformation();
        return "";
    }
}
