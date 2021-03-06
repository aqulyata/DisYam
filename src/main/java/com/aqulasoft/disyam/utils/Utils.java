package com.aqulasoft.disyam.utils;

import com.aqulasoft.disyam.models.audio.YaTrack;
import com.aqulasoft.disyam.models.audio.YaTrackSupplement;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

import java.awt.*;

public class Utils {
    public static boolean checkVoiceChannelAvailability(GuildMessageReceivedEvent event, TextChannel channel) {
        AudioManager audioManager = event.getGuild().getAudioManager();

        if (!audioManager.isConnected()) {
            channel.sendMessage("I'm not playing anything").queue();
            return false;
        }

        GuildVoiceState memberVoiceState = event.getMember().getVoiceState();

        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("Please join a voice channel first").queue();
            return false;
        }
        return true;
    }

    public static boolean joinVoice(GuildMessageReceivedEvent event, TextChannel channel) {
        AudioManager audioManager = event.getGuild().getAudioManager();

        if (!audioManager.isConnected()) {
            GuildVoiceState memberVoiceState = event.getMember().getVoiceState();

            if (memberVoiceState != null && !memberVoiceState.inVoiceChannel()) {
                channel.sendMessage("Please join a voice channel first").queue();
                return true;
            }
            VoiceChannel voiceChannel = memberVoiceState.getChannel();
            Member selfMember = event.getGuild().getSelfMember();

            if (!selfMember.hasPermission(voiceChannel, Permission.VOICE_CONNECT)) {
                channel.sendMessageFormat("I am missing permission to join %s", voiceChannel).queue();
                return true;
            }

            audioManager.openAudioConnection(voiceChannel);
        }
        return false;
    }

    public static String convertTimePeriod(long milliseconds) {
        String seconds = padLeftZeros(String.valueOf((milliseconds / 1000 % 60)), 2);
        String minutes = padLeftZeros(String.valueOf((milliseconds / (1000 * 60) % 60)), 2);
        int h = (int) (milliseconds / (1000 * 60 * 60) % 24);
        String hours = (h > 0 ? padLeftZeros(String.valueOf(h), 2) + ":" : "");
        return String.format("%s%s:%s", hours, minutes, seconds);
    }

    public static String padLeftZeros(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append('0');
        }
        sb.append(inputString);

        return sb.toString();
    }

    public static String transliterate(String message) {
        char[] abcCyr = {' ', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', '??', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        String[] abcLat = {" ", "a", "b", "v", "g", "d", "e", "e", "zh", "z", "i", "y", "k", "l", "m", "n", "o", "p", "r", "s", "t", "u", "f", "h", "ts", "ch", "sh", "sch", "", "i", "", "e", "ju", "ja", "A", "B", "V", "G", "D", "E", "E", "Zh", "Z", "I", "Y", "K", "L", "M", "N", "O", "P", "R", "S", "T", "U", "F", "H", "Ts", "Ch", "Sh", "Sch", "", "I", "", "E", "Ju", "Ja", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            for (int x = 0; x < abcCyr.length; x++) {
                if (message.charAt(i) == abcCyr[x]) {
                    builder.append(abcLat[x]);
                }
            }
        }
        return builder.toString();
    }

    public static Integer getEmojiNum(String emoji) throws NumberFormatException {
        switch (emoji) {
            case "1??????":
                return 1;
            case "2??????":
                return 2;
            case "3??????":
                return 3;
            case "4??????":
                return 4;
            case "5??????":
                return 5;
            case "6??????":
                return 6;
            case "7??????":
                return 7;
            case "8??????":
                return 8;
            case "9??????":
                return 9;
            case "\uD83D\uDD1F":
                return 10;
        }
        throw new NumberFormatException("Unknown emoji: " + emoji);
    }

    public static String trimString(String str, int maxLen) {
        return str.length() > maxLen ? str.substring(0, maxLen) : str;

    }
}
