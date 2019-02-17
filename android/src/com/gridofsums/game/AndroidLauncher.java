package com.gridofsums.game;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.gridofsums.game.GridOfSums;
import com.mopub.common.MoPub;
import com.mopub.common.SdkConfiguration;

public class AndroidLauncher extends AndroidApplication implements AdsController {
	private static final String BANNER_AD_UNIT_ID = "ca-app-pub-5225464865745943/7568157939";
	private static final String BANNER_MOPUB_UNIT_ID = "2b2bed7c41c04f31a2d11ec96e20101d";


	AdView bannerAd;
	View gameView;
	RelativeLayout layout;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		gameView = initializeForView(new GridOfSums(this), config);
		setupAds();
		layout = new RelativeLayout(this);
		layout.addView(gameView, ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		layout.addView(bannerAd, params);

		MobileAds.initialize(this, BANNER_AD_UNIT_ID);
		setContentView(layout);

		setContentView(layout);

        SdkConfiguration sdkConfiguration =
                new SdkConfiguration.Builder(BANNER_MOPUB_UNIT_ID).build();

        MoPub.initializeSdk(this, sdkConfiguration, null);
	}

	public void setupAds() {
		bannerAd = new AdView(this);
		bannerAd.setVisibility(View.INVISIBLE);
		bannerAd.setBackgroundColor(Color.TRANSPARENT);
		bannerAd.setAdUnitId(BANNER_AD_UNIT_ID);
		bannerAd.setAdSize(AdSize.SMART_BANNER);
	}

	@Override
	public boolean isWifiConnected() {
		ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null && activeNetwork.isConnected();
		return isConnected;
	}

	@Override
	public void showBannerAd() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				bannerAd.setVisibility(View.VISIBLE);
				AdRequest.Builder builder = new AdRequest.Builder();
				AdRequest ad = builder.build();
				bannerAd.loadAd(ad);
			}
		});
	}

	@Override
	public void onDestroy() {
		if (bannerAd != null) {
			bannerAd.destroy();
		}
		super.onDestroy();
	}
}
