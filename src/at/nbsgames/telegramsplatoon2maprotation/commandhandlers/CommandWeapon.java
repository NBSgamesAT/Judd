package at.nbsgames.telegramsplatoon2maprotation.commandhandlers;

import at.nbsgames.telegramsplatoon2maprotation.Main;
import at.nbsgames.telegramsplatoon2maprotation.commands.Command;
import at.nbsgames.telegramsplatoon2maprotation.commands.SenderLocation;
import at.nbsgames.telegramsplatoon2maprotation.database.SqlResult;

import java.sql.ResultSet;
import java.sql.SQLException;

public  class CommandWeapon extends Command {

    @Override
    public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObject) {
        try {
            SqlResult result = Main.getDatabaseHandler().getData("SELECT * FROM MainWeapons AS main INNER JOIN SubWeapons AS sub ON main.SubWeapon = sub.SubWeaponId INNER JOIN SpecialWeapons AS special ON main.SpecialWeapon = special.SpecialWeaponId INNER JOIN WeaponKind AS kind ON main.Kind = kind.WeaponKindId WHERE main.MainName = ?;", command);
            StringBuilder builder = new StringBuilder();
            if(result.getResultSet().next()){
                builder.append("```\nWeapon Name: ");
                builder.append(result.getResultSet().getString("MainName"));
                builder.append("\n  Level: ");
                builder.append(result.getResultSet().getString("Level"));
                builder.append("\n  Cost: ");
                builder.append(result.getResultSet().getString("Cost"));

                formatData(builder, result.getResultSet(), "Value1");
                formatData(builder, result.getResultSet(), "Value2");
                formatData(builder, result.getResultSet(), "Value3");
                formatData(builder, result.getResultSet(), "Data1");
                formatData(builder, result.getResultSet(), "Data2");
                formatData(builder, result.getResultSet(), "Data3");

                builder.append("\n\nSub Weapon: ");
                builder.append(result.getResultSet().getString("SubWeaponName"));
                builder.append("\n  Ink Consumption: ");
                builder.append(result.getResultSet().getString("InkConsumption"));

                formatData(builder, result.getResultSet(), "SubDuration1");
                formatData(builder, result.getResultSet(), "SubDuration2");
                formatData(builder, result.getResultSet(), "SubDuration3");
                formatData(builder, result.getResultSet(), "SubDamage1");
                formatData(builder, result.getResultSet(), "SubDamage2");
                formatData(builder, result.getResultSet(), "SubDamage3");

                builder.append("\n\nSpecial Weapon: ");
                builder.append(result.getResultSet().getString("SpecialWeaponName"));
                builder.append("\n  Duration: ");
                builder.append(result.getResultSet().getString("SpecialDuration"));
                formatData(builder, result.getResultSet(), "SpecialDamage1");
                formatData(builder, result.getResultSet(), "SpecialDamage2");
                formatData(builder, result.getResultSet(), "SpecialDamage3");
                formatData(builder, result.getResultSet(), "SpecialDamage4");


                builder.append("\n```");
                result.closeResources();
                return builder.toString();
            }
            else {
                return "No Such Weapon Found";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return e;
        }
    }

    private static void formatData(StringBuilder builder, ResultSet result, String value) throws SQLException{
        String name = result.getString(value + "Name");
        if(name != null){
            builder.append("\n  ");
            builder.append(name);
            builder.append(": ");
            builder.append(result.getString(value));
        }
    }

}
