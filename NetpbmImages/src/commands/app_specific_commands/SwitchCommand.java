package commands.app_specific_commands;

import commands.Command;
import models.session.Session;
import models.session.SessionManager;
/**
 * Превключва към друга активна сесия.
 * Зарежда сесия по подаден идентификатор.
 */
public class SwitchCommand implements Command {
    /**
     * идентификаторът на желаната сесия.
     */
    private final int id;

    /**
     * Създава команда за смяна на сесия.
     *
     * @param id идентификатор на сесията
     */
    public SwitchCommand(int id) {
        this.id = id;
    }

    /**
     * Превключва активната сесия.
     *
     * @return резултат от операцията
     */
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
