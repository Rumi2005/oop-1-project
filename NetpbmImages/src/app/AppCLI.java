package app;

import commands.Command;
import commands.app_specific_commands.*;
import commands.base_commands.*;
import commands.transform_commands.GrayscaleCommand;
import commands.transform_commands.MonochromeCommand;
import commands.transform_commands.NegativeCommand;
import commands.transform_commands.RotateCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

public class AppCLI {
    private final Scanner scanner = new Scanner(System.in);
    private final
    Map<String, Function<String[], Command>> commands = createCommands();

    public void start() {
        System.out.println("NetPBM Image Editor");
        System.out.println("Type 'help' " + "for commands.");
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty())
                continue;
            try {
                String[] parts = input.split("\\s+");
                String key = getCommandKey(parts);
                Function<String[], Command> factory = commands.get(key);
                if (factory == null) {
                    System.out.println("Unknown " + "command.");
                    continue;
                }
                Command command = factory.apply(parts);
                String result = command.execute();
                if (result != null && !result.isEmpty())
                    System.out.println(result);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private Map<String, Function<String[], Command>> createCommands() {
        Map<String, Function<String[], Command>> map = new HashMap<>();
        map.put("load", parts -> {requireLength(parts, 2, "load <path>");
            return new LoadCommand(parts[1]);});
        map.put("add", parts -> {requireLength(parts, 2, "add <path>");
            return new AddCommand(parts[1]);});
        map.put("close", parts -> new CloseCommand());
        map.put("exit", parts -> new ExitCommand());
        map.put("grayscale", parts -> new GrayscaleCommand());
        map.put("monochrome", parts -> new MonochromeCommand());
        map.put("negative", parts -> new NegativeCommand());
        map.put("rotate", parts -> {requireLength(parts, 2, "rotate <left|right>");
            return new RotateCommand(parts[1]);});
        map.put("undo", parts -> new UndoCommand());
        map.put("session info", parts -> new SessionInfoCommand());
        map.put("switch", parts -> {requireLength(parts, 2, "switch <id>");
            return new SwitchCommand(Integer.parseInt(parts[1]));});
        map.put("collage", parts -> {requireLength(parts, 5, "collage <horizontal|vertical> <image1> <image2> <outimage>");
                    return new CollageCommand(parts[1], parts[2], parts[3], parts[4]);});
        map.put("save", parts -> new SaveCommand());
        map.put("save as", parts -> {requireLength(parts, 2, "save as <newName>");
            return new SaveAsCommand(parts[1]);});
        map.put("help", parts -> new HelpCommand());
        return map;
    }
    private String getCommandKey(String[] parts) {
        if (parts.length >= 2 && parts[0].equalsIgnoreCase("session") && parts[1].equalsIgnoreCase("info"))
            return "session info";
        return parts[0].toLowerCase();
    }

    private void requireLength(String[] parts, int minLength, String usage) {
        if (parts.length < minLength)
            throw new IllegalArgumentException(usage);
    }
}
