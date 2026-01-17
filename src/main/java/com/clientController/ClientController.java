package com.clientController;

import com.clientProxy.*;

public class ClientController {

    private final ServiceProxy proxy;
    private String token;
    private String emailUsuario;

    public ClientController() {
        this.proxy = new ServiceProxy("http://localhost:8080");
    }

    // LOGIN
    public boolean login(String email, String password) {
        String response = proxy.login(email, password);

        if (response.contains("token") && !response.contains("error")) {
            this.token = extraerValorJson(response, "token");
            this.emailUsuario = email;
            return true;
        }
        return false;
    }

    // LOGOUT
    public boolean logout() {
        if (token == null) return false;

        proxy.logout(token);
        this.token = null;
        this.emailUsuario = null;
        return true;
    }

    // CREAR CONTENEDOR
    public String crearContenedor(String id, String ubicacion, int capacidad, String codigoPostal) {
        return proxy.crearContenedor(id, ubicacion, capacidad, codigoPostal, token);
    }

    // CONSULTAR ESTADO CONTENEDOR
    public String consultarEstadoContenedor(String id, String fechaInicio, String fechaFin) {
        return proxy.consultarEstadoContenedor(id, fechaInicio, fechaFin, token);
    }

    // CONSULTAR CAPACIDAD PLANTA
    public String consultarCapacidadPlanta(String idPlanta, String fecha) {
        return proxy.consultarCapacidadPlanta(idPlanta, fecha, token);
    }

    // ASIGNAR CONTENEDORES A PLANTA
    public String asignarContenedores(String idPlanta, String[] contenedorIds) {
        StringBuilder json = new StringBuilder();
        json.append("{\"nombre\":\"").append(idPlanta).append("\",\"listaContenedores\":[");

        for (int i = 0; i < contenedorIds.length; i++) {
            json.append("{\"id\":\"").append(contenedorIds[i]).append("\",\"nivelLlenado\":50}");
            if (i < contenedorIds.length - 1) {
                json.append(",");
            }
        }
        json.append("]}");

        return proxy.asignarContenedores(json.toString(), token);
    }

    // GETTERS
    public String getToken() {
        return token;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public boolean isLoggedIn() {
        return token != null;
    }

    // UTILIDAD: Extraer valor de JSON simple
    private String extraerValorJson(String json, String clave) {
        try {
            String buscar = "\"" + clave + "\":\"";
            int inicio = json.indexOf(buscar) + buscar.length();
            int fin = json.indexOf("\"", inicio);
            return json.substring(inicio, fin);
        } catch (Exception e) {
            return null;
        }
    }
}