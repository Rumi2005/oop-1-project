package app;
/**
 * Начален клас на приложението.
 * Стартира командния интерфейс.
 */
public class Application {
    /**
     * Главна входна точка на програмата.
     *
     * @param args аргументи от командния ред
     */
    public static void main(String[] args) {
        AppCLI app = new AppCLI();
        app.start();
    }
}
