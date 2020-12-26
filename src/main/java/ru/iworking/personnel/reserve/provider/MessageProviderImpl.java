package ru.iworking.personnel.reserve.provider;

import org.springframework.stereotype.Component;
import ru.iworking.personnel.reserve.MessageAction;
import ru.iworking.personnel.reserve.MessageProvider;
import ru.iworking.personnel.reserve.component.Messager;

@Component
public class MessageProviderImpl implements MessageProvider {

    @Override
    public void sendMessage(String text) {
        Messager.getInstance().sendMessage(text);
    }

    @Override
    public void sendMessageWithAction(String text, MessageAction messageAction) {
        Messager.getInstance().sendMessageWithAction(text, messageAction);
    }

}
