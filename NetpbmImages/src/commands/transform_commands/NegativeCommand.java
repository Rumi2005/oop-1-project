package commands.transform_commands;

import commands.Command;
import models.images.Image;
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
        images.replaceAll(this::transform);
        session.addTransformation("monochrome");
        return "";
    }

    @Override
    public Image transform(Image image) {
        int[] originalData = image.getData();
        int[] newData = new int[originalData.length];
        int max = image.getMaxVal();
        for(int i = 0; i < originalData.length; i++)
            newData[i] = max - originalData[i];
        Image negative = image.createEmpty();
        negative.setWidth(image.getWidth());
        negative.setHeight(image.getHeight());
        negative.setMaxVal(image.getMaxVal());
        negative.setData(newData);
        return negative;
    }
}
