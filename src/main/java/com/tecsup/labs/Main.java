package com.tecsup.labs;

public class Main {

    public static void main(String[] args) {
        UserRegistrationService service = new UserRegistrationService();

        // Casos de prueba
        service.registerUser("juan", "123", "juan@correo");
        System.out.println(service.lastErrorMessage);

        service.registerUser(null, "12345678", "correo-sin-arroba");
        System.out.println(service.lastErrorMessage);

        service.registerUser("error", "12345678", "error@correo.com");
        System.out.println(service.lastErrorMessage);
    }
}