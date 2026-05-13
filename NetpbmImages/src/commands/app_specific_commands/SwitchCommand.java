package commands.app_specific_commands;

import commands.Command;
import models.session.Session;
import models.session.SessionManager;

public class SwitchCommand implements Command {
    private final int id;

    public SwitchCommand(int id) {
        this.id = id;
    }

    @Override
    public String execute() {
        boolean switched = SessionManager.switchSession(id);
        if (!switched)
            return "Session " + id + " does not exist.";
        Session current = SessionManager.getCurrentSession();
        SessionInfoCommand info = new SessionInfoCommand();
        return "You switched you session with ID: " + current.getId() + "!\n" + info.execute();
    }
}
