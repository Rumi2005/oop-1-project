package commands.app_specific_commands;

import commands.Command;
import commands.utils.Utilities;
import models.images.Image;
import models.session.Session;
import models.session.SessionManager;
import java.io.IOException;

/**
 * Добавя ново изображение към сесията.
 * Зарежда допълнителен файл за обработка.
 */
public class AddCommand implements Command {
    /**
     * Съхранява пътя до файла.
     */
    private final String path;

    /**
     * Създава команда за добавяне на изображение.
     *
     * @param path път до файла
     */
    public AddCommand(String path) {
        this.path = path;
    }

    /**
     * Добавя изображение към активната сесия.
     */
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
