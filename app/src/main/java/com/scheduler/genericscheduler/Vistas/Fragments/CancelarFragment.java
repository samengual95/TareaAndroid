package com.scheduler.genericscheduler.Vistas.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.scheduler.genericscheduler.Controladores.CancelarReservaAdaptador;
import com.scheduler.genericscheduler.Controladores.InterfaceServicios;
import com.scheduler.genericscheduler.Modelos.Empleado;
import com.scheduler.genericscheduler.Modelos.Horario;
import com.scheduler.genericscheduler.Modelos.Reserva;
import com.scheduler.genericscheduler.Modelos.RespuestaSesion;
import com.scheduler.genericscheduler.Modelos.Status;
import com.scheduler.genericscheduler.R;
import com.scheduler.genericscheduler.Vistas.Activities.HomeActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CancelarFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "prueba";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView list_view_cancelar;
    private CancelarReservaAdaptador cancelarReservaAdaptador;
    private RespuestaSesion respuestaSesion;
    private ProgressDialog progressDialog;
    private Retrofit retrofit;
    private Boolean cancelar;
    private Reserva reserva;
    private String token;
    private String tipo;

    private OnFragmentInteractionListener mListener;

    public CancelarFragment() {
        // Required empty public constructor
    }

    public static CancelarFragment newInstance(String param1, String param2) {
        CancelarFragment fragment = new CancelarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cancelar, container, false);
        list_view_cancelar = view.findViewById(R.id.list_view_cancelar);
        cancelarReservaAdaptador = new CancelarReservaAdaptador(getActivity());
        SharedPreferences prefs = getActivity().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        token = prefs.getString("token","algo");
        tipo = prefs.getString("tipo","algo");
        if (tipo.equals("CLIENTE"))
            new TareaCargarReservas().execute();
        else
            new TareaCargarReservasEmpleado().execute();
        list_view_cancelar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                reserva = (Reserva) cancelarReservaAdaptador.getItem(position);
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getActivity());
                dialogo1.setTitle("Cancelar Reserva");
                dialogo1.setMessage("¿ Desea cancelar esta reserva ?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new TareaCancelarReserva().execute();
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialogo1.show();

            }
        });
        return view;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void obtenerDatos() {
        InterfaceServicios service = retrofit.create(InterfaceServicios.class);
        Call<ArrayList<Reserva>> reservaCall = service.ObtenerReservas(token);
        reservaCall.enqueue(new Callback<ArrayList<Reserva>>() {
            @Override
            public void onResponse(Call<ArrayList<Reserva>> call, Response<ArrayList<Reserva>> response) {
                ArrayList<Reserva> resp = response.body();
                Log.e(TAG,"paso");
                for (int i=0;i<resp.size();i++)
                    Log.e(TAG,"idreserva: "+ resp.get(i).getId());
                cancelarReservaAdaptador.AdicionarListaReservas(resp);
            }

            @Override
            public void onFailure(Call<ArrayList<Reserva>> call, Throwable t) {

            }
        });

    }

    private void obtenerDatosEmpleado() {
        InterfaceServicios service = retrofit.create(InterfaceServicios.class);
        Call<ArrayList<Reserva>> reservaCall = service.ObtenerReservasEmpleado(token);
        reservaCall.enqueue(new Callback<ArrayList<Reserva>>() {
            @Override
            public void onResponse(Call<ArrayList<Reserva>> call, Response<ArrayList<Reserva>> response) {
                ArrayList<Reserva> resp = response.body();
                Log.e(TAG,"paso");
                for (int i=0;i<resp.size();i++)
                    Log.e(TAG,"idreserva: "+ resp.get(i).getId());
                cancelarReservaAdaptador.AdicionarListaReservas(resp);
            }

            @Override
            public void onFailure(Call<ArrayList<Reserva>> call, Throwable t) {

            }
        });

    }

    public void CancelarReserva(){
        InterfaceServicios service = retrofit.create(InterfaceServicios.class);
        Bundle bundle = getActivity().getIntent().getExtras();
        Call<Status> statusCall = service.CancelarReserva(reserva.getId(),token);
        statusCall.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                Status ok = response.body();
                cancelar = false;
                SharedPreferences prefs = getActivity().getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("cancelar",cancelar);
                editor.commit();
                Toast toast = Toast.makeText(getActivity(), "Reserva Cancelada", Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {

            }
        });
    }


    public class TareaCargarReservas extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Procesando...");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://18.218.149.158/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            obtenerDatos();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            list_view_cancelar.setAdapter(cancelarReservaAdaptador);
            progressDialog.dismiss();
        }
    }

    public class TareaCargarReservasEmpleado extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Procesando...");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://18.218.149.158/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            obtenerDatosEmpleado();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            list_view_cancelar.setAdapter(cancelarReservaAdaptador);
            progressDialog.dismiss();
        }
    }

    public class TareaCancelarReserva extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Procesando...");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://18.218.149.158/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            CancelarReserva();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new TareaCambiarFragmentHome().execute();
            progressDialog.dismiss();
        }
    }

    public class TareaCambiarFragmentHome extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
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
            Intent intent = new Intent(getActivity(),HomeActivity.class);
            startActivity(intent);
            progressDialog.dismiss();
        }
    }

}
