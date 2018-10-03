package at.nbsgames.telegramsplatoon2maprotation.telegram;

import at.nbsgames.telegramsplatoon2maprotation.telegram.objects.TMessage;

public interface TEventHook {

    void messageReceived(TMessage message);

}
