package com.practica1.androidgame;

public class ShopConfigurationInfo {
    private BackgroundInfo[] backgroundList;

    public ShopConfigurationInfo(BackgroundInfo[] backgroundList)
    {
        this.backgroundList = backgroundList;
    }

    // GETTERS
    public BackgroundInfo[] getBackgroundList() {
        return backgroundList;
    }

}
