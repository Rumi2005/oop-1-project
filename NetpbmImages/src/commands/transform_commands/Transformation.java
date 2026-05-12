package commands.transform_commands;

import models.images.Image;

public interface Transformation {
    Image transform(Image image);
}
