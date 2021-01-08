package com.aqulasoft.disyam.models.bot;

import com.aqulasoft.disyam.models.audio.YaAudioException;
import com.aqulasoft.disyam.models.audio.YaTrack;
import lombok.Getter;

import java.util.List;

@Getter
abstract public class PlayerState {
    private boolean isRepeatOneOn = false;
    private boolean isPaused = false;
    private int position;

    public boolean isRepeatOneOn() {
        return isRepeatOneOn;
    }

    public void setRepeatOneOn(boolean repeatOneOn) {
        isRepeatOneOn = repeatOneOn;
        updateMessage(false);
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
        updateMessage(false);
    }

    public void updateRepeatOne() {
        isRepeatOneOn = !isRepeatOneOn;
        updateMessage(false);
    }

    public int prev() {
        if (isRepeatOneOn) return position;
        if (position - 1 >= 0) {
            position--;
        } else {
            throw new YaAudioException("Unable to load previous track");
        }
        return position;
    }

    public int next() {
        if (isRepeatOneOn) return position;
        if (position + 1 < getTrackCount()) {
            position++;
        } else {
            throw new YaAudioException("Unable to load next track");
        }
        return position;
    }

    public YaTrack getCurrentTrack() {
        return getTrack(position);
    }

    public abstract void updateMessage(boolean addReactions);

    public abstract List<YaTrack> getTracks();

    public abstract int getTrackCount();

    public abstract YaTrack getTrack(int pos);

}