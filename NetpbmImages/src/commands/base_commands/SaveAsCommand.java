package commands.base_commands;

import commands.Command;
import commands.utils.Utilities;
import models.images.Image;
import models.session.Session;
import models.session.SessionManager;

import java.io.IOException;
import java.util.List;

public class SaveAsCommand implements Command {
    private final String newFileName;
    public SaveAsCommand(String newFileName) {
        this.newFileName = newFileName;
    }

    @Override
    public String execute() {
        Session session = SessionManager.getCurrentSession();
        if (session == null)
            return "No active session.";
        List<Image> images = session.getImages();
        List<String> fileNames = session.getFileNames();
        List<String> pending = session.getPendingTransformations();
        try {
            for (int i = 0; i < images.size(); i++) {
                Image image = Utilities.applyTransformations(images.get(i), pending);
                String target = (i == 0) ? newFileName : fileNames.get(i);
                Utilities.saveImage(image, target);
            }
            pending.clear();
            return "";
        } catch (IOException e) {
            return "Save failed: " + e.getMessage();
        }
    }
}
