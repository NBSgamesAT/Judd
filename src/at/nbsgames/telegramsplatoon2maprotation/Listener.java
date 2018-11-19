package at.nbsgames.telegramsplatoon2maprotation;

import at.nbsgames.telegramsplatoon2maprotation.commands.MainCommandRegistry;
import at.nbsgames.telegramsplatoon2maprotation.commands.SenderLocation;
import at.nbsgames.telegramsplatoon2maprotation.telegram.TEventHook;
import at.nbsgames.telegramsplatoon2maprotation.telegram.enums.TChatType;
import at.nbsgames.telegramsplatoon2maprotation.telegram.objects.TMessage;

public class Listener implements TEventHook{

    @Override
    public void messageReceived(TMessage message) {
        if(message.getText() != null && message.getText().startsWith("/")) {
            String[] commandHandle = message.getText().split(" ", 2);
            String cutThings = commandHandle[0].replace("@" + message.getBotUsername(), "");
            commandHandle[0] = cutThings.substring(1).toLowerCase();
            String command = commandHandle.length > 1 ? commandHandle[0] + " " + commandHandle[1] : commandHandle[0];
            Object result = MainCommandRegistry.runCommand(command, SenderLocation.TELEGRAM, message);
            if(result == null){
                if(message.getChat().getChatType() == TChatType.PRIVATE) message.getChat().sendMessageText("Command not found");
            }
            else if(result instanceof String){
                message.getChat().sendMessageText((String) result);
            }
            else if(result instanceof Boolean){
                if(!(Boolean) result){
                    if(message.getChat().getChatType() == TChatType.PRIVATE) message.getChat().sendMessageText("Command not found");
                }
            }
        }
        else{
            if(message.getChat().getChatType() == TChatType.PRIVATE) message.getChat().sendMessageText("Cannot handle message. Must begin with a / and must be completely text");
        }
    }
}
