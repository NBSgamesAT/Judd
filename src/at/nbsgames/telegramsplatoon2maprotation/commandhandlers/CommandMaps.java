package at.nbsgames.telegramsplatoon2maprotation.commandhandlers;

import at.nbsgames.telegramsplatoon2maprotation.Main;
import at.nbsgames.telegramsplatoon2maprotation.commands.Command;
import at.nbsgames.telegramsplatoon2maprotation.commands.SenderLocation;
import at.nbsgames.telegramsplatoon2maprotation.database.SqlResult;

import java.sql.ResultSet;

public class CommandMaps extends Command {

    @Override
    public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObject) {
        try {
            SqlResult result = Main.getDatabaseHandler().getData("SELECT MapName FROM Maps;");
            ResultSet sets = result.getResultSet();
            boolean isFirst = true;
            StringBuilder reply = new StringBuilder();
            while(sets.next()){
                if(isFirst){
                    reply.append(sets.getString("MapName"));
                    isFirst = false;
                }
                else{
                    reply.append("\n");
                    reply.append(sets.getString("MapName"));
                }
            }
            result.closeResources();
            return reply;
        } catch (Exception e) {
            e.printStackTrace();
            return "Im sorry but something happened soooo!";
        }
    }
}
