package commands.base_commands;

import commands.Command;
import commands.utils.Utilities;
import models.images.Image;
import models.session.Session;
import models.session.SessionManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
                String oldPath = fileNames.get(i);
                String target = (i == 0) ? newFileName : oldPath;
                Utilities.saveImage(image, target);
                if (i == 0 && !oldPath.equals(newFileName)) {
                    Files.deleteIfExists(Paths.get(oldPath));
                    fileNames.set(0, newFileName);
                }
            }
            fileNames.set(0, newFileName);
            pending.clear();
            return null;
        } catch (IOException e) {
            return "Save failed: " + e.getMessage();
        }
    }
}
