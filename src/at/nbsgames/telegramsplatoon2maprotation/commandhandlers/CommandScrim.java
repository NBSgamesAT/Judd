package at.nbsgames.telegramsplatoon2maprotation.commandhandlers;

import at.nbsgames.telegramsplatoon2maprotation.Main;
import at.nbsgames.telegramsplatoon2maprotation.commands.Command;
import at.nbsgames.telegramsplatoon2maprotation.commands.MainCommandRegistry;
import at.nbsgames.telegramsplatoon2maprotation.commands.SenderLocation;
import at.nbsgames.telegramsplatoon2maprotation.database.SqlResult;
import at.nbsgames.telegramsplatoon2maprotation.splatoonink.AvailableMaps;
import at.nbsgames.telegramsplatoon2maprotation.splatoonink.Battle;
import at.nbsgames.telegramsplatoon2maprotation.splatoonink.WarKind;

import java.sql.SQLException;
import java.util.ArrayList;

public class CommandScrim extends Command {

    @Override
    public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObject) {
        int battleCount = 5;
        boolean areCombis = false;
        String[] splittedCommand;
        if(command != null){
            splittedCommand = command.split(" ");
            if(command.equals("all") || command.startsWith("all ")){
                areCombis = true;
            }
            else if(splittedCommand[0].matches("(10|11|12|13)|[1-9]")){
                battleCount = Integer.parseInt(splittedCommand[0]);
                if(splittedCommand.length > 1 && splittedCommand[1].equals("all")) {
                    areCombis = true;
                }
            }
            else{
                return MainCommandRegistry.runCommand("help scrim", commandReceiver, additionalObject);
            }
        }
        try{
            Battle[] battles = null;
            if(areCombis){
                SqlResult result = Main.getDatabaseHandler().getData("SELECT MapName FROM Maps WHERE SearchOnly = 0;");
                ArrayList<String> maps = new ArrayList<>();
                while(result.getResultSet().next()){
                    maps.add(result.getResultSet().getString("MapName"));
                }
                battles = WarKind.createScrim(maps, battleCount);

            }
            else{
                SqlResult result = Main.getDatabaseHandler().getData("SELECT MapName, RankSplatZones, RankTowerControl, RankRainmaker, RankClamBlitz FROM Maps WHERE SearchOnly = 0 AND 1 in (RankSplatZones, RankTowerControl, RankRainmaker, RankClamBlitz);");
                AvailableMaps scrimCreator = new AvailableMaps();
                while (result.getResultSet().next()) {
                    if(result.getResultSet().getInt("RankSplatZones") >= 1){
                        scrimCreator.addToSplatzones(result.getResultSet().getString("MapName"));
                    }
                    if(result.getResultSet().getInt("RankTowerControl") >= 1){
                        scrimCreator.addToTowerControl(result.getResultSet().getString("MapName"));
                    }
                    if(result.getResultSet().getInt("RankRainmaker") >= 1){
                        scrimCreator.addToRainmaker(result.getResultSet().getString("MapName"));
                    }
                    if(result.getResultSet().getInt("RankClamBlitz") >= 1){
                        scrimCreator.addToClamBlitz(result.getResultSet().getString("MapName"));
                    }
                }
                battles = scrimCreator.createScrimWithSet(battleCount);
            }
            String outputString = "Here is the scrim set you requested...\n" + (areCombis ? "Only Map/Mode combinations used for ranked" : "All Map/Mode combinations used!");
            for(int count = 0; count < battleCount; count++){
                if(commandReceiver == SenderLocation.DISCORD){
                    outputString = outputString + "\nGame " + (count + 1)+ ": `" + battles[count].getKind().getFriendlyName() + " -> " + battles[count].getMap() + "`";
                }
                else{
                    outputString = outputString + "\nGame " + (count + 1)+ ": " + battles[count].getKind().getFriendlyName() + " -> " + battles[count].getMap();
                }
            }
            return outputString;
        }
        catch(SQLException e){
            e.printStackTrace();
            return "Im having trouble right now!";
        }
    }
}
