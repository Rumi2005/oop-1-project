package commands.base_commands;

import commands.Command;
import models.session.SessionManager;

public class CloseCommand implements Command {
    @Override
    public String execute() {
        if (!SessionManager.hasSession()) {
            return "No active session to close.";
        }
        int sessionId = SessionManager.getCurrentSession().getId();
        SessionManager.closeSession();
        return "Session with Id: " + sessionId + " closed successfully.";
    }
}
