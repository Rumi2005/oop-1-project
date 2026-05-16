package models.images;

import java.awt.image.BufferedImage;

/**
 * Представя изображение в PPM формат.
 * използва RGB цветови стойности.
 */
public class PPMImage extends Image {
    /**
     * Създава PPM изображение.
     */
    public PPMImage(){
        this.imageType = BufferedImage.TYPE_INT_RGB;
    }

    /**
     * Създава празно PPM изображение.
     *
     * @return ново празно изображение
     */
    @Override
    public Image createEmpty() {
        return new PPMImage();
    }

    /**
     * Връща броя цветови канали.
     *
     * @return броят канали
     */
    @Override
    public int getChannels() {
        return 3;
    }

    /**
     * Проверява дали изображението е цветно.
     *
     * @return true, защото PPM е цветно
     */
    @Override
    public boolean isColored() {
        return true;
    }

    /**
     * Проверява дали изображението е monochrome.
     *
     * @return false, защото PPM не е monochrome
     */
    @Override
    public boolean isMonochrome() {
        int[] data = getData();
        int max = getMaxVal();
        for (int i = 0; i < data.length; i += 3) {
            int r = data[i];
            int g = data[i + 1];
            int b = data[i + 2];

            boolean black = r == 0 && g == 0 && b == 0;
            boolean white = r == max && g == max && b == max;
            if (!black && !white)
                return false;
        }
        return true;
    }
}
