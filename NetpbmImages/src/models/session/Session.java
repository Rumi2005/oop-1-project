package models.session;

import models.images.Image;

import java.util.ArrayList;
import java.util.List;

public class Session {
    private static int nextId = 1;
    private int id;
    private List<Image> images;
    private final List<String> fileNames;
    private final List<String> pendingTransformations;

    public Session(Image image, String fileName){
        this.id = nextId++;
        this.images = new ArrayList<>();
        this.fileNames = new ArrayList<>();
        this.pendingTransformations = new ArrayList<>();
        images.add(image);
        fileNames.add(fileName);
    }

    public int getId() {
        return id;
    }

    public List<Image> getImages() {
        return images;
    }

    public List<String> getFileNames() {
        return fileNames;
    }

    public List<String> getPendingTransformations() {
        return pendingTransformations;
    }

    public void addImage(Image image, String fileName) {
        images.add(image);
        fileNames.add(fileName);
    }

    public void addTransformation(String transformation) {
        pendingTransformations.add(transformation);
    }

    public void undoTransformation() {
        if (!pendingTransformations.isEmpty())
            pendingTransformations.remove(pendingTransformations.size() - 1);
    }
}
