package at.nbsgames.telegramsplatoon2maprotation.commandhandlers;

import at.nbsgames.telegramsplatoon2maprotation.ListenerDiscord;
import at.nbsgames.telegramsplatoon2maprotation.commands.Command;
import at.nbsgames.telegramsplatoon2maprotation.commands.SenderLocation;
import at.nbsgames.telegramsplatoon2maprotation.splatoonink.BattleSlotV2;
import at.nbsgames.telegramsplatoon2maprotation.splatoonink.WebsiteChecker;
import at.nbsgames.telegramsplatoon2maprotation.telegram.objects.TMessage;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class CommandLeague extends Command {

    @Override
    public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObjects) {
        BattleSlotV2 slot;
        String message = "";
        try{
            int num;
            if(command == null){
                num = 0;
                message = "Getting information about the current league rotation";
            }
            else{
                num = Integer.parseInt(command);
            }
            if(num < 0){
                message = "Number entered is too low. Using 0 (currently active)!";
                num = 0;
            }
            else if(num > 11){
                message = "Number entered is too high. Using 11 (starting in 20 - 22 hours)!";
                num = 11;
            }
            else if(command != null){
                message = "Getting information about the giving league rotation (" + num + ").";
            }
            slot = WebsiteChecker.getLeague(num);
        }
        catch(NumberFormatException e){
            message = "Giving value is not a number; Using 0 (currently active)!";
            slot = WebsiteChecker.getTurf(0);
        }
        if(commandReceiver == SenderLocation.TELEGRAM){
            message = message + "\nMode: " + slot.getRankingKind().getName() + "\n" +
                    "Maps:\n" + BattleSlotV2.createTrimmedMapString(slot.getMaps(), "\n") + "\n\n" +
                    (slot.hasStarted() ? "Ends in: " + slot.getTime().formatTime() : "Starts in: " + slot.getTime().formatTime());
            TMessage tMessage = (TMessage) additionalObjects[0];
            tMessage.getChat().sendMessageText(message);
            return true;
        }
        else{
            MessageReceivedEvent e = (MessageReceivedEvent) additionalObjects[0];
            e.getChannel().sendMessage(message, ListenerDiscord.createEmbedObjectForBattleSlot(slot));
            return true;
        }
    }
}
