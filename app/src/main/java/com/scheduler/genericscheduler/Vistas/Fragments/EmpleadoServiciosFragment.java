package com.scheduler.genericscheduler.Vistas.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.scheduler.genericscheduler.Controladores.InterfaceServicios;
import com.scheduler.genericscheduler.Controladores.ServiciosAdaptador;
import com.scheduler.genericscheduler.Modelos.Empleado;
import com.scheduler.genericscheduler.Modelos.RespuestaSesion;
import com.scheduler.genericscheduler.Modelos.Servicio;
import com.scheduler.genericscheduler.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EmpleadoServiciosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EmpleadoServiciosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmpleadoServiciosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ProgressDialog progressDialog;
    private EmpleadosFragment empleadosFragment;
    private ServiciosAdaptador serviciosAdaptador;
    private ListView listViewServicios;
    private EmpleadoHorariosFragment empleadoHorariosFragment;
    private RespuestaSesion respuestaSesion;
    private Empleado empleado;
    private Retrofit retrofit;
    private String token;
    private String tipo;
    private android.support.v7.widget.Toolbar mToolbar;

    private static final String TAG = "Probando";

    private OnFragmentInteractionListener mListener;

    public EmpleadoServiciosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmpleadoServiciosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmpleadoServiciosFragment newInstance(String param1, String param2) {
        EmpleadoServiciosFragment fragment = new EmpleadoServiciosFragment();
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
        View view = inflater.inflate(R.layout.fragment_empleado_servicios, container, false);
        mToolbar = view.findViewById(R.id.toolbar3);
        mToolbar.setTitle(R.string.titulo_toolbar_servicios);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Mover a fragment empleados
                new TareaVolverAEmpleados().execute();

            }
        });
        listViewServicios = view.findViewById(R.id.list_view_servicios);
        serviciosAdaptador = new ServiciosAdaptador(getActivity());
        Bundle bundle = getActivity().getIntent().getExtras();
        empleado = (Empleado) bundle.getSerializable("empleado_seleccionado");
        SharedPreferences prefs = getActivity().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        token = prefs.getString("token","algo");
        tipo = prefs.getString("tipo","algo");
        if (tipo.equals("EMPLEADO"))
            new TareaCargarDatosServicios().execute();
        else
            new TareaCargarDatosServiciosCliente().execute();
        listViewServicios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Servicio s = (Servicio) serviciosAdaptador.getItem(i);
                getActivity().getIntent().putExtra("servicio_seleccionado",s);
                new TareaCambiarFragmentHorarios().execute();
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void obtenerDatosServicio() {
        InterfaceServicios service = retrofit.create(InterfaceServicios.class);
        Call<ArrayList<Servicio>> servicioListCall = service.ObtenerListaServicios(token);//aca tengo que pasar token empleado
        servicioListCall.enqueue(new Callback<ArrayList<Servicio>>() {
            @Override
            public void onResponse(Call<ArrayList<Servicio>> call, Response<ArrayList<Servicio>> response) {
                ArrayList<Servicio> resp = response.body();
                serviciosAdaptador.AdicionarListaServicios(resp);
            }

            @Override
            public void onFailure(Call<ArrayList<Servicio>> call, Throwable t) {

            }
        });
    }
    private void obtenerDatosServicioCliente() {
        InterfaceServicios service = retrofit.create(InterfaceServicios.class);
        Call<ArrayList<Servicio>> servicioListCall = service.ObtenerListaServiciosCliente(empleado.getId().toString(),token);
        servicioListCall.enqueue(new Callback<ArrayList<Servicio>>() {
            @Override
            public void onResponse(Call<ArrayList<Servicio>> call, Response<ArrayList<Servicio>> response) {
                ArrayList<Servicio> resp = response.body();
                serviciosAdaptador.AdicionarListaServicios(resp);
            }

            @Override
            public void onFailure(Call<ArrayList<Servicio>> call, Throwable t) {

            }
        });
    }


    public class TareaCargarDatosServicios extends AsyncTask<Void,Void,Void>{
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
            obtenerDatosServicio();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listViewServicios.setAdapter(serviciosAdaptador);
            progressDialog.dismiss();
        }
    }

    public class TareaCambiarFragmentHorarios extends AsyncTask<Void,Void,Void> {
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
            empleadoHorariosFragment = new EmpleadoHorariosFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_contenedor,empleadoHorariosFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            progressDialog.dismiss();
        }
    }
    public class TareaCargarDatosServiciosCliente extends AsyncTask<Void,Void,Void>{
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
            obtenerDatosServicioCliente();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listViewServicios.setAdapter(serviciosAdaptador);
            progressDialog.dismiss();
        }
    }
    public class TareaVolverAEmpleados extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            empleadosFragment = new EmpleadosFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_contenedor,empleadosFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            return null;
        }
    }
}
