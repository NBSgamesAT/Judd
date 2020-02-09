package at.nbsgames.judd.commandhandlers;

import at.nbsgames.judd.Main;
import at.nbsgames.judd.commands.Command;
import at.nbsgames.judd.commands.SenderLocation;
import at.nbsgames.judd.database.SqlResult;

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
            return reply.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Im sorry but something happened soooo!";
        }
    }
}
