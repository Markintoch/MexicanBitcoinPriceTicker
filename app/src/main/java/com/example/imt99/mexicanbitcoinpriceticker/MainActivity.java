package com.example.imt99.mexicanbitcoinpriceticker;

/**
 * Created by Markintoch on 14/07/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private static Context contexto;
    static TextView minimo;
    static TextView maximo;
    static TextView precioActual;
    static TextView compra;
    static TextView venta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contexto = this;
        minimo  = (TextView) findViewById(R.id.minimoValue);
        maximo = (TextView) findViewById(R.id.maximoValue);
        precioActual = (TextView) findViewById(R.id.precioActual);
        compra = (TextView) findViewById(R.id.askValue);
        venta = (TextView) findViewById(R.id.bidValue);
        startService(new Intent(this,WidgetService.class));
    }

    public static Context getAppContext(){
        return contexto;
    }

    public static void setMinValor(String valor) {
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
