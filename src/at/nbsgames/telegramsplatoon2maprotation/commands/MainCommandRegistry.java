package at.nbsgames.telegramsplatoon2maprotation.commands;

import java.util.HashMap;

public class MainCommandRegistry {

    private static HashMap<String, Command> subCommands = new HashMap<>();

    public static Object runCommand(String command, SenderLocation commandReceiver, Object... additionalObjects){
        String commandHandle[] = command.split(" ", 2);
        if(subCommands.containsKey(commandHandle[0])){
            if(subCommands.get(commandHandle[0]).isRestrictedTo() == null || subCommands.get(commandHandle[0]).isRestrictedTo() == commandReceiver)
                return subCommands.get(commandHandle[0]).preHandle(commandHandle.length > 1 ? commandHandle[1] : "", commandReceiver, additionalObjects);
            else
                return null;

        }
        else{
            return null;
        }
    }

    public static void registerCommand(String commandName, Command command){
        subCommands.put(commandName.toLowerCase(), command);
    }

}
