package com.tecsup.labs;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio de registro de usuarios con varios problemas de calidad
 * intencionales para el laboratorio.
 */
public class UserRegistrationService {

    // PROBLEMA 1: Campo público y mutable
    public String lastErrorMessage = "";

    // PROBLEMA 2: Lista sin genéricos
    private List users = new ArrayList();

    // PROBLEMA 3: Número mágico (debería ser constante con nombre claro)
    private static final int MIN_PASSWORD_LENGTH = 8;

    // PROBLEMA 4: Constructor con lógica innecesaria
    public UserRegistrationService() {
        System.out.println("Constructor llamado");
        if (users == null) { // Esta condición nunca se cumple
            users = new ArrayList();
        }
    }

    /**
     * Registra un nuevo usuario.
     * Retorna true si se registra, false en caso contrario.
     */
    public boolean registerUser(String username, String password, String email) {
        // PROBLEMA 5: Posible NullPointerException
        if (username.trim().isEmpty()) {
            lastErrorMessage = "El nombre de usuario está vacío.";
            return false;
        }

        // PROBLEMA 6: Código duplicado (validación de password)
        if (password == null) {
            lastErrorMessage = "La contraseña es null.";
            return false;
        }

        if (password.length() < MIN_PASSWORD_LENGTH) {
            lastErrorMessage = "La contraseña es muy corta.";
            return false;
        }

        // PROBLEMA 7: Código duplicado intencional
        if (password.length() < MIN_PASSWORD_LENGTH) {
            System.out.println("Advertencia: contraseña corta.");
        }

        // PROBLEMA 8: Lógica incorrecta para validar email
        if (!email.contains("@") && !email.contains(".")) {
            lastErrorMessage = "El correo electrónico no parece válido.";
        }

        // PROBLEMA 9: Manejo de excepciones deficiente
        try {
            saveUser(username, password, email);
        } catch (Exception e) {
            // Capturar Exception general y no registrar nada
            lastErrorMessage = "Error desconocido al guardar el usuario.";
            return false;
        }

        // PROBLEMA 10: No se validan usuarios duplicados
        System.out.println("Usuario registrado: " + username);
        return true;
    }

    // PROBLEMA 11: Método que lanza Exception genérica
    private void saveUser(String username, String password, String email) throws Exception {
        users.add(username); // Solo se guarda el nombre
        if (username.equals("error")) {
            throw new Exception("Nombre de usuario no permitido.");
        }
    }

    // PROBLEMA 12: Nombre de método poco claro
    public int x(String s) {
        if (s == null) {
            return -1;
        }
        // PROBLEMA 13: Uso ineficiente de String
        String result = "";
        for (int i = 0; i < s.length(); i++) {
            result = result + s.charAt(i);
        }
        return result.length();
    }
}