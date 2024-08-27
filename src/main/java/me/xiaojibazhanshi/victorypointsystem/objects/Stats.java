package me.xiaojibazhanshi.victorypointsystem.objects;

public class Stats {

    private int level;
    private int points;
    private int playerKills;
    private int deaths;
    private int passiveKills;
    private int aggressiveKills;

    public Stats(int level, int points, int playerKills, int deaths, int passiveKills, int aggressiveKills) {
        this.level = level;
        this.points = points;
        this.playerKills = playerKills;
        this.deaths = deaths;
        this.passiveKills = passiveKills;
        this.aggressiveKills = aggressiveKills;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getPlayerKD() {
        return (double) playerKills / deaths;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getAggressiveKills() {
        return aggressiveKills;
    }

    public void setAggressiveKills(int aggressiveKills) {
        this.aggressiveKills = aggressiveKills;
    }

    public int getAllKills() {
        return playerKills + passiveKills + aggressiveKills;
    }

    public int getPassiveKills() {
        return passiveKills;
    }

    public void setPassiveKills(int passiveKills) {
        this.passiveKills = passiveKills;
    }

    public int getPlayerKills() {
        return playerKills;
    }

    public void setPlayerKills(int playerKills) {
        this.playerKills = playerKills;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}