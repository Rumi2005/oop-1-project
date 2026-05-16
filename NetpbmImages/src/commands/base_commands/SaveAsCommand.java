package commands.base_commands;

import commands.Command;
import commands.utils.Utilities;
import models.images.Image;
import models.session.Session;
import models.session.SessionManager;

import java.io.IOException;
import java.util.List;
/**
 * Записва изображението в нов файл.
 * Позволява избор на ново име.
 */
public class SaveAsCommand implements Command {
    /**
     * Съхранява новия път до файла.
     */
    private final String newFileName;

    /**
     * Създава команда за запис в нов файл.
     *
     * @param newFileName нов път за запис
     */
    public SaveAsCommand(String newFileName) {
        this.newFileName = newFileName;
    }

    /**
     * Записва файла на ново място.
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
