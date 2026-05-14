package models.images;

import java.awt.image.BufferedImage;

public abstract class Image {
    protected int imageType;
    protected int width;
    protected int height;
    protected int maxVal;
    protected int[] data;

    public abstract Image createEmpty();

    public abstract int getChannels();

    public abstract boolean isColored();

    public abstract boolean isMonochrome();

    public int getImageType() {
        return imageType;
    }

    public void setImageType(int imageType) {
        this.imageType = imageType;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int[] getData() {
        return data;
    }

    public void setData(int[] data) {
        this.data = data;
    }

    public int getMaxVal() {
        return maxVal;
    }

    public void setMaxVal(int maxVal) {
        this.maxVal = maxVal;
    }

    public BufferedImage getImage() {
        BufferedImage output = new BufferedImage(getWidth(), getHeight(), getImageType());
        output.getRaster().setPixels(0, 0, getWidth(), getHeight(), getData());
        return output;
    }

    public boolean sameFormat(Image other) {
        return getClass().equals(other.getClass());
    }
}
