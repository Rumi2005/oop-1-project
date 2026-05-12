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
}
