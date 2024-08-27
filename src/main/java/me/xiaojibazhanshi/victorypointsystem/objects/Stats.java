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

    public void incrementLevel(boolean increment) {
        this.level = increment ? level + 1 : level;
    }

    public double getPlayerKD() {
        return deaths != 0 ? (double) getAllKills() / deaths : getAllKills();
    }

    public int getDeaths() {
        return deaths;
    }

    public void incrementDeaths(boolean increment) {
        this.deaths = increment ? deaths + 1 : deaths;
    }

    public int getAggressiveKills() {
        return aggressiveKills;
    }

    public void incrementAggressiveKills(boolean increment) {
        this.aggressiveKills = increment ? aggressiveKills + 1 : aggressiveKills;
    }

    public int getAllKills() {
        return playerKills + passiveKills + aggressiveKills;
    }

    public int getPassiveKills() {
        return passiveKills;
    }

    public void incrementPassiveKills(boolean increment) {
        this.passiveKills = increment ? passiveKills + 1 : passiveKills;
    }

    public int getPlayerKills() {
        return playerKills;
    }

    public void incrementPlayerKills(boolean increment) {
        this.playerKills = increment ? playerKills + 1 : playerKills;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}