package com.aqulasoft.disyam.commands;

import com.aqulasoft.disyam.audio.YandexMusicClient;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.utils.AttachmentOption;

import java.util.List;

public class DownloadCommand implements Command {

    public DownloadCommand() {

    }

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        TextChannel channel = event.getChannel();

        if (args.isEmpty()) {
            channel.sendMessage("Please provide some arguments").queue();
            return;
        }
        byte[] song = YandexMusicClient.downloadSong(Long.parseLong(args.get(0)));
        channel.sendMessage("Here you are").addFile(song, "song.mp3", new AttachmentOption[0]).queue();
    }

    @Override
    public String getHelp(String prefix) {
        return "Download a song\n" +
                "Usage: `" + prefix + getInvoke() + " <Yandex song url>`";
    }

    @Override
    public String getInvoke() {
        return "download";
    }
}
