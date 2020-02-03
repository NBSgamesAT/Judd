package at.nbsgames.telegramsplatoon2maprotation.commandhandlers;

import at.nbsgames.telegramsplatoon2maprotation.Main;
import at.nbsgames.telegramsplatoon2maprotation.commands.Command;
import at.nbsgames.telegramsplatoon2maprotation.commands.SenderLocation;
import at.nbsgames.telegramsplatoon2maprotation.database.SqlResult;

import java.sql.SQLException;

public class CommandWeapons extends Command {

    @Override
    public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObject) {
        try {
            SqlResult result = Main.getDatabaseHandler().getData("SELECT MainName FROM MainWeapons;");
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
            }
            return builder.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            return e;
        }
    }
}
