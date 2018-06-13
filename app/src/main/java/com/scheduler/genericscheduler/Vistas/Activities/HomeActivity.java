package com.scheduler.genericscheduler.Vistas.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.scheduler.genericscheduler.Controladores.InterfaceServicios;
import com.scheduler.genericscheduler.Modelos.RespuestaSesion;
import com.scheduler.genericscheduler.Modelos.TokenRequest;
import com.scheduler.genericscheduler.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {


    private CardView reservar,reservas,cerrarSesion;
    private TextView textoUsuario;
    private CircleImageView imageView;
    private Retrofit retrofit;
    private RespuestaSesion respuestaSesion;
    private TokenRequest nuevo;
    private Boolean cancelar = false;
    private String token;
    private String fbId;
    private String tipo;
    private static final String TAG = "Prueba3";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(AccessToken.getCurrentAccessToken() == null){
            new TareaMoverseLogin().execute();
        }
        else{
            SharedPreferences preferences = getSharedPreferences("Preferencias_usuario", Context.MODE_PRIVATE);
            String mail = preferences.getString("mail","No hay dato");
            String nombre = preferences.getString("nombre","No hay dato");
            String ape = preferences.getString("apellido","No hay dato");
            String token_fb = preferences.getString("token_fb","No hay dato");
            String fb_id = preferences.getString("fb_id","No hay dato");
            nuevo = new TokenRequest();
            nuevo.setCorreo(mail);
            nuevo.setNombre(nombre);
            nuevo.setApellido(ape);
            nuevo.setToken(token_fb);
            nuevo.setFacebookid(fb_id);
            /*Bundle bundle = getIntent().getExtras();
            nuevo = (TokenRequest) bundle.getSerializable("datos_usuario");*/
            SharedPreferences prefs = getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
            token = prefs.getString("token","not");
            if (token.equals("not")){
                mandarDatos();
                respuestaSesion = new RespuestaSesion();
            }
            setContentView(R.layout.activity_home);
            imageView = findViewById(R.id.imagen_perfil_facebook);
            Picasso.get()
                    .load("https://graph.facebook.com/v2.2/" + fb_id + "/picture?height=120&type=normal")
                    .resize(85,85)
                    .into(imageView);
            textoUsuario = findViewById(R.id.textView2);
            textoUsuario.setText(nombre+" "+ape);
            reservar = findViewById(R.id.cardViewReserva);
            reservas = findViewById(R.id.cardViewCancelar);
            cerrarSesion = findViewById(R.id.cardViewCerrarSesion);

            reservar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new TareaMoversePrincipal().execute();
                }
            });

            reservas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new TareaMoversePrincipalCancelar().execute();
                }
            });

            cerrarSesion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cerrarSesion();
                    new TareaMoverseLogin().execute();
                }
            });
            }
        }

    public void mandarDatos(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://18.218.149.158/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        InterfaceServicios service = retrofit.create(InterfaceServicios.class);
        Call<RespuestaSesion> respuesta = service.ObtenerToken(nuevo);
        respuesta.enqueue(new Callback<RespuestaSesion>() {
            @Override
            public void onResponse(Call<RespuestaSesion> call, Response<RespuestaSesion> response) {
                    respuestaSesion = response.body();
                    SharedPreferences prefs = getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("token", respuestaSesion.getToken());
                    editor.putString("tipo", respuestaSesion.getTipo());
                    editor.apply();
                    Log.e(TAG,"tipo : " + respuestaSesion.getToken());
            }

            @Override
            public void onFailure(Call<RespuestaSesion> call, Throwable t) {
                Log.e(TAG,"adsasdadsa");
            }
        });
    }

    private ProgressDialog progressDialog;

    public class TareaMoversePrincipal extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(HomeActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Procesando...");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = new Intent(HomeActivity.this,PrincipalActivity.class);
            intent.putExtra("tokentipo",respuestaSesion);
            startActivity(intent);
            overridePendingTransition(R.anim.left_in,R.anim.left_out);
            progressDialog.dismiss();
        }
    }
    public class TareaMoversePrincipalCancelar extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(HomeActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Procesando...");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            cancelar = true;
            SharedPreferences prefs = getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("cancelar",cancelar);
            editor.commit();
            Intent intent = new Intent(HomeActivity.this,PrincipalActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.left_in,R.anim.left_out);
            progressDialog.dismiss();
        }
    }

    public class TareaMoverseLogin extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(HomeActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Procesando...");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.left_in,R.anim.left_out);
            progressDialog.dismiss();
        }
    }


    public void cerrarSesion(){
        SharedPreferences prefs = getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit().clear();
        editor.apply();
        LoginManager.getInstance().logOut();
    }
}
