package models.images;

import java.awt.image.BufferedImage;

/**
 * Представя изображение в PBM формат.
 * Използва monochrome пикселни стойности.
 */
public class PBMImage extends Image {
    /**
     * Създава PBM изображение.
     */
    public PBMImage(){
        setImageType(BufferedImage.TYPE_BYTE_BINARY);
    }

    /**
     * Преобразува изображението в BufferedImage.
     *
     * @return BufferedImage представяне
     */
    @Override
    public BufferedImage getImage() {
        int[] original = getData().clone();
        for(int i = 0; i < getData().length; i++)
            this.data[i] = (this.data[i] + 1) % 2;
        BufferedImage image = super.getImage();
        this.setData(original);
        return image;
    }

    /**
     * Създава празно PBM изображение.
     *
     * @return ново празно изображение
     */
    @Override
    public Image createEmpty() {
        return new PBMImage();
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
     * @return false, защото PBM не е цветно
     */
    @Override
    public boolean isColored() {
        return false;
    }

    /**
     * Проверява дали изображението е monochrome.
     *
     * @return true, защото PBM е monochrome
     */
    @Override
    public boolean isMonochrome() {
        return true;
    }
}
