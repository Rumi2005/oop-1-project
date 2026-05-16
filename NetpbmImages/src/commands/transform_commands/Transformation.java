package commands.transform_commands;

import models.images.Image;
/**
 * Представя трансформация върху изображение.
 * Всички трансформации трябва да имплементират този интерфейс.
 */
public interface Transformation {
    /**
     * Прилага трансформация върху изображение.
     *
     * @param image изображението за обработка
     * @return новото трансформирано изображение
     */
    Image transform(Image image);
}
