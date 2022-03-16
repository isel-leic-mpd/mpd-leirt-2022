package isel.leirt.mpd.mathapp_aula_03_15.notifications;

import java.util.ArrayList;
import java.util.List;

public class PublisherImpl implements Publisher {
    private List<Subscriber> subscribers;

    public PublisherImpl() {
        subscribers = new ArrayList<>();
    }
    @Override
    public void subscribe(Subscriber s) {
        subscribers.add(s);
    }

    @Override
    public void unsubscribe(Subscriber s) {
        subscribers.remove(s);
    }

    @Override
    public void notifyChange() {
        for(Subscriber s : subscribers)
            s.valueChanged();
    }
}
