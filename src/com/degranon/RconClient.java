package com.degranon;

import net.kronos.rkon.core.Rcon;
import net.kronos.rkon.core.ex.AuthenticationException;

import java.io.IOException;
import java.security.InvalidParameterException;

public class RconClient {
    private static Rcon rcon;

    public static void authenticate(String host, Integer port, String password) {
        try {
            rcon = new Rcon(host, port, password.getBytes());
        } catch (AuthenticationException | IOException e) {
            throw new InvalidParameterException("Authentication failed!");
        }
    }

    public static String send(String command) {
        try {
            rcon.command(command);
            return rcon.command("skip");
        } catch (IOException e) {
            return "Unable to execute the command.";
        }
    }
}
