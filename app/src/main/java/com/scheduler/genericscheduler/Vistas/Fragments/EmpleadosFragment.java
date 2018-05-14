package com.scheduler.genericscheduler.Vistas.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.scheduler.genericscheduler.Controladores.EmpleadoAdaptador;
import com.scheduler.genericscheduler.Controladores.InterfaceServicios;
import com.scheduler.genericscheduler.Modelos.Empleado;
import com.scheduler.genericscheduler.Modelos.EmpleadoRespuesta;
import com.scheduler.genericscheduler.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class EmpleadosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ProgressDialog progressDialog;
    private static final String TAG = "Prueba";

    private EmpleadoAdaptador empleadoAdaptador;
    private ListView listViewEmpleado;

    private OnFragmentInteractionListener mListener;

    public EmpleadosFragment() {
        // Required empty public constructor
    }
    private Retrofit retrofit;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmpleadosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmpleadosFragment newInstance(String param1, String param2) {
        EmpleadosFragment fragment = new EmpleadosFragment();
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
        View view = inflater.inflate(R.layout.fragment_empleados, container, false);
        listViewEmpleado = view.findViewById(R.id.listView_empleados);
        empleadoAdaptador = new EmpleadoAdaptador(getActivity());
        new TareaCargarDatosEmpleados().execute();
        return view;
    }

    private Empleado obtenerDatos() {
        InterfaceServicios service = retrofit.create(InterfaceServicios.class);
        Call<EmpleadoRespuesta> empleadoRespuestaCall = service.ObtenerListaEmpleados();
        empleadoRespuestaCall.enqueue(new Callback<EmpleadoRespuesta>() {
            @Override
            public void onResponse(Call<EmpleadoRespuesta> call, Response<EmpleadoRespuesta> response) {
                if(response.isSuccessful()){
                    EmpleadoRespuesta empleadoRespuesta = response.body();
                    ArrayList<Empleado> resp = empleadoRespuesta.getEmpleados();
                    empleadoAdaptador.AdicionarListaEmpleados(resp);
                }
                else{
                }
            }

            @Override
            public void onFailure(Call<EmpleadoRespuesta> call, Throwable t) {

            }
        });
        return null;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class TareaCargarDatosEmpleados extends AsyncTask<Void,Void,Void>{
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
                    .baseUrl("http://18.219.46.139/grupo2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            obtenerDatos();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listViewEmpleado.setAdapter(empleadoAdaptador);
            progressDialog.dismiss();
        }
    }

}
