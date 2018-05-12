package com.scheduler.genericscheduler.Controladores;

import com.scheduler.genericscheduler.Modelos.EmpleadoRespuesta;

import retrofit2.Call;

/**
 * Created by Clara on 12/05/2018.
 */

public interface InterfaceServicios {

    Call<EmpleadoRespuesta> ObtenerListaEmpleados();
}
