package at.nbsgames.judd.commandhandlers;

import at.nbsgames.judd.Main;
import at.nbsgames.judd.commands.Command;
import at.nbsgames.judd.commands.SenderLocation;
import at.nbsgames.judd.database.SqlResult;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.spec.MessageCreateSpec;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;

public  class CommandWeapon extends Command {

    @Override
    public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObject) {
        try {
            SqlResult result = Main.getDatabaseHandler().getData("SELECT * FROM MainWeapons AS main INNER JOIN SubWeapons AS sub ON main.SubWeapon = sub.SubWeaponId INNER JOIN SpecialWeapons AS special ON main.SpecialWeapon = special.SpecialWeaponId INNER JOIN WeaponKind AS kind ON main.Kind = kind.WeaponKindId WHERE main.MainName = ?;", command);
            //StringBuilder builder = new StringBuilder();
            if(result.getResultSet().next()){

                if(SenderLocation.DISCORD == commandReceiver){
                    MessageCreateEvent e = (MessageCreateEvent) additionalObject[0];
                    //e.getMessage().getChannel().sendMessage(createForMain(result.getResultSet()));
                    e.getMessage().getChannel().block().createMessage(messageCreateSpec -> createForMain(messageCreateSpec, result.getResultSet())).block();
                    result.closeResources();
                    return null;
                }
                else{
                    return null;
                }

                /*builder.append("```\nWeapon Name: ");
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
                return builder.toString();*/
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

    static void createForMain(MessageCreateSpec spec, ResultSet result) {
        spec.setEmbed(embed -> {
            try{
                if (result.getString("MainName") == null) {
                    spec.setContent("No content found");
                    return;
                }
                embed.setTitle("Information about " + result.getString("MainName"));
                embed.setThumbnail("https://nbsgames.at/splatoon2" + result.getString("MainThumbnail"));
                embed.setColor(new Color(0xFFFF00));
                embed.addField("Category", result.getString("KindName"), true);
                embed.addField("Level", String.valueOf(result.getInt("Level")), true);
                embed.addField("Cost", String.valueOf(result.getString("Cost")), false);
                embed.addField(result.getString("Value1Name"), String.valueOf(result.getDouble("Value1")), true);
                embed.addField(result.getString("Value2Name"), String.valueOf(result.getDouble("Value2")), true);
                embed.addField(result.getString("Value3Name"), String.valueOf(result.getDouble("Value3")), false);
            }
            catch(SQLException e){
                System.out.println("That Error Should never be seen.");
            }
        });

        //int num = 6;
    }
}
