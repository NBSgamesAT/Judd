package at.nbsgames.judd.commandhandlers;

import at.nbsgames.judd.ListenerDiscord;
import at.nbsgames.judd.commands.Command;
import at.nbsgames.judd.commands.SenderLocation;
import at.nbsgames.judd.splatoonink.GetSalmonRun;
import at.nbsgames.judd.splatoonink.SalmonRunSlot;
import at.nbsgames.judd.telegram.objects.TMessage;
import discord4j.core.event.domain.message.MessageCreateEvent;

public class CommandSalmon extends Command {

    @Override
    public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObject) {
        final SalmonRunSlot slot = GetSalmonRun.getNextSalmonRunSlot();
        if(commandReceiver == SenderLocation.TELEGRAM){
            String reply;
            if(slot.hasStarted()){
                reply = "Current salmon run shift ends in: " + slot.getTime().formatTime() + "\n\n";
            }
            else{
                reply = "Salmon run is not open right now. Next shift starts in: " + slot.getTime().formatTime() + "\n\n";
            }
            reply = reply + "Map: " + slot.getMap() + "\n\n" +
                    "Weapons:\n" +
                    slot.getWeaponA() + "\n" +
                    slot.getWeaponB() + "\n" +
                    slot.getWeaponC() + "\n" +
                    slot.getWeaponD();
            TMessage message = (TMessage) additionalObject[0];
            message.getChat().sendMessageImage(reply, slot.getMapImageDownloadLink(), null);
            return true;
        }
        else{
            MessageCreateEvent e = (MessageCreateEvent) additionalObject[0];
            e.getMessage().getChannel().block().createMessage(spec -> {
                ListenerDiscord.createEmbededObjectForSalmonrunSlot(spec, slot);
            }).block();
            return true;
        }
    }

}
