package models;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.TreeMap;

public abstract class Image {
    protected int imageType;
    protected int width;
    protected int height;
    protected int maxVal;
    protected int[] data;

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

    public Map<Integer, Integer> getMap(){
        Map<Integer, Integer> output = new TreeMap<>();
        for(int x = 0; x < getMaxVal(); x++){
            int y = 0;
            for(int i = 0; i < getData().length; i++)
                if(getData()[i] == x)
                    y++;
            output.put(x,y);
        }
        return output;
    }
}
