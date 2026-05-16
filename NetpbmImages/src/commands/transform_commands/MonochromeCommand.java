package commands.transform_commands;

import commands.Command;
import models.images.Image;
import models.session.Session;
import models.session.SessionManager;

import java.util.List;
/**
 * Преобразува изображението в черно-бял режим.
 * използва само два цветови тона.
 */
public class MonochromeCommand implements Command, Transformation {
    /**
     * извършва преобразуването.
     */
    @Override
    public String execute() {
        Session session = SessionManager.getCurrentSession();
        if (session == null)
            return "No active session.";
        List<Image> images = session.getImages();
        for (int i = 0; i < images.size(); i++) {
            Image original = images.get(i);
            Image transformed = transform(original);
            if (transformed != original)
                images.set(i, transformed);
        }
        session.addTransformation("monochrome");
        return "";
    }

    /**
     * Преобразува изображението в черно-бяло.
     * @param image е подаденото изображение.
     */
    @Override
    public Image transform(Image image) {
        if (image.isMonochrome())
            return image;
        int[] data = image.getData();
        Image mono = image.createEmpty();
        mono.setWidth(image.getWidth());
        mono.setHeight(image.getHeight());
        mono.setMaxVal(image.getMaxVal());

        int[] newData = new int[data.length];
        if (!image.isColored())
            for (int i = 0; i < data.length; i++)
                newData[i] = (data[i] < 128) ? 0 : 255;
        else
            for (int i = 0; i < data.length; i += 3) {
                int r = data[i];
                int g = data[i + 1];
                int b = data[i + 2];
                int gray = (int) (0.3 * r + 0.59 * g + 0.11 * b);
                int monoPixel = (gray < 128) ? 0 : 255;
                newData[i] = monoPixel;
                newData[i + 1] = monoPixel;
                newData[i + 2] = monoPixel;
            }
        mono.setData(newData);
        return mono;
    }
}
