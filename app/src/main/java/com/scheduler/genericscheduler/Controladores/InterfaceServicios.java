package com.scheduler.genericscheduler.Controladores;

import com.scheduler.genericscheduler.Modelos.Empleado;
import com.scheduler.genericscheduler.Modelos.EmpleadoRespuesta;
import com.scheduler.genericscheduler.Modelos.Horario;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Clara on 12/05/2018.
 */

public interface InterfaceServicios {

    @GET("api/empleado/listar/1/token")
    Call<ArrayList<Empleado>> ObtenerListaEmpleados();

    @GET("api/hora/listar/{ddMMYYY}/1/token")
    Call<ArrayList<Horario>> ObtenerListaHorarios(@Path("ddMMYYY") String dia);
}
