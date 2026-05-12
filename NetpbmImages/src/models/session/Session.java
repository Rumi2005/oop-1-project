package models.session;

import models.images.Image;

import java.util.ArrayList;
import java.util.List;

public class Session {
    private static int nextId = 1;
    private int id;
    private List<Image> images;
    public Session(Image image){
        this.id = nextId++;
        images = new ArrayList<>();
        images.add(image);
    }

    public int getId() {
        return id;
    }

    public List<Image> getImages() {
        return images;
    }

    public void addImage(Image image){
        images.add(image);
    }
}
