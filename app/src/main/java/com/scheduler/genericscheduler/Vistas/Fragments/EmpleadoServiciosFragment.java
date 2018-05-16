package com.scheduler.genericscheduler.Vistas.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.scheduler.genericscheduler.Controladores.ServiciosAdaptador;
import com.scheduler.genericscheduler.Modelos.Empleado;
import com.scheduler.genericscheduler.R;

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
    private ServiciosAdaptador serviciosAdaptador;
    private ListView listViewServicios;

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
        listViewServicios = view.findViewById(R.id.list_view_servicios);
        serviciosAdaptador = new ServiciosAdaptador(getActivity());
        Empleado d = (Empleado) getActivity().getIntent().getExtras().getSerializable("empleado_seleccionado");
        serviciosAdaptador.AdicionarListaServicios(d.getServicios());
        listViewServicios.setAdapter(serviciosAdaptador);
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
}
