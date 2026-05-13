package commands.transform_commands;

import commands.Command;
import models.images.Image;
import models.images.PBMImage;
import models.images.PGMImage;
import models.images.PPMImage;
import models.session.Session;
import models.session.SessionManager;

import java.util.List;

public class GrayscaleCommand implements Command,Transformation {
    @Override
    public String execute() {
        Session session = SessionManager.getCurrentSession();
        if (session == null) {
            return "No active session.";
        }
        List<Image> images = session.getImages();
        int modified = 0;
        for (int i = 0; i < images.size(); i++) {
            Image original = images.get(i);
            Image transformed = transform(original);
            if (transformed != original) {
                images.set(i, transformed);
                modified++;
            }
        }
        session.addTransformation("grayscale");
        return modified + " image(s) converted.";
    }

    @Override
    public Image transform(Image image) {
        if (image instanceof PBMImage || image instanceof PGMImage)
            return image;
        if (!(image instanceof PPMImage))
            return image;

        PPMImage original = (PPMImage) image;
        int[] data = original.getData();
        int[] grayData = new int[data.length];
        boolean isGray = true;

        for (int i = 0; i < data.length; i += 3) {
            int r = data[i];
            int g = data[i+1];
            int b = data[i+2];
            if (r != g || g != b)
                isGray = false;
            int gray = (int) (0.3*r + 0.59*g + 0.11*b);
            grayData[i] = gray;
            grayData[i+1] = gray;
            grayData[i+2] = gray;
        }

        if (isGray)
            return image;

        PPMImage grayscale = new PPMImage();
        grayscale.setWidth(original.getWidth());
        grayscale.setHeight(original.getHeight());
        grayscale.setMaxVal(original.getMaxVal());
        grayscale.setData(grayData);

        return grayscale;
    }
}