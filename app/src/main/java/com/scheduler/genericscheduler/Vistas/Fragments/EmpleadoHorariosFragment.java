package com.scheduler.genericscheduler.Vistas.Fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.scheduler.genericscheduler.Controladores.HorarioAdaptador;
import com.scheduler.genericscheduler.Controladores.InterfaceServicios;
import com.scheduler.genericscheduler.Modelos.Empleado;
import com.scheduler.genericscheduler.Modelos.Horario;
import com.scheduler.genericscheduler.R;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EmpleadoHorariosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EmpleadoHorariosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class EmpleadoHorariosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button botonReserva;
    private Button botonOK;
    private int dia,mes,anio;
    private TextView textoReserva;
    private HorarioAdaptador horarioAdaptador;
    private Retrofit retrofit;
    private ListView listViewHorarios;
    private String fecha;
    private String fecha1;

    private static final String TAG = "Probando";

    private OnFragmentInteractionListener mListener;



    public EmpleadoHorariosFragment() {
        // Required empty public constructor
    }


    private ProgressDialog progressDialog;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmpleadoHorariosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmpleadoHorariosFragment newInstance(String param1, String param2) {
        EmpleadoHorariosFragment fragment = new EmpleadoHorariosFragment();
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
        View view = inflater.inflate(R.layout.fragment_empleado_horarios, container, false);
        textoReserva=view.findViewById(R.id.tvDia);
        botonReserva = view.findViewById(R.id.ButtonDia);
        botonOK = view.findViewById(R.id.ButtonOK);
        listViewHorarios = view.findViewById(R.id.list_view_horarios);
        botonReserva.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                dia = c.get(Calendar.DAY_OF_MONTH);
                mes = c.get(Calendar.MONTH);
                anio = c.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        textoReserva.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                        SimpleDateFormat parseador = new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat formateador = new SimpleDateFormat("ddMMyyyy");
                        fecha = textoReserva.getText().toString();
                        textoReserva.setText(" ");
                        try {
                            Date d = parseador.parse(fecha);
                             fecha1 =  formateador.format(d);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        getActivity().getIntent().putExtra("fecha",fecha);

                    }
                },dia,mes,anio);
                datePickerDialog.show();
            }
        });
        botonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                horarioAdaptador = new HorarioAdaptador(getActivity());
                new TareaCargarHorarios().execute();
            }
        });

        Empleado d = (Empleado) getActivity().getIntent().getExtras().getSerializable("empleado_seleccionado");
        return view;
    }

    private void obtenerDatos() {
        InterfaceServicios service = retrofit.create(InterfaceServicios.class);
        Call<ArrayList<Horario>> horarioRespuestaCall = service.ObtenerListaHorarios(fecha1);
        horarioRespuestaCall.enqueue(new Callback<ArrayList<Horario>>() {
            @Override
            public void onResponse(Call<ArrayList<Horario>> call, Response<ArrayList<Horario>> response) {
                ArrayList<Horario> resp = response.body();
                horarioAdaptador.AdicionarListaHorarios(resp);
                for (int i = 0 ; i < resp.size();i++){
                    Horario e = resp.get(i);
                    Log.e(TAG,"hora:" + e.getHora());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Horario>> call, Throwable t) {
                Log.e(TAG,"erroresVarios");
            }
        });
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

    public class TareaCargarHorarios extends AsyncTask<Void,Void,Void> {
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
                    .baseUrl("http://18.219.46.139/grupo1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            obtenerDatos();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listViewHorarios.setAdapter(horarioAdaptador);
            progressDialog.dismiss();
        }
    }
}
