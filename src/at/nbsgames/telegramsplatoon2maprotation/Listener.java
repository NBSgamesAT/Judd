package at.nbsgames.telegramsplatoon2maprotation;

import at.nbsgames.telegramsplatoon2maprotation.commands.MainCommandRegistry;
import at.nbsgames.telegramsplatoon2maprotation.commands.SenderLocation;
import at.nbsgames.telegramsplatoon2maprotation.splatoonink.BattleSlotV2;
import at.nbsgames.telegramsplatoon2maprotation.splatoonink.GetSalmonRun;
import at.nbsgames.telegramsplatoon2maprotation.splatoonink.SalmonRunSlot;
import at.nbsgames.telegramsplatoon2maprotation.splatoonink.WebsiteChecker;
import at.nbsgames.telegramsplatoon2maprotation.telegram.TEventHook;
import at.nbsgames.telegramsplatoon2maprotation.telegram.enums.TChatType;
import at.nbsgames.telegramsplatoon2maprotation.telegram.objects.TMessage;
import at.nbsgames.telegramsplatoon2maprotation.telegram.replykeyboard.TReplyKeyboardButton;
import at.nbsgames.telegramsplatoon2maprotation.telegram.replykeyboard.TReplyKeyboardLine;
import at.nbsgames.telegramsplatoon2maprotation.telegram.replykeyboard.TReplyKeyboardMarkup;

public class Listener implements TEventHook{

