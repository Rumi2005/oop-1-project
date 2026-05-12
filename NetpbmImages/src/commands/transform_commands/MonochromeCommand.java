package commands.transform_commands;

import commands.Command;
import models.images.Image;
import models.images.PBMImage;
import models.images.PGMImage;
import models.images.PPMImage;

public class MonochromeCommand implements Command, Transformation {
    @Override
    public String execute() {
        return "";
    }

    @Override
    public Image transform(Image image) {
        if(image instanceof PBMImage)
            return image;
        int[] data = image.getData();
        boolean isMonochrome = true;

        if(image instanceof PPMImage){
            for(int i = 0; i < data.length; i++){
                int r = data[i];
                int g = data[i+1];
                int b = data[i+2];

                boolean isWhite = r == 255 & g == 255 && b == 255;
                boolean isBlack = r == 0 && g == 0 && b == 0;
                if(!isWhite && !isBlack){
                    isMonochrome = false;
                    break;
                }
            }
        }else {
            for (int value : data)
                if (value != 0 && value != 255) {
                    isMonochrome = false;
                    break;
                }
        }
        if(isMonochrome)
            return image;

        if(image instanceof PGMImage){
            PGMImage mono = new PGMImage();
            mono.setWidth(image.getWidth());
            mono.setHeight(image.getHeight());
            mono.setMaxVal(image.getMaxVal());
            int[] newData = new int[data.length];
            for(int i = 0; i< data.length; i++)
                newData[i] = (data[i] < 128) ? 0 : 255;
            mono.setData(newData);
            return mono;
        }

        if (image instanceof PPMImage) {
            PPMImage mono = new PPMImage();
            mono.setWidth(image.getWidth());
            mono.setHeight(image.getHeight());
            mono.setMaxVal(image.getMaxVal());
            int[] newData = new int[data.length];
            for (int i = 0; i < data.length; i += 3) {
                int r = data[i];
                int g = data[i+1];
                int b = data[i+2];
                int gray = (int) (0.3*r + 0.59*g + 0.11*b);
                int monoPixel = (gray < 128) ? 0 : 255;
                newData[i] = monoPixel;
                newData[i + 1] = monoPixel;
                newData[i + 2] = monoPixel;
            }
            mono.setData(newData);
            return mono;
        }
        return image;
    }
}
