package commands.transform_commands;

import commands.Command;
import models.images.Image;
import models.session.Session;
import models.session.SessionManager;

import java.util.List;
/**
 * Прилага негативен ефект върху изображението.
 * Обръща цветовите стойности.
 */
public class NegativeCommand implements Command, Transformation {
    /**
     * Прилага негативен филтър.
     */
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

    /**
     * Създава негатив.
     * @param image изображението, което ще бъде направено в негатив
     */
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
