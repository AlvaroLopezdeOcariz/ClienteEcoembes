package com.clientProxy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ServiceProxy {

    private final String baseUrl;

    public ServiceProxy(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    // LOGIN
    public String login(String email, String password) {
        return postRequestForm("/auth/login", "email=" + email + "&password=" + password);
    }

    // LOGOUT
    public String logout(String token) {
        return postRequestForm("/auth/logout", "token=" + token);
    }

    // CREAR CONTENEDOR
    public String crearContenedor(String id, String ubicacion, int capacidad, String codigoPostal, String token) {
        String json = "{\"id\":\"" + id + "\",\"ubicacion\":\"" + ubicacion + "\",\"capacidad\":" + capacidad + ",\"codigoPostal\":\"" + codigoPostal + "\"}";
        return postRequest("/contenedores?token=" + token, json);
    }

    // CONSULTAR ESTADO CONTENEDOR (por ID y rango de fechas)
    public String consultarEstadoContenedor(String id, String fechaInicio, String fechaFin, String token) {
        String endpoint = "/contenedores/" + id + "/status?inicio=" + fechaInicio + "&fin=" + fechaFin + "&token=" + token;
        return getRequest(endpoint);
    }

    // CONSULTAR CAPACIDAD PLANTA
    public String consultarCapacidadPlanta(String idPlanta, String fecha, String token) {
        String endpoint = "/plantas/capacidad?idPlanta=" + idPlanta + "&fecha=" + fecha + "&token=" + token;
        return getRequest(endpoint);
    }

    // ASIGNAR CONTENEDORES A PLANTA
    public String asignarContenedores(String jsonAsignacion, String token) {
        return postRequest("/asignaciones?token=" + token, jsonAsignacion);
    }

    // METODO GET GENERICO
    private String getRequest(String endpoint) {
        try {
            URL url = new URL(baseUrl + endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();
            BufferedReader reader;
            if (responseCode >= 200 && responseCode < 300) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return response.toString();

        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    // METODO POST CON JSON
    private String postRequest(String endpoint, String jsonBody) {
        try {
            URL url = new URL(baseUrl + endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            BufferedReader reader;
            if (responseCode >= 200 && responseCode < 300) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return response.toString();

        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    // METODO POST CON FORM DATA
    private String postRequestForm(String endpoint, String formData) {
        try {
            URL url = new URL(baseUrl + endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = formData.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            BufferedReader reader;
            if (responseCode >= 200 && responseCode < 300) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return response.toString();

        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }
}