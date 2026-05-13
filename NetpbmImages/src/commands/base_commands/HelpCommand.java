package commands.base_commands;

import commands.Command;

public class HelpCommand implements Command {
    @Override
    public String execute() {
        StringBuilder help = new StringBuilder();
        help.append("The following commands are supported:").append("\n");
        help.append(formatCommand("load <image>", "Loads an image and starts a new session."));
        help.append(formatCommand("add <image>", "Adds an image to the current session."));
        help.append(formatCommand("close", "Closes the current session."));
        help.append(formatCommand("exit", "Closes the program."));
        help.append(formatCommand("grayscale", "Converts all colored images to grayscale."));
        help.append(formatCommand("monochrome", "Converts images to black and white."));
        help.append(formatCommand("negative", "Creates negatives of all images."));
        help.append(formatCommand("rotate <direction>", "Rotates all images 90 degrees."));
        help.append(formatCommand("undo", "Removes the last transformation."));
        help.append(formatCommand("session info", "Displays current session information."));
        help.append(formatCommand("switch <session>", "Switches to another session."));
        help.append(formatCommand("collage <direction> <image1> <image2> <outimage>", "Creates a collage from two images."));
        help.append(formatCommand("save", "Saves all images after applying transformations."));
        help.append(formatCommand("save as <newName>", "Saves the first image under a new name."));
        help.append(formatCommand("help", "Displays this help."));

        return help.toString();
    }

    private String formatCommand(String syntax, String description) {
        return String.format("%-45s %s%n", syntax, description);
    }
}
