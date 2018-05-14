package com.scheduler.genericscheduler.Vistas.Activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.scheduler.genericscheduler.R;
import com.scheduler.genericscheduler.Vistas.Fragments.EmpleadosFragment;

public class PrincipalActivity extends AppCompatActivity {

    private EmpleadosFragment empleadosFragment;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        new TareaCambiarAFragmentEmpleados().execute();
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
}
