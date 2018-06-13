package com.scheduler.genericscheduler.Vistas.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.scheduler.genericscheduler.Controladores.HorarioAdaptador;
import com.scheduler.genericscheduler.Controladores.InterfaceServicios;
import com.scheduler.genericscheduler.Modelos.Empleado;
import com.scheduler.genericscheduler.Modelos.Horario;
import com.scheduler.genericscheduler.Modelos.ReservaEmpleadoConfirmada;
import com.scheduler.genericscheduler.Modelos.RespuestaSesion;
import com.scheduler.genericscheduler.Modelos.Servicio;
import com.scheduler.genericscheduler.R;
import com.scheduler.genericscheduler.Vistas.Activities.HomeActivity;

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



public class EmpleadoHorariosFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private Button botonReserva;
    private Button botonOK;
    private int dia,mes,anio;
    private TextView textoReserva;
    private HorarioAdaptador horarioAdaptador;
    private Retrofit retrofit;
    private ListView listViewHorarios;
    private String fecha,fecha3;
    private String fecha1;
    private String fechaReserva;
    private String s;
    private String s1;
    private Empleado empleado;
    private RespuestaSesion respuestaSesion;
    private Servicio servicio;
    private static final String TAG = "Probando";
    private OnFragmentInteractionListener mListener;
    private ProgressDialog progressDialog;
    private String token;
    private String tipo;
    private DialogFragment ConfirmarReserva;
    private android.support.v7.widget.Toolbar mToolbar;
    EmpleadoServiciosFragment empleadoServiciosFragment;
    CardView cardView;

    public EmpleadoHorariosFragment() {
        // Required empty public constructor
    }


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
        mToolbar = view.findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.titulo_toolbar_horarios);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TareaVolverAServicios().execute();
            }
        });
        cardView = view.findViewById(R.id.cardViewDia);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                dia = c.get(Calendar.DAY_OF_MONTH);
                mes = c.get(Calendar.MONTH);
                anio = c.get(Calendar.YEAR);

                final DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        textoReserva.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                        SimpleDateFormat parseador = new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat formateador = new SimpleDateFormat("ddMMyyyy");
                        SimpleDateFormat formateador1 = new SimpleDateFormat("dd/MM/yyyy");
                        fecha = textoReserva.getText().toString();
                        try {
                            Date d = parseador.parse(fecha);
                            fecha1 =  formateador.format(d);
                            fecha3 = formateador1.format(d);
                            textoReserva.setText(fecha3);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        s1 = fecha1.toString();
                        horarioAdaptador = new HorarioAdaptador(getActivity());
                        if (tipo.equals("CLIENTE"))
                            new TareaCargarHorarios().execute();
                        else
                            new TareaCargarHorariosEmpleado().execute();
                    }
                },anio,mes,dia);
                datePickerDialog.show();
            }
        });
        Bundle bundle = getActivity().getIntent().getExtras();
        SharedPreferences prefs = getActivity().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        token = prefs.getString("token","algo");
        tipo = prefs.getString("tipo","algo");
        empleado = (Empleado) getActivity().getIntent().getExtras().getSerializable("empleado_seleccionado");
        servicio = (Servicio) getActivity().getIntent().getExtras().getSerializable("servicio_seleccionado");
        textoReserva=view.findViewById(R.id.tvDia);
        listViewHorarios = view.findViewById(R.id.list_view_horarios);
        listViewHorarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Horario h = (Horario) horarioAdaptador.getItem(i);
                if (h.getHoraReservada()!=null){
                    Toast toast = Toast.makeText(getActivity(), "La hora se encuentra reservada", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    String horario = h.getHora().toString();
                    SimpleDateFormat parseador1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                    SimpleDateFormat formateador1 = new SimpleDateFormat("ddMMyyyyHHmmss");
                    try {
                        Date d1 = parseador1.parse(horario);
                        fechaReserva=formateador1.format(d1);
                        Log.e(TAG,"horario:" + fechaReserva);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    s = fechaReserva.toString();


                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getActivity());
                    dialogo1.setTitle("Confirmar Reserva");
                    dialogo1.setMessage("¿ Desea agendar esta reserva ?");
                    dialogo1.setCancelable(false);
                    dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            if (tipo.equals("CLIENTE"))
                                new TareaReservar().execute();
                            else
                                new TareaReservarEmpleado().execute();
                        }
                    });
                    dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                        }
                    });
                    dialogo1.show();

                }
            }
        });
        return view;
    }

    private void obtenerDatos() {
        InterfaceServicios service = retrofit.create(InterfaceServicios.class);
        Call<ArrayList<Horario>> horarioCall = service.ObtenerListaHorarios(s1,empleado.getId(),token);
        horarioCall.enqueue(new Callback<ArrayList<Horario>>() {
            @Override
            public void onResponse(Call<ArrayList<Horario>> call, Response<ArrayList<Horario>> response) {
                ArrayList<Horario> resp = response.body();
                horarioAdaptador.AdicionarListaHorarios(resp);
            }

            @Override
            public void onFailure(Call<ArrayList<Horario>> call, Throwable t) {
                Log.e(TAG,"hayReserva" );
            }
        });
    }

    private void obtenerDatosEmpleado() {
        InterfaceServicios service = retrofit.create(InterfaceServicios.class);
        Call<ArrayList<Horario>> horarioCall = service.ObtenerListaHorariosEmpleado(s1,token);
        horarioCall.enqueue(new Callback<ArrayList<Horario>>() {
            @Override
            public void onResponse(Call<ArrayList<Horario>> call, Response<ArrayList<Horario>> response) {
                ArrayList<Horario> resp = response.body();
                horarioAdaptador.AdicionarListaHorarios(resp);
            }

            @Override
            public void onFailure(Call<ArrayList<Horario>> call, Throwable t) {
                Log.e(TAG,"hayReserva" );
            }
        });
    }

    private void ReservarEmpleado(){
        InterfaceServicios service = retrofit.create(InterfaceServicios.class);
        Call<ReservaEmpleadoConfirmada> reservaEmpleadoCall = service.ReservarEmpleado(s,empleado.getId(),servicio.getId(),token);
        reservaEmpleadoCall.enqueue(new Callback<ReservaEmpleadoConfirmada>() {
            @Override
            public void onResponse(Call<ReservaEmpleadoConfirmada> call, Response<ReservaEmpleadoConfirmada> response) {
                ReservaEmpleadoConfirmada resp = response.body();
                Toast toast = Toast.makeText(getActivity(), "Reserva exitosa", Toast.LENGTH_SHORT);
                toast.show();
            }
            @Override
            public void onFailure(Call<ReservaEmpleadoConfirmada> call, Throwable t) {
            }
        });
    }

    private void ReservarEmpleadoEmpleado(){
        InterfaceServicios service = retrofit.create(InterfaceServicios.class);
        Call<ReservaEmpleadoConfirmada> reservaEmpleadoCall = service.ReservarEmpleadoEmpleado(s,servicio.getId(),token);
        reservaEmpleadoCall.enqueue(new Callback<ReservaEmpleadoConfirmada>() {
            @Override
            public void onResponse(Call<ReservaEmpleadoConfirmada> call, Response<ReservaEmpleadoConfirmada> response) {
                ReservaEmpleadoConfirmada resp = response.body();
                Toast toast = Toast.makeText(getActivity(), "Reserva exitosa", Toast.LENGTH_SHORT);
                toast.show();
            }
            @Override
            public void onFailure(Call<ReservaEmpleadoConfirmada> call, Throwable t) {
            }
        });
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
                    .baseUrl("http://18.218.149.158/")
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

    public class TareaCargarHorariosEmpleado extends AsyncTask<Void,Void,Void> {
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
            listViewHorarios.setAdapter(horarioAdaptador);
            progressDialog.dismiss();
        }
    }

    public class TareaReservar extends AsyncTask<Void,Void,Void> {
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
            ReservarEmpleado();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new TareaCambiarFragmentHome().execute();
            progressDialog.dismiss();
        }
    }

    public class TareaReservarEmpleado extends AsyncTask<Void,Void,Void> {
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
            ReservarEmpleadoEmpleado();
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
            intent.putExtra("tokentipo",respuestaSesion);
            startActivity(intent);
            progressDialog.dismiss();
        }
    }
    public class TareaVolverAServicios extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            empleadoServiciosFragment = new EmpleadoServiciosFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_contenedor,empleadoServiciosFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            return null;
        }
    }
}
