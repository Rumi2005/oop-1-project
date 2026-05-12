package models.images;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PPMImage extends Image {
    public PPMImage(){
        this.imageType = BufferedImage.TYPE_INT_RGB;
    }

    @Override
    public Map<Integer, Integer> getMap() {
        Map<Integer, Integer> output = new TreeMap<>();
        int[] grayScale = new int[data.length/3];
        for(int i = 0; i < grayScale.length; i++)
            grayScale[i] = (int)(data[i*3] * 0.3 + data[i*3+1] * 0.59 + data[i*3+2] * 0.11);
        for(int x = 0; x < getMaxVal(); x++){
            int y = 0;
            for (int j : grayScale)
                if (j == x)
                    y++;
            output.put(x,y);
        }
        return output;
    }

    public List<Map<Integer, Integer>> getRGBMap(){
        List<Map<Integer, Integer>> output = new ArrayList<>();
        Map<Integer, Integer> red = new TreeMap<>();
        Map<Integer, Integer> blue = new TreeMap<>();
        Map<Integer, Integer> green = new TreeMap<>();
        for(int x = 0; x < getMaxVal(); x++){
            int r = 0;
            int g = 0;
            int b = 0;
            for(int i = 0; i < data.length; i++){
                if(data[i] == x){
                    if(i%3 == 0)
                        r++;
                    else if(i%3 == 1)
                        b++;
                    else if(i%3 == 2)
                        g++;
                }
            }
            red.put(x,r);
            green.put(x,g);
            blue.put(x,b);
        }
        output.add(red);
        output.add(green);
        output.add(blue);
        return output;
    }
}
