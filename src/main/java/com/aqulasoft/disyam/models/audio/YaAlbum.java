package com.aqulasoft.disyam.models.audio;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class YaAlbum {
    private final long id;
    private int year;
    private String genre;
    private final String title;
    private int trackCount;
    private final List<String> labels;
    private final List<YaArtist> artists;

    public YaAlbum(JSONObject json) {
        id = json.getLong("id");
        if (json.has("year")) year = json.getInt("year");
        if (json.has("genre")) genre = json.getString("genre");
        title = json.getString("title");
        if (json.has("trackCount")) trackCount = json.getInt("trackCount");
        artists = new ArrayList<>();
        if (json.has("artists")) {
            JSONArray jsonArtistArray = json.getJSONArray("artists");
            for (int i = 0; i < jsonArtistArray.length(); i++) {
                JSONObject artist = jsonArtistArray.getJSONObject(i);
                artists.add(new YaArtist(artist));
            }
        }

        labels = new ArrayList<>();
        if (json.has("labels")) {
            for (Object label :
                    json.getJSONArray("labels")
            ) {
                if (label instanceof String)
                    labels.add((String) label);
                else
                    labels.add(((JSONObject) label).getString("name"));

            }
        }

    }

}
