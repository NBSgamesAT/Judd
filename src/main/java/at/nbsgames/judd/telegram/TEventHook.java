package at.nbsgames.judd.telegram;

import at.nbsgames.judd.telegram.objects.TMessage;

public interface TEventHook {

    void messageReceived(TMessage message);

}
