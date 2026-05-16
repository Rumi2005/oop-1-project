package commands.base_commands;

import commands.Command;
import models.session.SessionManager;
/**
 * Затваря текущо отворената сесия.
 */
public class CloseCommand implements Command {
    /**
     * Затваря текущата сесия.
     */
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
