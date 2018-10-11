package at.nbsgames.telegramsplatoon2maprotation.commandhandlers;

import at.nbsgames.telegramsplatoon2maprotation.ListenerDiscord;
import at.nbsgames.telegramsplatoon2maprotation.commands.Command;
import at.nbsgames.telegramsplatoon2maprotation.commands.SenderLocation;
import at.nbsgames.telegramsplatoon2maprotation.splatoonink.GetSalmonRun;
import at.nbsgames.telegramsplatoon2maprotation.splatoonink.SalmonRunSlot;
import at.nbsgames.telegramsplatoon2maprotation.telegram.objects.TMessage;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class CommandSalmon extends Command {

    @Override
    public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObject) {
        SalmonRunSlot slot = GetSalmonRun.getNextSalmonRunSlot();
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
            MessageReceivedEvent e = (MessageReceivedEvent) additionalObject[0];
            e.getChannel().sendMessage(ListenerDiscord.createEmbededObjectForSalmonrunSlot(slot));
            return true;
        }
    }

}
