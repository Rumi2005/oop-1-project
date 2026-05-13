package commands.utils;

import commands.transform_commands.*;
import models.images.Image;
import models.images.PBMImage;
import models.images.PGMImage;
import models.images.PPMImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utilities {
    private Utilities() {}

    public static Image load(String path) throws IOException {
        try (InputStream is = new BufferedInputStream(Files.newInputStream(Paths.get(path)))) {
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
                    throw new IOException("Unsupported format: " + magic);
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
                    throw new IOException("Invalid format");
            }
            image.setData(data);
            return image;
        }
    }

    private static int[] loadPBMAscii(InputStream is, int width, int height) throws IOException {
        int[] data = new int[width * height];
        for (int i = 0; i < data.length; i++)
            data[i] = Integer.parseInt(readToken(is));
        return data;
    }

    private static int[] loadPGMAscii(InputStream is, int width, int height, int maxVal) throws IOException {
        int[] data = new int[width * height];
        for (int i = 0; i < data.length; i++)
            data[i] = scale(Integer.parseInt(readToken(is)), maxVal);
        return data;
    }

    private static int[] loadPPMAscii(InputStream is, int width, int height, int maxVal) throws IOException {
        int[] data = new int[width*height*3];
        for (int i = 0; i < data.length; i++)
            data[i] = scale(Integer.parseInt(readToken(is)), maxVal);
        return data;
    }

    private static int scale(int value, int maxVal) {
        return (value * 255) / maxVal;
    }

    private static String readToken(InputStream is) throws IOException {
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

    public static final Map<String, Transformation> transformations = createTransformations();

    private static Map<String, Transformation> createTransformations() {
        Map<String, Transformation> map = new HashMap<>();
        map.put("grayscale", new GrayscaleCommand());
        map.put("monochrome", new MonochromeCommand());
        map.put("negative", new NegativeCommand());
        map.put("rotate left", new RotateCommand("left"));
        map.put("rotate right", new RotateCommand("right"));
        return map;
    }

    public static Image applyTransformations(Image image, List<String> pending) {
        for (String name : pending) {
            Transformation t = transformations.get(name.toLowerCase());
            if (t != null)
                image = t.transform(image);
        }
        return image;
    }

    public static void saveImage(Image image, String fileName) throws IOException {
        BufferedImage output = image.getImage();
        String extension = getExtension(fileName);
        ImageIO.write(output, extension, new File(fileName));
    }

    private static String getExtension(String fileName) {
        int dot = fileName.lastIndexOf('.');
        if (dot == -1)
            return "png";
        return fileName.substring(dot + 1).toLowerCase();
    }
}
