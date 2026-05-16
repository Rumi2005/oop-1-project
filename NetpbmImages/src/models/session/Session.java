package models.session;

import models.images.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Представя потребителска сесия.
 * Съхранява изображения и трансформации.
 */
public class Session {
    /**
     * идентификатор на следващата сесия.
     */
    private static int nextId = 1;
    /**
     * идентификаторът на сесията.
     */
    private int id;
    /**
     * Списък със заредените изображения.
     */
    private List<Image> images;
    /**
     * Списък с имена на изображенията
     */
    private final List<String> fileNames;
    /**
     * Списък с чакащи трансформации.
     */
    private final List<String> pendingTransformations;

    /**
     * Създава нова сесия.
     *
     * @param image първото изображение
     * @param fileName име на изображението
     */
    public Session(Image image, String fileName){
        this.id = nextId++;
        this.images = new ArrayList<>();
        this.fileNames = new ArrayList<>();
        this.pendingTransformations = new ArrayList<>();
        images.add(image);
        fileNames.add(fileName);
    }

    /**
     * Връща идентификатора на сесията.
     *
     * @return id на сесията
     */
    public int getId() {
        return id;
    }

    /**
     * Връща изображенията в сесията.
     *
     * @return списък с изображения
     */
    public List<Image> getImages() {
        return images;
    }

    /**
     * Връща имената на изображенията
     * @return списък с имена
     */
    public List<String> getFileNames() {
        return fileNames;
    }

    /**
     * Връща трансформациите в сесията.
     *
     * @return списък с трансформации
     */
    public List<String> getPendingTransformations() {
        return pendingTransformations;
    }

    /**
     * Добавя изображение към сесията.
     *
     * @param image изображението за добавяне
     * @param fileName име на изображението
     */
    public void addImage(Image image, String fileName) {
        images.add(image);
        fileNames.add(fileName);
    }

    /**
     * Добавя трансформация към сесията.
     *
     * @param transformation трансформацията
     */
    public void addTransformation(String transformation) {
        pendingTransformations.add(transformation);
    }

    /**
     * Премахва последната трансформация в списъка
     */
    public void undoTransformation() {
        if (!pendingTransformations.isEmpty())
            pendingTransformations.remove(pendingTransformations.size() - 1);
    }
}
