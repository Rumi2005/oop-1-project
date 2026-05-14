package models.images;

import java.awt.image.BufferedImage;

public class PPMImage extends Image {
    public PPMImage(){
        this.imageType = BufferedImage.TYPE_INT_RGB;
    }

    @Override
    public Image createEmpty() {
        return new PPMImage();
    }

    @Override
    public int getChannels() {
        return 3;
    }

    @Override
    public boolean isColored() {
        return true;
    }

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
