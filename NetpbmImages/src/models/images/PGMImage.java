package models.images;

import java.awt.image.BufferedImage;

public class PGMImage extends Image {
    public PGMImage(){
        this.imageType = BufferedImage.TYPE_BYTE_GRAY;
    }

    @Override
    public Image createEmpty() {
        return new PGMImage();
    }

    @Override
    public int getChannels() {
        return 1;
    }

    @Override
    public boolean isColored() {
        return false;
    }

    @Override
    public boolean isMonochrome() {
        for (int value : getData())
            if (value != 0 && value != getMaxVal())
                return false;
        return true;
    }
}
