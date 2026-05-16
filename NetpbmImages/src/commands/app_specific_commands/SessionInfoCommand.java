package commands.app_specific_commands;

import commands.Command;
import models.session.Session;
import models.session.SessionManager;

import java.util.List;

/**
 * Показва информация за текущата сесия.
 * Извежда заредените изображения и операции.
 */
public class SessionInfoCommand implements Command {
    /**
     * Извежда информация за активната сесия.
     */
    @Override
    public String execute() {
        Session session = SessionManager.getCurrentSession();
        if (session == null)
            return "No active session.";

        StringBuilder output = new StringBuilder();
        output.append("Name of images in session: ");
        List<String> fileNames = session.getFileNames();
        for (String file : fileNames)
            output.append(file).append(" ");
        output.append("\nPending transformations: ");
        List<String> transformations = session.getPendingTransformations();
        if (transformations.isEmpty())
            output.append("None");
        else
            for (String t : transformations)
                output.append(t).append(", ");
        return output.toString();
    }
}
