/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pucmm.jdbc.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author pc
 */
@Entity
@Table(name = "logic")
public class Room {

    @Column(name = "userlist")
    private String userlist;
    @Column(name = "playing on")
    private boolean playing = true;
    @Column(name = "playing of")
    private boolean playingoff = false;

    public String getUserlist() {
        return userlist;
    }

    public void setUserlist(String userlist) {
        this.userlist = userlist;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public boolean isPlayingoff() {
        return playingoff;
    }

    public void setPlayingoff(boolean playingoff) {
        this.playingoff = playingoff;
    }
}
