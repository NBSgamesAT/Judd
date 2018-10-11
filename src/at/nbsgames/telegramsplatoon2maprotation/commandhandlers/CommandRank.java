package at.nbsgames.telegramsplatoon2maprotation.commandhandlers;

import at.nbsgames.telegramsplatoon2maprotation.DatabaseColumnNames;
import at.nbsgames.telegramsplatoon2maprotation.Main;
import at.nbsgames.telegramsplatoon2maprotation.commands.Command;
import at.nbsgames.telegramsplatoon2maprotation.commands.MainCommandRegistry;
import at.nbsgames.telegramsplatoon2maprotation.commands.SenderLocation;
import at.nbsgames.telegramsplatoon2maprotation.database.SqlResult;
import at.nbsgames.telegramsplatoon2maprotation.splatoonink.WarKind;

import java.sql.SQLException;

public class CommandRank extends Command {

    @Override
    public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObject) {
        if(command != null){
            WarKind mode = WarKind.getWarKindByName(command);
            if(mode != WarKind.UNKNOWN && mode != WarKind.SPLATFEST && mode != WarKind.TURF_WAR){
                try {
                    SqlResult result = Main.getDatabaseHandler().getData("SELECT MapName FROM Maps WHERE " + DatabaseColumnNames.getRankColumnsByWarKind(mode) + " != 0");
                    StringBuilder builder = new StringBuilder();
                    builder.append("This are the maps playable in ");
                    builder.append(mode.getFriendlyName());
                    builder.append(":");
                    builder.append(commandReceiver == SenderLocation.DISCORD ? "\n```" : "\n");
                    while(result.getResultSet().next()){
                       builder.append("\n");
                       builder.append(result.getResultSet().getString("MapName"));
                    }
                    if(commandReceiver == SenderLocation.DISCORD) builder.append("\n```");
                    return builder.toString();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return "There is a problem";
                }
            }
            else{
                return "C'mon. The mode must be a mode that happens in ranked battle.";
            }
        }
        else{
            return MainCommandRegistry.runCommand("help rank", commandReceiver, additionalObject);
        }
    }

}
