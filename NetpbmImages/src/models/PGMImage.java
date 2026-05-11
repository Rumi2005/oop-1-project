package models;

import java.awt.image.BufferedImage;

public class PGMImage extends Image{
    public PGMImage(){
        this.imageType = BufferedImage.TYPE_BYTE_GRAY;
    }
}
