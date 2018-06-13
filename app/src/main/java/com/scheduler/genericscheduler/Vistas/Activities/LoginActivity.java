package com.scheduler.genericscheduler.Vistas.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.scheduler.genericscheduler.Modelos.TokenRequest;
import com.scheduler.genericscheduler.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private ProgressDialog progressDialog;
    private TokenRequest tokenRequest;

    private static final String TAG = "Prueba3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.loginButton);
        loginButton.setReadPermissions(Arrays.asList("email","public_profile","contact_email","user_birthday"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response){
                        String nombre="";
                        String mail="";
                        String token="";
                        String ape="";
                        String fbId="";
                        if(response != null){
                            try{
                                if(object.getString("email") != null){
                                    mail = object.getString("email");
                                }
                                if(object.getString("first_name") != null){
                                    nombre = object.getString("first_name");
                                }
                                if(object.getString("last_name") != null){
                                    ape = object.getString("last_name");
                                }
                                if(object.getString("id") != null){
                                    fbId = object.getString("id");
                                }
                                token = loginResult.getAccessToken().getToken();
                                SharedPreferences preferences = getSharedPreferences("Preferencias_usuario", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("mail",mail);
                                editor.putString("token_fb",token);
                                editor.putString("nombre",nombre);
                                editor.putString("apellido",ape);
                                editor.putString("fb_id",fbId);
                                editor.apply();
                                tokenRequest = new TokenRequest();
                                tokenRequest.setToken(token);
                                tokenRequest.setCorreo(mail);
                                tokenRequest.setNombre(nombre);
                                tokenRequest.setApellido(ape);
                                tokenRequest.setFacebookid(fbId);
                                Log.e(TAG,"token:" + tokenRequest.getToken());
                                Log.e(TAG,"correo: " + tokenRequest.getCorreo());
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "email,first_name,last_name,id");
                request.setParameters(parameters);
                request.executeAsync();
                new TareaCambiarAHome().execute();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),R.string.cancel_inicio_facebook,Toast.LENGTH_SHORT);
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(),R.string.error_inicio_facebook,Toast.LENGTH_SHORT);
            }
        });
    }

    public class TareaCambiarAHome extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(LoginActivity.this);
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
            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
            intent.putExtra("datos_usuario",tokenRequest);
            startActivity(intent);
            overridePendingTransition(R.anim.left_in,R.anim.left_out);
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }
}
