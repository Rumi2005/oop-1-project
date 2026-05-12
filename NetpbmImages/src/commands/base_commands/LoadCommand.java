package commands.base_commands;

import commands.Command;
import models.images.Image;
import models.images.PBMImage;
import models.images.PGMImage;
import models.images.PPMImage;
import models.session.Session;
import models.session.SessionManager;

import java.io.*;

public class LoadCommand implements Command {
    private String path;

    public LoadCommand(String path) {
        this.path = path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String execute() {
        try {
            Image image = load(path);
            Session session = new Session(image);
            SessionManager.setCurrentSession(session);
            return "Image loaded successfully. Session ID: " + session.getId();
        } catch (IOException e) {
            return "Failed to load image: " + e.getMessage();
        }
    }

    private Image load(String path) throws IOException {
        try (InputStream is = new BufferedInputStream(new FileInputStream(path))) {
            String magic = readToken(is);
            Image image;
            switch (magic) {
                case "P1":
                    image = new PBMImage();
                    break;
                case "P2":
                    image = new PGMImage();
                    break;
                case "P3":
                    image = new PPMImage();
                    break;
                default:
                    throw new IOException("Unsupported NetPBM format: " + magic);
            }
            int width = Integer.parseInt(readToken(is));
            int height = Integer.parseInt(readToken(is));
            image.setWidth(width);
            image.setHeight(height);
            int maxVal = 1;
            if (!magic.equals("P1"))
                maxVal = Integer.parseInt(readToken(is));
            image.setMaxVal(maxVal);
            int[] data;
            switch (magic) {
                case "P1":
                    data = loadPBMAscii(is, width, height);
                    break;
                case "P2":
                    data = loadPGMAscii(is, width, height, maxVal);
                    break;
                case "P3":
                    data = loadPPMAscii(is, width, height, maxVal);
                    break;
                default:
                    throw new IOException("Invalid data format");
            }
            image.setData(data);
            return image;
        }
    }

    private String readToken(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        int c;
        while ((c = is.read()) != -1) {
            if (c == '#')
                while ((c = is.read()) != '\n' && c != -1);
            else if (!Character.isWhitespace(c))
                break;
        }
        if (c == -1)
            throw new EOFException();
        do {
            sb.append((char) c);
            c = is.read();
        } while (c != -1 && !Character.isWhitespace(c));
        return sb.toString();
    }

    private int[] loadPBMAscii(InputStream is, int width, int height) throws IOException {
        int[] data = new int[width*height];
        for (int i = 0; i < data.length; i++)
            data[i] = Integer.parseInt(readToken(is));
        return data;
    }

    private int[] loadPGMAscii(InputStream is, int width, int height, int maxVal) throws IOException {
        int[] data = new int[width*height];
        for (int i = 0; i < data.length; i++)
            data[i] = scale(Integer.parseInt(readToken(is)), maxVal);
        return data;
    }

    private int[] loadPPMAscii(InputStream is, int width, int height, int maxVal) throws IOException {
        int[] data = new int[width*height*3];
        for (int i = 0; i < data.length; i++)
            data[i] = scale(Integer.parseInt(readToken(is)), maxVal);
        return data;
    }

    private int scale(int value, int maxVal) {
        return (value*255)/maxVal;
    }
}
