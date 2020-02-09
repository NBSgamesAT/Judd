package at.nbsgames.judd.commandhandlers;

import at.nbsgames.judd.ListenerDiscord;
import at.nbsgames.judd.commands.Command;
import at.nbsgames.judd.commands.SenderLocation;
import at.nbsgames.judd.splatoonink.BattleSlotV2;
import at.nbsgames.judd.splatoonink.WebsiteChecker;
import at.nbsgames.judd.telegram.objects.TMessage;
import discord4j.core.event.domain.message.MessageCreateEvent;

public class CommandTurf extends Command {

    @Override
    public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObjects) {
        final BattleSlotV2 slot;
        String message = "";
        int num;
        try{
            if(command == null){
                num = 0;
                message = "Getting information about the current turf rotation";
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
                message = "Getting information about the giving turf rotation (" + num + ").";
            }
        }
        catch(NumberFormatException e){
            message = "Giving value is not a number; Using 0 (currently active)!";
            num = 0;
        }
        slot = WebsiteChecker.getTurf(num);
        final String finalMessage = message;
        if(commandReceiver == SenderLocation.TELEGRAM){
            message = message + "\nMode: " + slot.getWarKind().getFriendlyName() + "\n" +
                    "Maps:\n" + BattleSlotV2.createTrimmedMapString(slot.getMaps(), "\n") + "\n\n" +
                    (slot.hasStarted() ? "Ends in: " + slot.getTime().formatTime() : "Starts in: " + slot.getTime().formatTime());
            TMessage tMessage = (TMessage) additionalObjects[0];
            tMessage.getChat().sendMessageText(message);
            return true;
        }
        else{
            MessageCreateEvent e = (MessageCreateEvent) additionalObjects[0];
            e.getMessage().getChannel().block().createMessage(spec -> {
                ListenerDiscord.createEmbedObjectForBattleSlot(spec, slot);
                spec.setContent(finalMessage);
            }).block();
            return true;
        }
    }
}
