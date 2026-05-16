package commands.app_specific_commands;

import commands.Command;
import models.session.Session;
import models.session.SessionManager;

/**
 * Отменя последната извършена промяна.
 * Възстановява предишното състояние на изображението.
 */
public class UndoCommand implements Command {
    /**
     * Премахва последната операция.
     */
    @Override
    public String execute() {
        Session session = SessionManager.getCurrentSession();
        if (session == null)
            return "No active session.";
        session.undoTransformation();
        return "";
    }
}
