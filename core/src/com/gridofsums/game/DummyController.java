package com.gridofsums.game;

/**
 * Created by Rod on 8/7/2017.
 */

class DummyController implements AdsController {
    @Override
    public boolean isWifiConnected() {
        return false;
    }

    @Override
    public void showBannerAd() {

    }
}