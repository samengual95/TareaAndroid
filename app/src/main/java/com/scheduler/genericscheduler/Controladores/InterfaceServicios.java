package com.scheduler.genericscheduler.Controladores;

import com.scheduler.genericscheduler.Modelos.Empleado;
import com.scheduler.genericscheduler.Modelos.Horario;
import com.scheduler.genericscheduler.Modelos.ReservaEmpleadoConfirmada;
import com.scheduler.genericscheduler.Modelos.RespuestaSesion;
import com.scheduler.genericscheduler.Modelos.TokenRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Clara on 12/05/2018.
 */

public interface InterfaceServicios {

    @GET("api/empleado/listar/1/token")
    Call<ArrayList<Empleado>> ObtenerListaEmpleados();

    @GET("api/hora/listar/{ddMMYYY}/1/token")
    Call<ArrayList<Horario>> ObtenerListaHorarios(@Path("ddMMYYY") String dia);

    @GET("api/reserva/crear/{ddMMyyyyhhmmss}/{servicio}/tokenempleado")
    Call<ReservaEmpleadoConfirmada> ReservarEmpleado(@Path("ddMMyyyyhhmmss")String f, @Path("servicio")String s);

    @POST("api/session/login")
    Call<RespuestaSesion> ObtenerToken(@Body TokenRequest tokenRequest);
}
