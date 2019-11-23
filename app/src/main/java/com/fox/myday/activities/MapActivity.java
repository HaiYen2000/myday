package com.fox.myday.activities;

import androidx.annotation.IdRes;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fox.myday.R;
import com.fox.myday.base.CommonActivity;
import com.fox.myday.viewmodels.MapViewModel;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import static com.fox.myday.Constants.API_KEY;

public class MapActivity extends CommonActivity {

    private BottomBar bottomBar;
    private WebView webView;
    private MapViewModel mapViewModel;

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapViewModel = ViewModelProviders.of(this).get(MapViewModel.class);

        if (savedInstanceState == null) {
            mapViewModel.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            mapViewModel.mapLat = prefs.getFloat("latitude", 0);
            mapViewModel.mapLon = prefs.getFloat("longitude", 0);
            mapViewModel.apiKey = mapViewModel.sharedPreferences.getString("apiKey", API_KEY);
        }

        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/map.html?lat=" + mapViewModel.mapLat + "&lon="
                + mapViewModel.mapLon + "&appid=" + mapViewModel.apiKey
                + "&zoom=" + mapViewModel.mapZoom);
        webView.addJavascriptInterface(new HybridInterface(), "NativeInterface");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                if (savedInstanceState != null) {
                    setMapState(mapViewModel.tabPosition);
                }
            }
        });

        bottomBar = BottomBar.attach(this, savedInstanceState);
        bottomBar.setItems(R.menu.menu_map_bottom);
        bottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                setMapState(menuItemId);
                mapViewModel.tabPosition = menuItemId;
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
            }
        });
    }

    private void setMapState(int item) {
        switch (item) {
            case R.id.map_rain:
                webView.loadUrl("javascript:map.removeLayer(windLayer);map.removeLayer(tempLayer);"
                        + "map.addLayer(rainLayer);");
                break;
            case R.id.map_wind:
                webView.loadUrl("javascript:map.removeLayer(rainLayer);map.removeLayer(tempLayer);"
                        + "map.addLayer(windLayer);");
                break;
            case R.id.map_temperature:
                webView.loadUrl("javascript:map.removeLayer(windLayer);map.removeLayer(rainLayer);"
                        + "map.addLayer(tempLayer);");
                break;
            default:
                Log.w("WeatherMap", "Layer not configured");
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        bottomBar.onSaveInstanceState(outState);
    }

    private class HybridInterface {

        @JavascriptInterface
        public void transferLatLon(double lat, double lon) {
            mapViewModel.mapLat = lat;
            mapViewModel.mapLon = lon;
        }

        @JavascriptInterface
        public void transferZoom(int level) {
            mapViewModel.mapZoom = level;
        }
    }

}
