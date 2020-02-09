package at.nbsgames.judd.commandhandlers;

import at.nbsgames.judd.Main;
import at.nbsgames.judd.commands.Command;
import at.nbsgames.judd.commands.SenderLocation;
import at.nbsgames.judd.database.SqlResult;
import discord4j.core.event.domain.message.MessageCreateEvent;

import java.sql.SQLException;

public class CommandWeapons extends Command {

    @Override
    public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObject) {
        try {
            SqlResult result = Main.getDatabaseHandler().getData("SELECT MainName FROM MainWeapons ORDER BY MainName ASC;");
            StringBuilder builder = new StringBuilder();
            boolean isFirst = true;
            while(result.getResultSet().next()){
                if(isFirst){
                    isFirst = false;
                }
                else{
                    builder.append("\n");
                }
                builder.append(result.getResultSet().getString("MainName"));
                if(builder.length() > 1800){
                    if(commandReceiver == SenderLocation.DISCORD){
                        MessageCreateEvent e = (MessageCreateEvent) additionalObject[0];
                        e.getMessage().getChannel().block().createMessage(builder.toString()).block();
                        isFirst = true;
                        builder = new StringBuilder();
                    }
                }
            }
            return builder.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            return e;
        }
    }
}
