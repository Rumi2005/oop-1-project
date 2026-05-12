package commands.transform_commands;

import commands.Command;
import models.images.Image;
import models.images.PBMImage;
import models.images.PGMImage;
import models.images.PPMImage;
import models.session.Session;
import models.session.SessionManager;

import java.util.List;

public class NegativeCommand implements Command, Transformation {
    @Override
    public String execute() {
        Session session = SessionManager.getCurrentSession();
        if(session == null) {
            return "No active session.";
        }
        List<Image> images = session.getImages();
        int modified = 0;
        for(int i = 0; i < images.size(); i++) {
            Image original = images.get(i);
            Image transformed = transform(original);
            images.set(i, transformed);
            modified++;
        }
        return modified + " image(s) converted.";
    }

    @Override
    public Image transform(Image image) {
        int[] originalData = image.getData();
        int[] newData = new int[originalData.length];
        int max = image.getMaxVal();
        for(int i = 0; i < originalData.length; i++)
            newData[i] = max - originalData[i];
        Image negative;
        if(image instanceof PBMImage)
            negative = new PBMImage();
        else if(image instanceof PGMImage)
            negative = new PGMImage();
        else if(image instanceof PPMImage)
            negative = new PPMImage();
        else
            return image;

        negative.setWidth(image.getWidth());
        negative.setHeight(image.getHeight());
        negative.setMaxVal(image.getMaxVal());
        negative.setData(newData);
        return negative;
    }
}
