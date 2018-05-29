package com.scheduler.genericscheduler.Controladores;

import com.scheduler.genericscheduler.Modelos.Empleado;
import com.scheduler.genericscheduler.Modelos.Horario;
import com.scheduler.genericscheduler.Modelos.Reserva;
import com.scheduler.genericscheduler.Modelos.ReservaEmpleadoConfirmada;
import com.scheduler.genericscheduler.Modelos.RespuestaSesion;
import com.scheduler.genericscheduler.Modelos.Status;
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

    @GET("api/empleado/listar/1/token")//token cliente
    Call<ArrayList<Empleado>> ObtenerListaEmpleados();

    @GET("api/hora/listar/{ddMMYYY}/1/token")//borrar el 1 y token del empleado que sea
    Call<ArrayList<Horario>> ObtenerListaHorarios(@Path("ddMMYYY") String dia);

    @GET("api/reserva/crear/{ddMMyyyyhhmmss}/{servicio}/tokenempleado")//token del empleado que sea
    Call<ReservaEmpleadoConfirmada> ReservarEmpleado(@Path("ddMMyyyyhhmmss")String f, @Path("servicio")String s);

    @POST("api/session/login")
    Call<RespuestaSesion> ObtenerToken(@Body TokenRequest tokenRequest);

    @GET("api/reserva/obtener/token")
    Call<ArrayList<Reserva>> ObtenerReservas(/*@Path("token")String token*/);

    @GET("api/reserva/cancelar/{idReserva}/token")
    Call<Status> CancelarReserva(@Path("idReserva") String id/*@Path("token")String token*/);
}
