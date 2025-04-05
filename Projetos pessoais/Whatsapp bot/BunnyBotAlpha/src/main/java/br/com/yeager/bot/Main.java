package br.com.yeager.bot;

import br.com.yeager.bot.listener.Listeners;
import it.auties.whatsapp.api.QrHandler;
import it.auties.whatsapp.api.Whatsapp;
import it.auties.whatsapp.listener.Listener;

public class Main {
    private static Listener Listeners;

    public static void main(String[] args) {
        Whatsapp.webBuilder() // Use the Web api
                .lastConnection().autodetectListeners(true) // Deserialize the last connection, or create a new one if it doesn't exist
                .unregistered(QrHandler.toTerminal()) // Print the QR to the terminal
                .addLoggedInListener(api -> System.out.printf("Connected: %s%n", api.store().privacySettings())) // Print a message when connected
                .addDisconnectedListener(reason -> System.out.printf("Disconnected: %s%n", reason)) // Print a message when disconnected
                .addNewChatMessageListener(message -> System.out.printf("New message: %s%n", message.toJson())) // Print a message when a new chat message arrives
                .connect() // Connect to Whatsapp asynchronously
                .join() // Await the result
                .awaitDisconnection(); // Wait
    }
}