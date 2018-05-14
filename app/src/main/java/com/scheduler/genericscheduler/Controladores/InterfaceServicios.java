package com.scheduler.genericscheduler.Controladores;

import com.scheduler.genericscheduler.Modelos.Empleado;
import com.scheduler.genericscheduler.Modelos.EmpleadoRespuesta;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Clara on 12/05/2018.
 */

public interface InterfaceServicios {

    @GET("api/empleado/listar/1/token")
    Call<EmpleadoRespuesta> ObtenerListaEmpleados();
}
