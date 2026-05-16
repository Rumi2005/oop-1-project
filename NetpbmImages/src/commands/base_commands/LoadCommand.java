package commands.base_commands;

import commands.Command;
import commands.utils.Utilities;
import models.images.Image;
import models.session.Session;
import models.session.SessionManager;

import java.io.*;
/**
 * Отваря изображение от подаден файл.
 * Зарежда изображението в приложението.
 */
public class LoadCommand implements Command {
    /**
     * Съхранява пътя до файла.
     */
    private String path;

    /**
     * Създава команда за отваряне на файл.
     *
     * @param path път до изображението
     */
    public LoadCommand(String path) {
        this.path = path;
    }

    /**
     * Зарежда изображението от файл. Стартира нова сесия.
     */
    @Override
    public String execute() {
        try {
            Image image;
            File file = new File(path);
            if (file.exists())
                image = Utilities.load(path);
            else {
                image = createImage(path);
                Utilities.saveImage(image, path);
            }
            Session session = new Session(image, path);
            SessionManager.setCurrentSession(session);
            return "Session with ID: " + session.getId() + " started\nImage '" + path + "' added";

        } catch (IOException e) {
            return "Failed to load image: " + e.getMessage();
        }
    }

    private Image createImage(String path) throws IOException {
        String lower = path.toLowerCase();
        String magic;
        if(lower.endsWith(".pbm"))
            magic = "P1";
        else if(lower.endsWith(".pgm"))
            magic = "P2";
        else if(lower.endsWith(".ppm"))
            magic = "P3";
        else
            throw new IOException("Unsupported file type.");
        Image image = Utilities.imageFactory.get(magic).get();
        image.setWidth(1);
        image.setHeight(1);
        if (!magic.equals("P1"))
            image.setMaxVal(255);
        image.setData(new int[image.getWidth() * image.getHeight() * image.getChannels()]);
        return image;
    }
}
