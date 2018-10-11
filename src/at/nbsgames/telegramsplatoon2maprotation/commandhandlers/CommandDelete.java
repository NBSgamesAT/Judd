package at.nbsgames.telegramsplatoon2maprotation.commandhandlers;

import at.nbsgames.telegramsplatoon2maprotation.commands.Command;
import at.nbsgames.telegramsplatoon2maprotation.commands.SenderLocation;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;

public class CommandDelete extends Command {

    @Override
    public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObject) {
        MessageReceivedEvent e = (MessageReceivedEvent) additionalObject[0];
        if(e.getAuthor().getPermissionsForGuild(e.getGuild()).contains(Permissions.ADMINISTRATOR) && e.getAuthor() == e.getClient().getApplicationOwner()){
                //this.sqlConnection.sendSql("UPDATE `Clans` SET `TeamName` = ? WHERE `DiscordServer` = ? ;", name, e.getGuild().getLongID());
                //e.getChannel().sendMessage("The team were removed " + name + ".");
                //return true;

        }
        else {
            return "I can't let you do that! Sorry. Ask the server owner or someone else with the administrator permission to do that!";
        }
        return null;
    }
}
