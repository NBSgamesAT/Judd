package at.nbsgames.telegramsplatoon2maprotation.commandhandlers;

import at.nbsgames.telegramsplatoon2maprotation.Main;
import at.nbsgames.telegramsplatoon2maprotation.commands.Command;
import at.nbsgames.telegramsplatoon2maprotation.commands.SenderLocation;
import at.nbsgames.telegramsplatoon2maprotation.database.SqlResult;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommandMaps extends Command {

    @Override
    public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObject) {
        try {
            SqlResult result = Main.getDatabaseHandler().getData("SELECT MapName FROM Maps;");
            ResultSet sets = result.getResultSet();
            boolean isFirst = true;
            String reply = "";
            while(sets.next()){
                if(isFirst){
                    reply = sets.getString("MapName");
                    isFirst = false;
                }
                else{
                    reply = reply + "\n" + sets.getString("MapName");
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