    @Override
    public void messageReceived(TMessage message) {

        /*
        try{
            String text = message.getText();
            String[] splittedCommand = text.split(" ");
            System.out.println(splittedCommand[0]);
            if(message.getText() != null && (message.matchCommand("/turf", splittedCommand[0]) || message.matchCommand("/t", splittedCommand[0]))){

                BattleSlotV2 slot;
                try{
                    if(Integer.parseInt(splittedCommand[1]) > 11){
                        message.getChat().sendMessageText("Number given too big.");
                        slot = WebsiteChecker.getTurf(0);
                    }
                    else{
                        slot = WebsiteChecker.getTurf(Integer.parseInt(splittedCommand[1]));
                    }
                }
                catch(NumberFormatException | ArrayIndexOutOfBoundsException e){
                    slot = WebsiteChecker.getTurf(0);
                }
                if(slot == null){
                    message.getChat().sendMessageText("Could not find a rotation set for that time.");
                    return;
                }
                String reply = "Maps and Mode in Regular:\nMode: ";
                reply = reply + slot.getWarKind().getFriendlyName();
                reply = reply + "\nMaps:\n" + BattleSlotV2.createTrimmedMapString(slot.getMaps(), "\n");
                reply = reply + (slot.hasStarted() ? "\n\nEnds in: " : "\n\nStarts in: ") + slot.getTime().formatTime();
                message.getChat().sendMessageText(reply);
                System.out.println("User " + message.getWriter().getUserId() + " requested Regular matches. Handled with out problems.");
            }
            else if(message.getText() != null && (message.matchCommand("/ranked", splittedCommand[0]) || message.matchCommand("/rank", splittedCommand[0]) || message.matchCommand("/r", splittedCommand[0]))){

                BattleSlotV2 slot;
                try{
                    if(Integer.parseInt(splittedCommand[1]) > 11){
                        message.getChat().sendMessageText("Number given too big.");
                        slot = WebsiteChecker.getRanked(0);
                    }
                    else{
                        slot = WebsiteChecker.getRanked(Integer.parseInt(splittedCommand[1]));
                    }
                }
                catch(NumberFormatException | ArrayIndexOutOfBoundsException e){
                    slot = WebsiteChecker.getRanked(0);
                }
                if(slot == null){
                    message.getChat().sendMessageText("Could not find a rotation set for that time.");
                    return;
                }
                String reply = "Maps and Mode in Ranked:\nMode: ";
                reply = reply + slot.getWarKind().getFriendlyName();
                reply = reply + "\nMaps:\n" + BattleSlotV2.createTrimmedMapString(slot.getMaps(), "\n");
                reply = reply + (slot.hasStarted() ? "\n\nEnds in: " : "\n\nStarts in: ") + slot.getTime().formatTime();
                message.getChat().sendMessageText(reply);
                System.out.println("User " + message.getWriter().getUserId() + " requested Ranked matches. Handled with out problems.");
            }
            else if(message.getText() != null && (message.matchCommand("/league", splittedCommand[0]) || message.matchCommand("/l", splittedCommand[0]))){

                BattleSlotV2 slot;
                try{
                    if(Integer.parseInt(splittedCommand[1]) > 11){
                        message.getChat().sendMessageText("Number given too big.");
                        slot = WebsiteChecker.getLeague(0);
                    }
                    else{
                        slot = WebsiteChecker.getLeague(Integer.parseInt(splittedCommand[1]));
                    }
                }
                catch(NumberFormatException | ArrayIndexOutOfBoundsException e){
                    slot = WebsiteChecker.getLeague(0);
                }
                if(slot == null){
                    message.getChat().sendMessageText("Could not find a rotation set for that time.");
                    return;
                }
                String reply = "Maps and Mode in League:\nMode: ";
                reply = reply + slot.getWarKind().getFriendlyName();
                reply = reply + "\nMaps:\n" + BattleSlotV2.createTrimmedMapString(slot.getMaps(), "\n");
                reply = reply + (slot.hasStarted() ? "\n\nEnds in: " : "\n\nStarts in: ") + slot.getTime().formatTime();
                message.getChat().sendMessageText(reply);
                System.out.println("User " + message.getWriter().getUserId() + " requested League matches. Handled with out problems.");
            }
            else if((message.getText() != null && (message.matchCommand("/salmonrun", splittedCommand[0]) || message.matchCommand("/salmon", splittedCommand[0]) || message.matchCommand("/s", splittedCommand[0])))){

                SalmonRunSlot slot = GetSalmonRun.getNextSalmonRunSlot();
                String reply;
                if(slot.hasStarted()){
                    reply = "Current salmon run shift ends in: " + slot.getTime().formatTime() + "\n\n";
                }
                else{
                    reply = "Salmon run is not open right now. Next shift starts in: " + slot.getTime().formatTime() + "\n\n";
                }
                reply = reply + "Map: " + slot.getMap() + "\n\n";
                reply = reply + "Weapons:\n";
                reply = reply + slot.getWeaponA() + "\n";
                reply = reply + slot.getWeaponB() + "\n";
                reply = reply + slot.getWeaponC() + "\n";
                reply = reply + slot.getWeaponD();
                message.getChat().sendMessageImage(reply, slot.getMapImageDownloadLink(), false);
                System.out.println("User " + message.getWriter().getUserId() + " requested salmonrun rotation. Handled with out problems.");
            }
            else if(message.getText() != null && message.matchCommand("/start", splittedCommand[0])){
                message.getChat().sendMessageReplyKeyboardMarkup("This bot is able to tell you that current and coming map rotation of Splatoon2\n\n" +
                                "/turf offset*: Get the rotation for regular battles\n" +
                                "/eanked offset*: Get the rotation for ranked battles\n" +
                                "/league offset*: Get the rotation for league battles\n" +
                                "/salmonrun: Get the current/next salmon run shift\n\n" +
                                "/author: Get information about the author\n" +
                                "/version: Get Information about the version\n" +
                                "offset*: Optional. Jumps said rotation further. Usefull if you want to know the next rotation (1) or the rotation after that (2).\n\n" +
                                "It is possible to short the commands, eg: /t (Turf), /rank (Ranked), /r (Ranked), /l (League)",
                        new TReplyKeyboardMarkup(false, true, false,
                                new TReplyKeyboardLine(
                                        new TReplyKeyboardButton("/turf", false, false),
                                        new TReplyKeyboardButton("/turf 1", false, false)),
                                new TReplyKeyboardLine(
                                        new TReplyKeyboardButton("/ranked", false, false),
                                        new TReplyKeyboardButton("/ranked 1", false, false)),
                                new TReplyKeyboardLine(
                                        new TReplyKeyboardButton("/league", false, false),
                                        new TReplyKeyboardButton("/league 1", false, false)),
                                new TReplyKeyboardLine(
                                        new TReplyKeyboardButton("/salmonrun", false, false)
                                )
                        )
                );
                System.out.println("User " + message.getWriter().getUserId() + " typed /start. Handled without any problems.");
            }
            else if(message.getText() != null && message.matchCommand("/stop", splittedCommand[0])){
                message.getChat().sendMessageRemoveReplyKeyboard("There's no need to stop me. When you stop writing to me I will do that aswell. But I removed your fast keyboard. Type /start to get it again.", false);
                System.out.println("User " + message.getWriter().getUserId() + " typed /stop. Handled without any problems.");
            }
            else if(message.getText() != null && message.matchCommand("/help", splittedCommand[0])){
                message.getChat().sendMessageText("This bot is able to tell you that current and coming map rotation of Splatoon2\n\n" +
                        "/turf offset*: Get the rotation for regular battles\n" +
                        "/ranked offset*: Get the rotation for ranked battles\n" +
                        "/league offset*: Get the rotation for league battles\n" +
                        "/author: Get information about the author\n" +
                        "/version: Get Information about the version\n" +
                        "offset*: Jumps said rotation further. Usefull if you want to know the next rotation (1) or the rotation after that (2).\n\n" +
                        "It is possible to short the commands, eg: /t (Turf), /rank (Ranked), /r (Ranked), /l (League)");
                System.out.println("User " + message.getWriter().getUserId() + " typed /help. Handled without any problems.");
            }
            else if(message.getText() != null && message.matchCommand("/author", splittedCommand[0])){
                message.getChat().sendMessageText("This bot were developed my NBSgames.at aka. NBSink(on squidboards.com), NBScode (on Githhub). He might put some code of me on Github.\n\n" +
                        "Type /version to get my current version!");
                System.out.println("User " + message.getWriter().getUserId() + " typed /author. Handled without any problems.");
            }
            else if(message.getText() != null && message.matchCommand("/version", splittedCommand[0])){
                message.getChat().sendMessageText("Versions:\n\n" +
                        " * Bot: 1.5 June 27th, 2018\n" +
                        " * Telegram Library: 3.0unfinishedV2 30th June, 2018\n" +
                        " * SplatoonInk Library: 2.1.2 June 15th, 2018\n" +
                        " * File Version: 3.0 30th June, 2018 \n\n" +
                        "Libraries written by NBSgames.at for this bot. He is going to release them as Libraries when they are finished. Bot written in Java.");
                System.out.println("User " + message.getWriter().getUserId() + " typed /version. Handled without any problems.");
            }
            /*
            else if(MessageType.TEXT == type && splittedCommand[0].equalsIgnoreCase("/test")){
                SendMessage.sendMessageInlineKeyboardMarkup(telegramChecker, chatId, messageId, "Here is what you want:", new InlineKeyboardMarkup(
                        new InlineKeyboardLineMarkup(
                                new InlineKeyboardButtonMarkup("CallBackData", "dataTest", InlineKeyboardButtonKind.SEND_CALLBACK_QUERY),
                                new InlineKeyboardButtonMarkup("OpenURL", "https://nbsgames.at/NewSite/", InlineKeyboardButtonKind.OPEN_URL)
                        )
                ));

                System.out.println("User " + userId + " typed /text. Handled without any problems.");
            }
            else if(message.getText() != null && message.getChat().getChatType() == TChatType.PRIVATE){
                message.getChat().sendMessageText("Could not regonize your message. Please type /help to get commands.");
                System.out.println("User " + message.getWriter().getUserId() + " typed unregonized message. Handled without any problems.");
            }
            else if(message.getText() != null && message.getChat().getChatType() == TChatType.PRIVATE){
                message.getChat().sendMessageText("Please type a message. I can't do anything without a message of the type Text. Use /help to get a list of commands.");
                System.out.println("User " + message.getWriter().getUserId() + " typed no message Handled without any problems.");
            }
        }
        catch(Exception e){
            System.out.println("User " + message.getWriter().getUserId() + " typed \"" + message + "\".");
            e.printStackTrace();
            message.getChat().sendMessageText("I'm having technical difficulties right now... So I couldn't handle your requests... I printed the error to my log so my developer should see it someday.");
        }
        */
        if(message.getText() != null && message.getText().startsWith("/")) {
            String[] commandHandle = message.getText().split(" ", 2);
            String cutThings = commandHandle[0].replace("@" + message.getBotUsername(), "");
            commandHandle[0] = cutThings.substring(1).toLowerCase();
            String command = commandHandle.length > 1 ? commandHandle[0] + " " + commandHandle[1] : commandHandle[0];
            System.out.println(command);
            Object result = MainCommandRegistry.runCommand(command, SenderLocation.TELEGRAM);
            if(result == null){
                message.getChat().sendMessageText("Command not found");
            }
            else if(result instanceof String){
                message.getChat().sendMessageText((String) result);
            }
        }
        else{
            message.getChat().sendMessageText("Cannot handle message. Must begin with a / and must be completely text");
        }
    }
}
