package com.tecsup.labs;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servicio de registro de usuarios.
 * Maneja el registro y validación de nuevos usuarios.
 */
public class UserRegistrationService {

    private static final Logger LOGGER = Logger.getLogger(UserRegistrationService.class.getName());
    
    private static final int MINIMUM_PASSWORD_LENGTH = 8;
    private static final int MINIMUM_USERNAME_LENGTH = 3;
    private static final int MAXIMUM_USERNAME_LENGTH = 20;
    
    private String lastErrorMessage = "";
    private List<String> users = new ArrayList<>();

    public String getLastErrorMessage() {
        return lastErrorMessage;
    }

    private void setLastErrorMessage(String lastErrorMessage) {
        this.lastErrorMessage = lastErrorMessage;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * 
     * @param username Nombre de usuario (no null, entre 3-20 caracteres)
     * @param password Contraseña (no null, mínimo 8 caracteres)
     * @param email Correo electrónico (formato válido)
     * @return true si el registro es exitoso, false en caso contrario
     */
    public boolean registerUser(String username, String password, String email) {
        // Validar username
        if (!isValidUsername(username)) {
            setLastErrorMessage("El nombre de usuario debe tener entre " + 
                MINIMUM_USERNAME_LENGTH + " y " + MAXIMUM_USERNAME_LENGTH + " caracteres.");
            return false;
        }

        // Validar password
        if (!isValidPassword(password)) {
            setLastErrorMessage("La contraseña debe tener al menos " + 
                MINIMUM_PASSWORD_LENGTH + " caracteres.");
            return false;
        }

        // Validar email
        if (!isValidEmail(email)) {
            setLastErrorMessage("El correo electrónico no tiene un formato válido.");
            return false;
        }

        // Verificar si el usuario ya existe
        if (isUserRegistered(username)) {
            setLastErrorMessage("El usuario '" + username + "' ya está registrado.");
            return false;
        }

        // Guardar usuario
        try {
            saveUser(username, password, email);
            LOGGER.log(Level.INFO, "Usuario registrado exitosamente: {0}", username);
            return true;
        } catch (IllegalArgumentException e) {
            setLastErrorMessage("Error de validación: " + e.getMessage());
            return false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al guardar usuario: " + username, e);
            setLastErrorMessage("Error interno al guardar el usuario.");
            return false;
        }
    }

    private boolean isValidUsername(String username) {
        if (username == null) {
            return false;
        }
        String trimmed = username.trim();
        return trimmed.length() >= MINIMUM_USERNAME_LENGTH && 
               trimmed.length() <= MAXIMUM_USERNAME_LENGTH;
    }

    private boolean isValidPassword(String password) {
        return password != null && password.length() >= MINIMUM_PASSWORD_LENGTH;
    }

    private boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        // Validación básica de email
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    private boolean isUserRegistered(String username) {
        return users.stream().anyMatch(u -> u.equalsIgnoreCase(username.trim()));
    }

    private void saveUser(String username, String password, String email) throws Exception {
        String trimmedUsername = username.trim();
        
        if (trimmedUsername.equalsIgnoreCase("error")) {
            throw new IllegalArgumentException("El nombre de usuario contiene palabras reservadas.");
        }
        
        users.add(trimmedUsername);
        LOGGER.log(Level.INFO, "Usuario guardado en la base de datos: {0}", trimmedUsername);
    }

    /**
     * Obtiene la longitud del string invertido.
     * 
     * @param input String a procesar
     * @return Longitud del string invertido, -1 si el input es null
     */
    public int getReversedStringLength(String input) {
        if (input == null) {
            return -1;
        }
        // Mejor uso de StringBuilder
        String reversed = new StringBuilder(input).reverse().toString();
        return reversed.length();
    }

    /**
     * Obtiene la lista de usuarios registrados.
     * 
     * @return Lista no modificable de usuarios
     */
    public List<String> getUsers() {
        return new ArrayList<>(users);
    }
}