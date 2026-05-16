package commands.base_commands;

import commands.Command;
import commands.transform_commands.*;
import commands.utils.Utilities;
import models.images.Image;
import models.session.Session;
import models.session.SessionManager;
import java.io.IOException;
import java.util.List;

/**
 * Записва текущото изображение.
 * Съхранява промените във файла.
 */
public class SaveCommand implements Command {
    /**
     * Записва текущите промени.
     */
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
                Utilities.saveImage(image, fileNames.get(i));
            }
            pending.clear();
            return "";
        } catch (IOException e) {
            return "Save failed: " + e.getMessage();
        }
    }
}
