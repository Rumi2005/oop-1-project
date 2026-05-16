package commands.utils;

import commands.transform_commands.*;
import commands.utils.loader.ImageLoader;
import models.images.Image;
import models.images.PBMImage;
import models.images.PGMImage;
import models.images.PPMImage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.function.Supplier;

/**
 * Помощен клас за зареждане и обработка на изображения.
 * Съдържа статични методи и фабрики за работа с формати и трансформации.
 */
public class Utilities {
    /**
     * Забранява създаването на инстанции на класа.
     */
    private Utilities() {}

    /**
     * Съхранява loaders за различните формати изображения.
     */
    public static final Map<String, ImageLoader> loaders = createLoaders();

    /**
     * Създава map с loaders за поддържаните формати.
     * @return map с loaders
     */
    private static Map<String, ImageLoader> createLoaders() {
        Map<String, ImageLoader> map = new HashMap<>();
        map.put("P1", (is, width, height, maxVal)
                -> loadPBM(is, width, height));
        map.put("P2", Utilities::loadPGM);
        map.put("P3", Utilities::loadPPM);
        return map;
    }

    /**
     * Съдържа фабрики за създаване на изображения.
     */
    public static final Map<String, Supplier<Image>> imageFactory = createImageFactories();

    /**
     * Създава фабрики за различните типове изображения.
     * @return map с image factories
     */
    private static Map<String, Supplier<Image>> createImageFactories() {
        Map<String, Supplier<Image>> map = new HashMap<>();
        map.put("P1", PBMImage::new);
        map.put("P2", PGMImage::new);
        map.put("P3", PPMImage::new);
        return map;
    }

    /**
     * Зарежда изображение от файл.
     *
     * @param path пътят до файла
     * @return зареденото изображение
     * @throws IOException при грешка при четене
     */
    public static Image load(String path) throws IOException {
        try (InputStream is = new BufferedInputStream(Files.newInputStream(Paths.get(path)))) {
            String magic = readToken(is);
            Supplier<Image> factory = Utilities.imageFactory.get(magic);
            if (factory == null)
                throw new IOException("Unsupported format: " + magic);
            Image image = factory.get();
            int width = Integer.parseInt(readToken(is));
            int height = Integer.parseInt(readToken(is));
            image.setWidth(width);
            image.setHeight(height);
            int maxVal = 1;
            if (!magic.equals("P1"))
                maxVal = Integer.parseInt(readToken(is));
            image.setMaxVal(maxVal);
            int[] data;
            ImageLoader loader = Utilities.loaders.get(magic);
            if (loader == null)
                throw new IOException("Invalid format");
            data = loader.load(is, width, height, maxVal);
            image.setData(data);
            return image;
        }
    }

    /**
     * Зарежда PBM изображение от поток.
     *
     * @param is входният поток
     * @param width ширината на изображението
     * @param height височината на изображението
     * @return масив с пикселни данни
     * @throws IOException при грешка при четене
     */
    private static int[] loadPBM(InputStream is, int width, int height) throws IOException {
        int[] data = new int[width * height];
        for (int i = 0; i < data.length; i++)
            data[i] = Integer.parseInt(readToken(is));
        return data;
    }

    /**
     * Зарежда PGM изображение от поток.
     *
     * @param is входният поток
     * @param width ширината на изображението
     * @param height височината на изображението
     * @param maxVal максималната стойност на пиксел
     * @return масив с пикселни данни
     * @throws IOException при грешка при четене
     */
    private static int[] loadPGM(InputStream is, int width, int height, int maxVal) throws IOException {
        int[] data = new int[width * height];
        for (int i = 0; i < data.length; i++)
            data[i] = scale(Integer.parseInt(readToken(is)), maxVal);
        return data;
    }

    /**
     * Зарежда PPM изображение от поток.
     *
     * @param is входният поток
     * @param width ширината на изображението
     * @param height височината на изображението
     * @param maxVal максималната стойност на пиксел
     * @return масив с пикселни данни
     * @throws IOException при грешка при четене
     */
    private static int[] loadPPM(InputStream is, int width, int height, int maxVal) throws IOException {
        int[] data = new int[width*height*3];
        for (int i = 0; i < data.length; i++)
            data[i] = scale(Integer.parseInt(readToken(is)), maxVal);
        return data;
    }

    /**
     * Скалира стойност на пиксел към диапазон 255.
     *
     * @param value стойността на пиксела
     * @param maxVal максималната стойност
     * @return скалираната стойност
     */
    private static int scale(int value, int maxVal) {
        return (value * 255) / maxVal;
    }

    /**
     * Прочита следващ token от входния поток.
     *
     * @param is входният поток
     * @return прочетеният token
     * @throws IOException при грешка при четене
     */
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

    /**
     * Съдържа всички налични трансформации.
     */
    public static final Map<String, Transformation> transformations = createTransformations();

    /**
     * Създава map с поддържаните трансформации.
     *
     * @return map с трансформации
     */
    private static Map<String, Transformation> createTransformations() {
        Map<String, Transformation> map = new HashMap<>();
        map.put("grayscale", new GrayscaleCommand());
        map.put("monochrome", new MonochromeCommand());
        map.put("negative", new NegativeCommand());
        map.put("rotate left", new RotateCommand("left"));
        map.put("rotate right", new RotateCommand("right"));
        return map;
    }

    /**
     * Прилага всички трансформации върху изображение.
     *
     * @param image изображението за обработка
     * @param pending списък с трансформации
     * @return трансформираното изображение
     */
    public static Image applyTransformations(Image image, List<String> pending) {
        for (String name : pending) {
            Transformation t = transformations.get(name.toLowerCase());
            if (t != null)
                image = t.transform(image);
        }
        return image;
    }

    /**
     * Записва изображение във файл.
     *
     * @param image изображението за запис
     * @param fileName пътят до изходния файл
     * @throws IOException при грешка при запис
     */
    public static void saveImage(Image image, String fileName) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            String magic;
            if(image.getChannels() == 3)
                magic = "P3";
            else if(image.isMonochrome())
                magic = "P1";
            else
                magic = "P2";
            writer.println(magic);
            writer.println(image.getWidth() + " " + image.getHeight());
            if (!magic.equals("P1"))
                writer.println(image.getMaxVal());
            int[] data = image.getData();
            int channels = image.getChannels();
            int width = image.getWidth();
            for (int i = 0; i < data.length; i += channels) {
                for (int c = 0; c < channels; c++) {
                    writer.print(data[i + c]);
                    writer.print(" ");
                }
                if (((i / channels) + 1) % width == 0)
                    writer.println();
            }
        }
    }
}
