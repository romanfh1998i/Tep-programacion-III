package edu.pucmm.jdbc.domains;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "cant_players")
    private int cantPlayers;
    @OneToMany(mappedBy = "users")
    private Set<User> users;
    @OneToMany(mappedBy = "games")
    private Set<Game> games;

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantPlayers() {
        return cantPlayers;
    }

    public void setCantPlayers(int cantPlayers) {
        this.cantPlayers = cantPlayers;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

}
