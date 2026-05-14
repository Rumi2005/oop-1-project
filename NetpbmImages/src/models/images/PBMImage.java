package models.images;

import java.awt.image.BufferedImage;

public class PBMImage extends Image {
    public PBMImage(){
        setImageType(BufferedImage.TYPE_BYTE_BINARY);
    }

    @Override
    public BufferedImage getImage() {
        int[] original = getData().clone();
        for(int i = 0; i < getData().length; i++)
            this.data[i] = (this.data[i] + 1) % 2;
        BufferedImage image = super.getImage();
        this.setData(original);
        return image;
    }

    @Override
    public Image createEmpty() {
        return new PBMImage();
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
        return true;
    }
}
