package models.images;

import java.awt.image.BufferedImage;

public class PGMImage extends Image {
    /**
     * Създава PGM изображение.
     */
    public PGMImage(){
        this.imageType = BufferedImage.TYPE_BYTE_GRAY;
    }

    /**
     * Създава празно PGM изображение.
     *
     * @return ново празно изображение
     */
    @Override
    public Image createEmpty() {
        return new PGMImage();
    }

    /**
     * Връща броя цветови канали.
     *
     * @return броят канали
     */
    @Override
    public int getChannels() {
        return 1;
    }

    /**
     * Проверява дали изображението е цветно.
     *
     * @return false, защото PGM не е цветно
     */
    @Override
    public boolean isColored() {
        return false;
    }

    /**
     * Проверява дали изображението е monochrome.
     *
     * @return false, защото PGM е grayscale
     */
    @Override
    public boolean isMonochrome() {
        for (int value : getData())
            if (value != 0 && value != getMaxVal())
                return false;
        return true;
    }
}
