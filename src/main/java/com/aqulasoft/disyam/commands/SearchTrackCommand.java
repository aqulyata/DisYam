package com.aqulasoft.disyam.commands;

import com.aqulasoft.disyam.audio.PlayerManager;
import com.aqulasoft.disyam.audio.YandexMusicClient;
import com.aqulasoft.disyam.models.audio.YaSearchResult;
import com.aqulasoft.disyam.models.bot.TrackSearchState;
import com.aqulasoft.disyam.service.BotStateManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;

import static com.aqulasoft.disyam.utils.Consts.PREFIX;
import static com.aqulasoft.disyam.utils.Utils.joinVoice;

public class SearchTrackCommand implements Command {

    public SearchTrackCommand() {

    }

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();

        if (args.isEmpty()) {
            channel.sendMessage("Please provide some arguments").queue();
            return;
        }
        if (joinVoice(event, channel)) return;

        YaSearchResult searchResult = YandexMusicClient.search(String.join(" ", args), "all", 0);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        event.getChannel().sendMessage(builder.build()).queue(message -> {
            TrackSearchState state = new TrackSearchState(searchResult, message);
            BotStateManager.getInstance().setState(event.getGuild().getIdLong(), state, false);
            state.updateSearchMsg(true);
            PlayerManager playerManager = PlayerManager.getInstance();
            playerManager.loadAndPlaySearch(event.getChannel());
        });
    }

    @Override
    public String getHelp() {
        return "Search a song\n" +
                "Usage: `" + PREFIX + getInvoke() + " <song name>`";
    }

    @Override
    public String getInvoke() {
        return "st";
    }
}
