package com.scheduler.genericscheduler.Controladores;

import com.scheduler.genericscheduler.Modelos.Empleado;
import com.scheduler.genericscheduler.Modelos.Horario;
import com.scheduler.genericscheduler.Modelos.Reserva;
import com.scheduler.genericscheduler.Modelos.ReservaEmpleadoConfirmada;
import com.scheduler.genericscheduler.Modelos.RespuestaSesion;
import com.scheduler.genericscheduler.Modelos.Servicio;
import com.scheduler.genericscheduler.Modelos.Status;
import com.scheduler.genericscheduler.Modelos.TokenRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface InterfaceServicios {

    @GET("api/empleado/listar/1/{token}")//funcion del cliente para listar empleados del comercio
    Call<ArrayList<Empleado>> ObtenerListaEmpleados(@Path("token")String token);

    @GET("api/servicio/listar/{tokenempleado}")//funcion del empleado para listar sus servicios
    Call<ArrayList<Servicio>> ObtenerListaServicios(@Path("tokenempleado")String tokenempleado);

    @GET("api/servicio/listar/{id}/{tokencliente}")//funcion del cliente para listar los servicios del empleado seleccionado
    Call<ArrayList<Servicio>> ObtenerListaServiciosCliente(@Path("id")String id, @Path("tokencliente")String tokencliente);

    @GET("api/hora/listar/{ddMMYYY}/{idEmpleado}/{tokencliente}")//funcion del cliente para listar horarios del empleado seleccionado
    Call<ArrayList<Horario>> ObtenerListaHorarios(@Path("ddMMYYY") String dia,@Path("idEmpleado")String idEmpleado,@Path("tokencliente")String tokenCliente);

    @GET("api/hora/listar/{ddMMYYY}/{tokenempleado}")//funcion del empleado para listar sus horarios
    Call<ArrayList<Horario>> ObtenerListaHorariosEmpleado(@Path("ddMMYYY") String dia,@Path("tokenempleado")String tokenEmpleado);

    @GET("api/reserva/crear/{ddMMyyyyhhmmss}/{idEmpleado}/{idServicio}/{tokenCliente}")//funcion del cliente para reservar
    Call<ReservaEmpleadoConfirmada> ReservarEmpleado(@Path("ddMMyyyyhhmmss")String f, @Path("idEmpleado")String idEmpleado,@Path("idServicio")String idServicio,@Path("tokenCliente")String tokencliente);

    @GET("api/reserva/crear/{ddMMyyyyhhmmss}/{idServicio}/{tokenEmpleado}")//funcion del empleado para reservar
    Call<ReservaEmpleadoConfirmada> ReservarEmpleadoEmpleado(@Path("ddMMyyyyhhmmss")String f, @Path("idServicio")String idServicio,@Path("tokenEmpleado")String tokenempleado);

    @POST("api/session/login")
    Call<RespuestaSesion> ObtenerToken(@Body TokenRequest tokenRequest);

    @GET("api/reserva/obtener/{token}")
    Call<ArrayList<Reserva>> ObtenerReservas(@Path("token")String token);

    @GET("api/reserva/obtener/empleado/{token}")
    Call<ArrayList<Reserva>> ObtenerReservasEmpleado(@Path("token")String token);

    @GET("api/reserva/cancelar/{idReserva}/{token}")
    Call<Status> CancelarReserva(@Path("idReserva") String id,@Path("token")String token);

}
