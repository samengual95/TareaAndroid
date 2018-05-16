package com.scheduler.genericscheduler.Controladores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.scheduler.genericscheduler.Modelos.Empleado;
import com.scheduler.genericscheduler.R;

import java.util.ArrayList;


public class EmpleadoAdaptador extends BaseAdapter {
    private ArrayList<Empleado> empleados;
    private Context context;

    public EmpleadoAdaptador(Context c){
        this.context=c;
        this.empleados = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return empleados.size();
    }

    @Override
    public Object getItem(int position) {
        return empleados.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Empleado empleado = (Empleado) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.empleado_item,null);
        TextView tvNombre = convertView.findViewById(R.id.textView9);
        tvNombre.setText(empleado.getNombre());
        TextView tvApellido = convertView.findViewById(R.id.textView10);
        tvApellido.setText(empleado.getApellido());
        return convertView;
    }

    public void AdicionarListaEmpleados(ArrayList<Empleado> resp) {
        empleados.addAll(resp);
        notifyDataSetChanged();
    }
}
