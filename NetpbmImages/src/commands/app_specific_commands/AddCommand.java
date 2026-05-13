package commands.app_specific_commands;

import commands.Command;
import commands.utils.Utilities;
import models.images.Image;
import models.session.Session;
import models.session.SessionManager;
import java.io.IOException;

public class AddCommand implements Command {
    private final String path;

    public AddCommand(String path) {
        this.path = path;
    }

    @Override
    public String execute() {
        Session session = SessionManager.getCurrentSession();
        if (session == null)
            return "No active session.";
        try {
            Image image = Utilities.load(path);
            session.addImage(image, path);
            return "Image '" + path + "' added";

        } catch (IOException e) {
            return "Failed to add image: " + e.getMessage();
        }
    }
}
