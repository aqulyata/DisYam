package com.aqulasoft.disyam.models.bot;

import com.aqulasoft.disyam.audio.YandexMusicClient;
import com.aqulasoft.disyam.models.audio.YaArtist;
import com.aqulasoft.disyam.models.audio.YaSearchResult;
import com.aqulasoft.disyam.utils.BotStateType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.List;

import static com.aqulasoft.disyam.utils.Consts.EMOJI_MAP;

public class ArtistSearchState extends SearchPager implements BotState {

    private YaSearchResult searchResult;
    private Message message;

    public ArtistSearchState(YaSearchResult searchResult, Message message) {
        super(searchResult.getTotal(), searchResult.getPerPage());
        this.searchResult = searchResult;
        this.message = message;
    }

    @Override
    public Message getLastMessage() {
        return message;
    }

    @Override
    public BotStateType getType() {
        return BotStateType.SEARCH_PLAYLIST;
    }

    public YaArtist getArtist(int pos) {
        return searchResult.getArtists().get(pos);
    }

    public void updateMessage(boolean addReactions) {
        message.editMessage(buildMessage(addReactions)).queue(m -> {
            message = m;
        });
    }

    private MessageEmbed buildMessage(boolean addReactions) {
        EmbedBuilder builder = new EmbedBuilder();

        builder.setColor(Color.CYAN);
        List<YaArtist> artists = searchResult.getArtists();

        if (artists != null) {
            if (addReactions && hasPages()) message.addReaction("⬅️").queue();
            for (int i = 0; i < artists.size(); i++) {
                YaArtist artist = artists.get(i);
                String emojiNum = EMOJI_MAP.get(String.valueOf(i + 1));
                builder.addField(String.format("%s %s", emojiNum, artist.getName()), "", true);
                if (addReactions) message.addReaction(emojiNum).queue();
            }
            if (addReactions && hasPages()) message.addReaction("➡️").queue();
        }
        builder.setFooter(getPager());
        return builder.build();
    }

    @Override
    public void updateResults(int page) {
        searchResult = YandexMusicClient.search(searchResult.getSearchStr(), searchResult.getSearchType(), page);
        updateMessage(false);
    }
}
