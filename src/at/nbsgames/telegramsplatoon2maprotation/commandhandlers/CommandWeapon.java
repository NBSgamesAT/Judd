package at.nbsgames.telegramsplatoon2maprotation.commandhandlers;

import at.nbsgames.telegramsplatoon2maprotation.Main;
import at.nbsgames.telegramsplatoon2maprotation.commands.Command;
import at.nbsgames.telegramsplatoon2maprotation.commands.SenderLocation;
import at.nbsgames.telegramsplatoon2maprotation.database.SqlResult;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.sql.ResultSet;
import java.sql.SQLException;

public  class CommandWeapon extends Command {

    @Override
    public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObject) {
        try {
            SqlResult result = Main.getDatabaseHandler().getData("SELECT * FROM MainWeapons AS main INNER JOIN SubWeapons AS sub ON main.SubWeapon = sub.SubWeaponId INNER JOIN SpecialWeapons AS special ON main.SpecialWeapon = special.SpecialWeaponId INNER JOIN WeaponKind AS kind ON main.Kind = kind.WeaponKindId WHERE main.MainName = ?;", command);
            //StringBuilder builder = new StringBuilder();
            if(result.getResultSet().next()){
                createForMain(result.getResultSet());

                if(SenderLocation.DISCORD == commandReceiver){
                    MessageReceivedEvent e = (MessageReceivedEvent) additionalObject[0];
                    e.getChannel().sendMessage(createForMain(result.getResultSet()));
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

    static EmbedObject createForMain(ResultSet result) throws SQLException{
        EmbedObject weapon = new EmbedObject();
        EmbedObject.ThumbnailObject thumbnail = new EmbedObject.ThumbnailObject();
        thumbnail.url = "https://nbsgames.at/splatoon2" + result.getString("MainThumbnail");
        //thumbnail.width = 300;
        weapon.thumbnail = thumbnail;
        //System.out.println("https://nbsgames.at/splatoon2" + result.getString("MainThumbnail"));
        weapon.color = 0xffff00;
        weapon.title = "Information about " + result.getString("MainName");

        int num = 6;
        if(result.getString("MainName") != null){

        }

        EmbedObject.EmbedFieldObject[] fields = new EmbedObject.EmbedFieldObject[6];

        fields[0] = new EmbedObject.EmbedFieldObject();
        fields[0].name = "Category";
        fields[0].value = String.valueOf(result.getString("KindName"));
        fields[0].inline = true;

        fields[1] = new EmbedObject.EmbedFieldObject();
        fields[1].name = "Level";
        fields[1].value = String.valueOf(result.getInt("Level"));
        fields[1].inline = true;

        fields[2] = new EmbedObject.EmbedFieldObject();
        fields[2].name = "Cost";
        fields[2].value = String.valueOf(result.getInt("Cost"));
        fields[2].inline = false;

        fields[3] = new EmbedObject.EmbedFieldObject();
        fields[3].name = result.getString("Value1Name");
        fields[3].value = String.valueOf(result.getDouble("Value1"));
        fields[3].inline = true;

        fields[4] = new EmbedObject.EmbedFieldObject();
        fields[4].name = result.getString("Value2Name");
        fields[4].value = String.valueOf(result.getDouble("Value2"));
        fields[4].inline = true;

        fields[5] = new EmbedObject.EmbedFieldObject();
        fields[5].name = result.getString("Value3Name");
        fields[5].value = String.valueOf(result.getDouble("Value3"));
        fields[5].inline = false;

        weapon.fields = fields;

        weapon.fields = fields;



        return weapon;
    }




    EmbedObject.EmbedFieldObject[] createFieldsList (){

        return null;
    }

}
