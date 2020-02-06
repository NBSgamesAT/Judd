package at.nbsgames.judd.commandhandlers;

import at.nbsgames.judd.ListenerDiscord;
import at.nbsgames.judd.Main;
import at.nbsgames.judd.commands.Command;
import at.nbsgames.judd.commands.MainCommandRegistry;
import at.nbsgames.judd.commands.SenderLocation;
import at.nbsgames.judd.database.SqlResult;
import at.nbsgames.judd.splatoonink.*;
import at.nbsgames.judd.telegram.enums.TextParseMode;
import at.nbsgames.judd.telegram.objects.TMessage;
import discord4j.core.event.domain.message.MessageCreateEvent;

public class CommandSearch extends Command {

    public CommandSearch(){
        this.addSubCommand("mode", new Command() {
            @Override
            public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObject) {
                WarKind kind = WarKind.getWarKindByName(command);
                if(kind == WarKind.CLAM_BLITZ || kind == WarKind.RAINMAKER || kind == WarKind.TOWER_CONTROL || kind == WarKind.SPLAT_ZONES){
                    FoundSlots slots = WebsiteChecker.searchForMode(kind);

                    if(commandReceiver == SenderLocation.DISCORD){
                        MessageCreateEvent e = (MessageCreateEvent) additionalObject[0];
                        e.getMessage().getChannel().block().createMessage("All the results I found:\n").block();
                        if(slots.getRanked2() != null){
                            e.getMessage().getChannel().block().createMessage(spec -> ListenerDiscord.createEmbedObjectForBattleSlots(spec, RankingKind.RANKED, slots.getRanked1(), slots.getRanked2())).block();
                        }
                        else if(slots.getRanked1() != null){
                            e.getMessage().getChannel().block().createMessage(spec -> ListenerDiscord.createEmbedObjectForBattleSlots(spec, RankingKind.RANKED, slots.getRanked1())).block();
                        }
                        if(slots.getLeague2() != null){
                            e.getMessage().getChannel().block().createMessage(spec -> ListenerDiscord.createEmbedObjectForBattleSlots(spec, RankingKind.LEAGUE, slots.getLeague1(), slots.getLeague2())).block();
                        }
                        else if(slots.getLeague1() != null){
                            e.getMessage().getChannel().block().createMessage(spec -> ListenerDiscord.createEmbedObjectForBattleSlots(spec, RankingKind.LEAGUE, slots.getLeague1())).block();
                        }
                        return true;
                    }
                    else{
                        TMessage e = (TMessage) additionalObject[0];
                        StringBuilder builder = new StringBuilder("All the results I found:\n\n");
                        if(slots.getRanked1() != null){
                            builder.append("*RANKED BATTLE*\n\n");
                            builder.append(CommandSearch.createTelegramRotationString(slots.getRanked1()));
                            builder.append("\n\n");
                        }
                        if(slots.getRanked2() != null){
                            builder.append(CommandSearch.createTelegramRotationString(slots.getRanked2()));
                            builder.append("\n\n");
                        }
                        if(slots.getLeague1() != null){
                            builder.append("*LEAGUE BATTLE*\n\n");
                            builder.append(CommandSearch.createTelegramRotationString(slots.getLeague1()));
                            builder.append("\n\n");
                        }
                        if(slots.getLeague2() != null){
                            builder.append(CommandSearch.createTelegramRotationString(slots.getLeague2()));
                            builder.append("\n\n");
                        }
                        e.getChat().sendMessageText(builder.toString().replaceAll("\\n\\n$", ""), -1, TextParseMode.MARKDOWN);
                        return true;
                    }

                }
                else{
                    return "Here are all available modes:\n\n" +
                            "Splat Zones: splatzones, sz\n" +
                            "Tower Control: towercontrol, tc\n" +
                            "Rainmaker: rm\n" +
                            "Clam Blitz: clam, cb\n\n" +
                            "Do we need to search for turf? No we don't. It's always going to be the first 2 of " + commandReceiver.getPrefix() + "turf";
                }
            }
        });
        this.addSubCommand("map", new Command() {
            @Override
            public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObject) {
                try{
                    SqlResult results = Main.getDatabaseHandler().getData("SELECT MapName FROM Maps WHERE ? in (LOWER(MapName), Syn1, Syn2);", command);
                    if(results.getResultSet().next()){
                        FoundSlots slots = WebsiteChecker.searchForMap(results.getResultSet().getString("MapName"));

                        if(commandReceiver == SenderLocation.DISCORD){
                            MessageCreateEvent e = (MessageCreateEvent) additionalObject[0];
                            e.getMessage().getChannel().block().createMessage("All the results I found:\n").block();
                            if(slots.getTurf2() != null){
                                e.getMessage().getChannel().block().createMessage(spec -> ListenerDiscord.createEmbedObjectForBattleSlots(spec, RankingKind.TURF_WAR, slots.getTurf1(), slots.getTurf2())).block();
                            }
                            else if(slots.getTurf1() != null){
                                e.getMessage().getChannel().block().createMessage(spec -> ListenerDiscord.createEmbedObjectForBattleSlots(spec, RankingKind.TURF_WAR, slots.getTurf1())).block();
                            }
                            if(slots.getRanked2() != null){
                                e.getMessage().getChannel().block().createMessage(spec -> ListenerDiscord.createEmbedObjectForBattleSlots(spec, RankingKind.RANKED, slots.getRanked1(), slots.getRanked2())).block();
                            }
                            else if(slots.getRanked1() != null){
                                e.getMessage().getChannel().block().createMessage(spec -> ListenerDiscord.createEmbedObjectForBattleSlots(spec, RankingKind.RANKED, slots.getRanked1())).block();
                            }
                            if(slots.getLeague2() != null){
                                e.getMessage().getChannel().block().createMessage(spec -> ListenerDiscord.createEmbedObjectForBattleSlots(spec, RankingKind.LEAGUE, slots.getLeague1(), slots.getLeague2())).block();
                            }
                            else if(slots.getLeague1() != null) {
                                e.getMessage().getChannel().block().createMessage(spec -> ListenerDiscord.createEmbedObjectForBattleSlots(spec, RankingKind.LEAGUE, slots.getLeague1())).block();
                            }
                        }
                        else{
                            TMessage e = (TMessage) additionalObject[0];
                            StringBuilder builder = new StringBuilder("All the results I found:\n\n");
                            if(slots.getTurf1() != null){
                                builder.append("*REGULAR BATTLE*\n\n");
                                builder.append(CommandSearch.createTelegramRotationString(slots.getTurf1()));
                                builder.append("\n\n");
                            }
                            if(slots.getTurf2() != null){
                                builder.append(CommandSearch.createTelegramRotationString(slots.getTurf2()));
                                builder.append("\n\n");
                            }
                            if(slots.getRanked1() != null){
                                builder.append("*RANKED BATTLE*\n\n");
                                builder.append(CommandSearch.createTelegramRotationString(slots.getRanked1()));
                                builder.append("\n\n");
                            }
                            if(slots.getRanked2() != null){
                                builder.append(CommandSearch.createTelegramRotationString(slots.getRanked2()));
                                builder.append("\n\n");
                            }
                            if(slots.getLeague1() != null){
                                builder.append("*LEAGUE BATTLE*\n\n");
                                builder.append(CommandSearch.createTelegramRotationString(slots.getLeague1()));
                                builder.append("\n\n");
                            }
                            if(slots.getLeague2() != null){
                                builder.append(CommandSearch.createTelegramRotationString(slots.getLeague2()));
                                builder.append("\n\n");
                            }
                            e.getChat().sendMessageText(builder.toString().replaceAll("\\n\\n$", ""), -1, TextParseMode.MARKDOWN);
                        }
                        results.closeResources();
                        return true;

                    }
                    else{
                        return "Usage: " + commandReceiver.getPrefix() + "search map <Map Name>.\n\n" +
                                "The Map were not found tho. Please use the " + commandReceiver.getPrefix() + "maps command to get a list of all maps.";
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                    return "Something strange happened!";
                }
            }
        });
    }

    @Override
    public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObject) {
        return MainCommandRegistry.runCommand("help search", commandReceiver, additionalObject);
    }

    static private String createTelegramRotationString(BattleSlotV2 battle){
        return "Mode: " + battle.getWarKind().getFriendlyName() + "\n" +
                "Maps: " + BattleSlotV2.createTrimmedMapString(battle.getMaps(), ", ") + "\n" +
                (battle.hasStarted() ? "Ends in: " : "Starts in: ") + battle.getTime().formatTime();
    }

}
