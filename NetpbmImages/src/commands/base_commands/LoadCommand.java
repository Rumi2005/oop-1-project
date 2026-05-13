package commands.base_commands;

import commands.Command;
import commands.utils.Utilities;
import models.images.Image;
import models.session.Session;
import models.session.SessionManager;

import java.io.*;

public class LoadCommand implements Command {
    private String path;

    public LoadCommand(String path) {
        this.path = path;
    }

    @Override
    public String execute() {
        try {
            Image image = Utilities.load(path);
            Session session = new Session(image, path);
            SessionManager.setCurrentSession(session);
            return "Session with ID: " + session.getId() + " started\nImage '" + path + "' added";

        } catch (IOException e) {

            return "Failed to load image: "
                    + e.getMessage();
        }
    }
}
