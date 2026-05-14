package commands.utils.loader;

import java.io.IOException;
import java.io.InputStream;

public interface ImageLoader {
    int[] load(InputStream is, int width, int height, int maxVal) throws IOException;
}
