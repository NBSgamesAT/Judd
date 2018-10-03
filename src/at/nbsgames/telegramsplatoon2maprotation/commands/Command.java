package at.nbsgames.telegramsplatoon2maprotation.commands;

import java.util.HashMap;

public abstract class Command {


    private HashMap<String, Command> subCommands = new HashMap<>();
    private SenderLocation restrictedTo = null;

    public Command(){

    }
    public Command(SenderLocation restrictedTo){
        this.restrictedTo = restrictedTo;
    }

    public Object preHandle(String command, SenderLocation commandReceiver, ){
        System.out.println(command);
        String commandHandle[] = command.split(" ", 2);
        if(subCommands.containsKey(commandHandle[0])){
            if(subCommands.get(commandHandle[0]).isRestrictedTo() == null || subCommands.get(commandHandle[0]).isRestrictedTo() == commandReceiver)
                return subCommands.get(commandHandle[0]).preHandle(commandHandle.length > 1 ? commandHandle[1] : "", commandReceiver);
            else
                return this.handleComamnd(!command.trim().equals("") ? command : null, commandReceiver);
        }
        else{
            return this.handleComamnd(!command.trim().equals("") ? command : null, commandReceiver);
        }
    }

    public abstract Object handleComamnd(String command, SenderLocation commandReceiver);

    public void addSubCommand(String commandName, Command command){
        this.subCommands.put(commandName, command);
    }
    public SenderLocation isRestrictedTo(){
        return restrictedTo;
    }
}
