package com.scheduler.genericscheduler.Controladores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.scheduler.genericscheduler.Modelos.Empleado;
import com.scheduler.genericscheduler.Modelos.Reserva;
import com.scheduler.genericscheduler.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CancelarReservaAdaptador extends BaseAdapter {

    private ArrayList<Reserva> reservas;
    private Context context;
    private String s;

    public CancelarReservaAdaptador(Context c){
        this.context=c;
        this.reservas= new ArrayList<>();
    }

    @Override
    public int getCount() {
        return reservas.size();
    }

    @Override
    public Object getItem(int position) {
        return reservas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Reserva reserva = (Reserva) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.cancelar_item,null);
        TextView tvIdReserva = convertView.findViewById(R.id.textViewIdReserva);
        TextView tvEntrada = convertView.findViewById(R.id.textViewEntrada);
        tvIdReserva.setText(reserva.getId());
        String entrada = reserva.getEntrada();
        SimpleDateFormat parseador = new SimpleDateFormat("ddMMyyyyHHmmss");
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        try {
            Date date = parseador.parse(entrada);
            s = formateador.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tvEntrada.setText(s);
        return convertView;
    }

    public void AdicionarListaReservas(ArrayList<Reserva> resp) {
        reservas.addAll(resp);
        notifyDataSetChanged();
    }
}
