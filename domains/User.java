package edu.pucmm.jdbc.domains;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "names")
    private String names;
    @Column(name = "lastnames")
    private String lastnames;
    @Column(name = "password")
    private String password;
    @Column(name = "birthdate")
    private Date birthDate;
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "totalplays")
    private int totalplays;
    @Column(name = "wins")
    private int wins;
    @Column(name = "user")
    private int user;
    @Column(name = "loses")
    private int loses;

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getTotalplays() {
        return totalplays;
    }

    public void setTotalplays(int totalplays) {
        this.totalplays = totalplays;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLoses() {
        return loses;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }

    //si el status es true el status online y offline
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getLastnames() {
        return lastnames;
    }

    public void setLastnames(String lastnames) {
        this.lastnames = lastnames;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
