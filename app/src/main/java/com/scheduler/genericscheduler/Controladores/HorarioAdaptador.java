package com.scheduler.genericscheduler.Controladores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.scheduler.genericscheduler.Modelos.Horario;
import com.scheduler.genericscheduler.R;

import java.util.ArrayList;

public class HorarioAdaptador extends BaseAdapter {

    private ArrayList<Horario> horarios;
    private Context context;

    public HorarioAdaptador (Context c){
        this.context=c;
        this.horarios=new ArrayList<>();
    }
    @Override
    public int getCount() {
        return horarios.size();
    }

    @Override
    public Object getItem(int position) {
        return horarios.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Horario horario = (Horario) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.horario_item,null);
        TextView tvHora = convertView.findViewById(R.id.textViewHorario);
        tvHora.setText(horario.getHora());
        TextView tvReserva = convertView.findViewById(R.id.textViewReserva);
        if(horario.getHoraReservada()==null){
            tvReserva.setText("Disponible");
        }
        else
        {
            tvReserva.setText("Reservada");
        }
        return convertView;
    }

    public void AdicionarListaHorarios(ArrayList<Horario> resp) {
        horarios.addAll(resp);
        notifyDataSetChanged();
    }
}
