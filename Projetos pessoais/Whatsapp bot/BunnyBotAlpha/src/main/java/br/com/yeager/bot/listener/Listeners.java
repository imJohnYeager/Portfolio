package br.com.yeager.bot.listener;

import it.auties.whatsapp.listener.RegisterListener;
import it.auties.whatsapp.api.Whatsapp;
import it.auties.whatsapp.listener.Listener;
import it.auties.whatsapp.model.info.ChatMessageInfo;
import it.auties.whatsapp.model.info.MessageInfo;
import it.auties.whatsapp.model.message.standard.StickerMessage;
import it.auties.whatsapp.model.message.standard.TextMessage;

@RegisterListener // Automatically registers this listener
public record Listeners(Whatsapp api) implements Listener { // A non-null whatsapp instance is injected
    @Override
    public void onLoggedIn() {
        System.out.println("Hello :)");
    }

    public void onMessage(Whatsapp api, MessageInfo info) {
        if (!(info.message().content() instanceof TextMessage textMessage)){
            return;
        }
        if(info.message().stickerMessage().isPresent()) {
            StickerMessage stickerMessage = info.message().stickerMessage().get();
            // Agora você pode trabalhar com stickerMessage
            // Por exemplo, pode fazer algo com stickerMessage aqui
        }

        if(textMessage.text().toLowerCase().contains("ping")) {
            api.sendMessage(info.senderJid(), "Pong!");
        }
    }
}