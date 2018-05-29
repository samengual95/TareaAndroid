package com.scheduler.genericscheduler.Vistas.Activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.scheduler.genericscheduler.Modelos.RespuestaSesion;
import com.scheduler.genericscheduler.Modelos.TokenRequest;
import com.scheduler.genericscheduler.R;
import com.scheduler.genericscheduler.Vistas.Fragments.CancelarFragment;
import com.scheduler.genericscheduler.Vistas.Fragments.EmpleadoServiciosFragment;
import com.scheduler.genericscheduler.Vistas.Fragments.EmpleadosFragment;

public class PrincipalActivity extends AppCompatActivity {

    private EmpleadosFragment empleadosFragment;
    private EmpleadoServiciosFragment empleadoServiciosFragment;
    private CancelarFragment cancelarFragment;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Bundle bundle = getIntent().getExtras();
        TokenRequest nuevo = (TokenRequest) bundle.getSerializable("datos_usuario");
        RespuestaSesion respuestaSesion = (RespuestaSesion) bundle.getSerializable("tokentipo");
        Boolean cancelar = (Boolean) bundle.get("cancelar");
        if (cancelar==true)
            new TareaCambiarAFragmentCancelar().execute();
        else{
            if(respuestaSesion.getTipo()=="EMPLEADO")
                new TareaCambiarAFragmentServicios().execute();
            else
                new TareaCambiarAFragmentEmpleados().execute();
        }
    }

    public class TareaCambiarAFragmentEmpleados extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(PrincipalActivity.this);
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
            empleadosFragment = new EmpleadosFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_contenedor,empleadosFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            progressDialog.dismiss();
        }
    }
    public class TareaCambiarAFragmentServicios extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(PrincipalActivity.this);
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
             empleadoServiciosFragment= new EmpleadoServiciosFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_contenedor,empleadoServiciosFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            progressDialog.dismiss();
        }
    }
    public class TareaCambiarAFragmentCancelar extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(PrincipalActivity.this);
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
            cancelarFragment= new CancelarFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_contenedor,cancelarFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            progressDialog.dismiss();
        }
    }
}
