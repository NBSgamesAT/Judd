# Judd
is a Telegram and Discord bot that shows information about Splatoon2. It can also create scrim sets, and much more.

## Differences between the Discord and Telegram Sid~e
The most noteable difference is, that all Infos are given through text in Telegram as Telegram does not have embeds. This mainly effects the commands turf, ranked, league, search and salmon. Another difference is the command character. Discord has the / reserved for their own commands like tts (text-to-speech), forcing bots to use other charecters such as an !. Judd uses the / on telegram, but an ; on Discord.

## Commands
Here is a list of all the commands:

- turf (0-11): Prints out the current Turf rotation. You can add a number from 0 to 11 to specify which timeslot with 0 being the current one and 11 being the furthes away (11 is typically 20 - 22 hours in the future)
- ranked (0-11): Same as Turf, but for Ranked Battle.
- league (0-11): Same as Turf, but for League Battles.
- rank <mode>: Looks up what Maps are currently playable in Ranked for given Mode. I don't really keep my database up-to-date tho...
- maps: Prints out all the names of the maps that Judd knows
- scrim (1-13) (all): Generates a scrim set. The amount of matches can be modified by typing the amount next to the command. If you want to use all possible map/mode combs, type all after the number (Example: scrim 9 all). Defaults to scrim 5 (ranked combs).
- search <map|mode> <map name | mode name>: Searches for a map or mode in the rotation.
- author: Prints out some of my information.
- invite: Prints and invite link for your discord server or the link to the telegram contact.
- version: Prints out some version numbers.
- help: Prints out help information for the bot.

## How to search for maps
The search command is kinda big. So let me explain it here.

search map <Map Name>: search for the map name in Regular, Ranked and League battle. Also accepts shorter versions like reef for the reef or tr for the reef.

search mode <Mode name>: Searches for the mode in Ranked and League battle. Also accepts shorter versions like tc for Tower Control.

## Contribute?
I'll always accept help in someway. So do if you want to.
