package me.xiaojibazhanshi.victorypointsystem.objects;

import static me.xiaojibazhanshi.victorypointsystem.util.GeneralUtil.color;

public record Level(int id, String title, int pointsToLvlUp, int pointsOnDeath, double dmgPerk, double hpPerk){

    @Override
    public String title() {
        return color(title);
    }

}
