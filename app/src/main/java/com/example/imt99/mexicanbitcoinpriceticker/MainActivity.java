package com.example.imt99.mexicanbitcoinpriceticker;

/**
 * Created by Markintoch on 14/07/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;


public class MainActivity extends AppCompatActivity {
    private static Context contexto;
    static TextView minimo;
    static TextView maximo;
    static TextView precioActual;
    static TextView compra;
    static TextView venta;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contexto = this;
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-8043603209068675~3544848145");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8043603209068675/8926333069");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        minimo  = (TextView) findViewById(R.id.minimoValue);
        maximo = (TextView) findViewById(R.id.maximoValue);
        precioActual = (TextView) findViewById(R.id.precioActual);
        compra = (TextView) findViewById(R.id.askValue);
        venta = (TextView) findViewById(R.id.bidValue);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mInterstitialAd.show();
        startService(new Intent(this,WidgetService.class)); //Inicia el servicio del widget
    }

    public static Context getAppContext(){ // Returna el contexto global de la aplicacion
        return contexto;
    }

    public static void setMinValor(String valor) { //Permite acceder a los items ID desde una clase externa
        minimo.setText(valor + " MXN");
    }

    public static void setMayorValor(String valor){
        maximo.setText(valor + " MXN");
    }

    public static void setPrecioActual(String valor){
        precioActual.setText("1 BTC = "+valor+" MXN");
    }

    public static void setCompraDemanda(String valor){
        compra.setText(valor+" MXN");
    }

    public static void setVentaDemanda(String valor){
        venta.setText(valor+" MXN");
    }
}
