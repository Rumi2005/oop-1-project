package commands.app_specific_commands;

import commands.Command;
import models.images.Image;
import models.session.Session;
import models.session.SessionManager;

import java.util.List;

/**
 * Създава колаж от две изображения.
 * Поддържа хоризонтално или вертикално обединяване.
 */
public class CollageCommand implements Command {
    /**
     * Посоката на създаване на колажа.
     */
    private final String direction;
    /**
     * името на първото изображение.
     */
    private final String firstImage;
    /**
     * името на второто изображение.
     */
    private final String secondImage;
    /**
     * името на резултатното изображение.
     */
    private final String outputImage;

    /**
     * Създава команда за колаж на две изображения.
     *
     * @param direction посока на колажа
     * @param firstImage първо изображение
     * @param secondImage второ изображение
     * @param outputImage име на резултатното изображение
     */
    public CollageCommand(String direction, String firstImage, String secondImage, String outputImage) {
        this.direction = direction.toLowerCase();
        this.firstImage = firstImage;
        this.secondImage = secondImage;
        this.outputImage = outputImage;
    }

    /**
     * Създава колаж от избраните изображения.
     *
     * @return резултат от изпълнението на командата
     */
    @Override
    public String execute() {
        Session session = SessionManager.getCurrentSession();
        if (session == null)
            return "No active session.";

        List<Image> images = session.getImages();
        List<String> names = session.getFileNames();
        int index1 = names.indexOf(firstImage);
        int index2 = names.indexOf(secondImage);
        if (index1 == -1 || index2 == -1)
            return "One or both images aren't found.";

        Image image1 = images.get(index1);
        Image image2 = images.get(index2);
        if (!image1.sameFormat(image2))
            return "Images need to be the same format.";
        Image result = createCollage(image1, image2);
        session.addImage(result, outputImage);
        return "New collage: '" + outputImage + "' created";
    }

    /**
     * Създава колаж от две изображения.
     *
     * @param image1 първото изображение
     * @param image2 второто изображение
     * @return новото изображение след обединяване
     */
    private Image createCollage(Image image1, Image image2) {
        int width = image1.getWidth();
        int height = image1.getHeight();
        int channels = image1.getChannels();
        int newWidth = direction.equals("horizontal") ? width * 2 : width;
        int newHeight = direction.equals("vertical") ? height * 2 : height;
        int[] data = new int[newWidth * newHeight * channels];

        copyImage(image1.getData(), data, width, height, newWidth, channels, 0, 0);
        int offsetX = direction.equals("horizontal") ? width : 0;
        int offsetY = direction.equals("vertical") ? height : 0;
        copyImage(image2.getData(), data, width, height, newWidth, channels, offsetX, offsetY);
        Image result = image1.createEmpty();
        result.setWidth(newWidth);
        result.setHeight(newHeight);
        result.setMaxVal(image1.getMaxVal());
        result.setData(data);
        return result;
    }

    /**
     * Копира пикселни данни от изходно изображение в целево изображение.
     *
     * @param source масивът с пикселни данни на изходното изображение
     * @param target масивът с пикселни данни на целевото изображение
     * @param width ширината на изходното изображение
     * @param height височината на изходното изображение
     * @param targetWidth ширината на целевото изображение
     * @param channels броят цветови канали на изображението
     * @param offsetX хоризонталното отместване при копиране
     * @param offsetY вертикалното отместване при копиране
     */
    private void copyImage(int[] source, int[] target, int width, int height, int targetWidth, int channels, int offsetX, int offsetY) {
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                int sourceIndex = (y*width+x) * channels;
                int targetIndex = ((y+offsetY) * targetWidth + (x+offsetX)) * channels;
                if (channels >= 0)
                    System.arraycopy(source, sourceIndex, target, targetIndex, channels);
            }
    }
}
