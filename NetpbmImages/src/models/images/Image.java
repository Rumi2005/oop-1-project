package models.images;

import java.awt.image.BufferedImage;

/**
 * Абстрактен клас за представяне на изображение.
 * Съдържа общи свойства и поведение за всички формати.
 */
public abstract class Image {
    /**
     * Типът на изображението.
     */
    protected int imageType;
    /**
     * Ширината на изображението.
     */
    protected int width;
    /**
     * Височината на изображението.
     */
    protected int height;
    /**
     * Максималната стойност на пиксел.
     */
    protected int maxVal;
    /**
     * Пикселните данни на изображението.
     */
    protected int[] data;

    /**
     * Създава празно изображение от същия тип.
     *
     * @return ново празно изображение
     */
    public abstract Image createEmpty();

    /**
     * Връща броя цветови канали.
     *
     * @return броят канали
     */
    public abstract int getChannels();

    /**
     * Проверява дали изображението е цветно.
     *
     * @return true ако е цветно
     */
    public abstract boolean isColored();

    /**
     * Проверява дали изображението е monochrome.
     *
     * @return true ако е monochrome
     */
    public abstract boolean isMonochrome();

    /**
     * Връща типа на изображението.
     *
     * @return типът на изображението
     */
    public int getImageType() {
        return imageType;
    }

    /**
     * Задава типа на изображението.
     *
     * @param imageType типът на изображението
     */
    public void setImageType(int imageType) {
        this.imageType = imageType;
    }

    /**
     * Връща ширината на изображението.
     *
     * @return ширината
     */
    public int getWidth() {
        return width;
    }

    /**
     * Задава ширината на изображението.
     *
     * @param width ширината
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Връща височината на изображението.
     *
     * @return височината
     */
    public int getHeight() {
        return height;
    }

    /**
     * Задава височината на изображението.
     *
     * @param height височината
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Връща пикселните данни.
     *
     * @return масивът с данни
     */
    public int[] getData() {
        return data;
    }

    /**
     * Задава пикселните данни.
     *
     * @param data масивът с данни
     */
    public void setData(int[] data) {
        this.data = data;
    }

    /**
     * Връща максималната стойност на пиксел.
     *
     * @return максималната стойност
     */
    public int getMaxVal() {
        return maxVal;
    }

    /**
     * Задава максималната стойност на пиксел.
     *
     * @param maxVal максималната стойност
     */
    public void setMaxVal(int maxVal) {
        this.maxVal = maxVal;
    }

    /**
     * Преобразува данните в BufferedImage.
     *
     * @return BufferedImage обект
     */
    public BufferedImage getImage() {
        BufferedImage output = new BufferedImage(getWidth(), getHeight(), getImageType());
        output.getRaster().setPixels(0, 0, getWidth(), getHeight(), getData());
        return output;
    }

    /**
     * Проверява дали две изображения са от един формат.
     *
     * @param other другото изображение
     * @return true ако форматите съвпадат
     */
    public boolean sameFormat(Image other) {
        return getClass().equals(other.getClass());
    }
}
