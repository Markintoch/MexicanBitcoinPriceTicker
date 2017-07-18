package com.example.imt99.mexicanbitcoinpriceticker;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Markintoch on 14/07/2017.
 */

public class BitsoJSON extends AsyncTask<Object,Context,JSONObject>{
    private final String BOOK = "btc_mxn";
    private final String URLBitso = "https://api.bitso.com/v3/ticker?book="+ BOOK;
    Context contexto; //Contexto principal de la aplicacion

    public BitsoJSON(Context contextoPrincipal){
        this.contexto = contextoPrincipal;
    }

    @Override
    protected JSONObject doInBackground(Object... params) {
        try{
            URL urlConnection = new URL(URLBitso);
            HttpURLConnection conexion = (HttpURLConnection) urlConnection.openConnection(); //Objeto que hace la conexion con el URL del API
            conexion.setRequestProperty("User-Agent", "Mexican Bitcoin Price Ticker Widget");
            conexion.setRequestMethod("GET"); //Peticion al servidor de la API URL
            BufferedReader lecturaJSON = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            String linea;
            StringBuffer respuesta = new StringBuffer();
            while((linea = lecturaJSON.readLine()) != null){
                respuesta.append(linea.toString()); //Compone un String con saltos de linea
            }
            lecturaJSON.close(); //Se cierra el lector
            JSONObject bitcoinJSON = new JSONObject(respuesta.toString()); //Compone un JSON mediante el String estructurado
            return bitcoinJSON;
        }catch(Exception e){
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONObject bitcoinJSON) {
        RemoteViews bitsoWidget = new RemoteViews(contexto.getPackageName(), R.layout.widget_bitcoin);
        NotificationCompat.Builder contruccionNotificacion = new NotificationCompat.Builder(contexto)
                .setSmallIcon(R.drawable.bitcoinlogo)
                .setContentTitle("Mexican Bitcoin Price Ticker")
                .setContent(bitsoWidget)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        Notification notificacion = contruccionNotificacion.build();
        notificacion .flags = Notification.FLAG_NO_CLEAR;
        //notificacion.bigContentView = bitsoWidget;
        //notificacion.flags = Notification.FLAG_ONGOING_EVENT;
        try{
            Log.i("Run","Se a actualizado el valor");
            String precioReciente = String.valueOf(bitcoinJSON.getJSONObject("payload").getString("last"));
            String menor = String.valueOf(bitcoinJSON.getJSONObject("payload").getString("low"));
            String mayor = String.valueOf(bitcoinJSON.getJSONObject("payload").getString("high"));
            String compra = String.valueOf(bitcoinJSON.getJSONObject("payload").getString("ask"));
            String venta = String.valueOf(bitcoinJSON.getJSONObject("payload").getString("bid"));
            bitsoWidget.setTextViewText(R.id.bitcoinPrincipal, " 1 BTC = "+precioReciente+" MXN ");
            bitsoWidget.setTextViewText(R.id.minimoWidgetValue, "Minimo = "+menor+" MXN");
            bitsoWidget.setTextViewText(R.id.maximoWidgetValue, "Maximo = "+mayor+" MXN");
            MainActivity.setMinValor(menor);
            MainActivity.setMayorValor(mayor);
            MainActivity.setPrecioActual(precioReciente);
            MainActivity.setCompraDemanda(compra);
            MainActivity.setVentaDemanda(venta);
        }catch(Exception e){
            String mensaje = e.getMessage();
            Log.i("Error", mensaje );
        }
        NotificationManager notificacionService = (NotificationManager) contexto.getSystemService(Context.NOTIFICATION_SERVICE);
        notificacionService.notify(1,notificacion);

    }
}
