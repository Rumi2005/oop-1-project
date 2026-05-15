package commands.transform_commands;

import commands.Command;
import models.images.Image;
import models.session.Session;
import models.session.SessionManager;

import java.util.List;

public class RotateCommand implements Command, Transformation {
    private boolean rotateRight;
    public RotateCommand(String direction) {
        if(direction == null)
            throw new IllegalArgumentException("Set direction to 'left' of 'right'");
        direction = direction.toLowerCase();
        if(direction.equals("right"))
            rotateRight = true;
        else if(direction.equals("left"))
            rotateRight = false;
        else
            throw new IllegalArgumentException("Set direction to 'left' of 'right'");
    }

    @Override
    public String execute() {
        Session session = SessionManager.getCurrentSession();
        if(session == null)
            return "No active session.";
        List<Image> images = session.getImages();
        images.replaceAll(this::transform);
        session.addTransformation(rotateRight ? "rotate right" : "rotate left");
        return "";
    }

    @Override
    public Image transform(Image image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int channels = image.getChannels();
        int[] oldData = image.getData();
        int[] newData = new int[oldData.length];
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                int oldIndex = (y * width + x) * channels;
                int newX;
                int newY;
                if (rotateRight) {
                    newX = height - 1 - y;
                    newY = x;
                } else {
                    newX = y;
                    newY = width - 1 - x;
                }
                int newIndex = (newY*height + newX)*channels;
                System.arraycopy(oldData, oldIndex, newData, newIndex, channels);
            }
        Image rotated = image.createEmpty();
        rotated.setWidth(height);
        rotated.setHeight(width);
        rotated.setMaxVal(image.getMaxVal());
        rotated.setData(newData);
        return rotated;
    }
}
