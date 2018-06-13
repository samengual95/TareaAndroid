package com.scheduler.genericscheduler.Controladores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.scheduler.genericscheduler.Modelos.Servicio;
import com.scheduler.genericscheduler.R;

import java.util.ArrayList;


public class ServiciosAdaptador extends BaseAdapter{
    private ArrayList<Servicio> servicios;
    private Context context;

    public ServiciosAdaptador(Context c){
        this.context=c;
        this.servicios = new ArrayList<>();
    }
    @Override
    public int getCount() {
        return servicios.size();
    }

    @Override
    public Object getItem(int position) {
        return servicios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Servicio servicio = (Servicio) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.servicio_item,null);
        TextView text1 = convertView.findViewById(R.id.textView11);
        text1.setText(servicio.getNombre());
        return convertView;
    }

    public void AdicionarListaServicios(ArrayList<Servicio> serv){
        servicios.addAll(serv);
        notifyDataSetChanged();
    }
}
