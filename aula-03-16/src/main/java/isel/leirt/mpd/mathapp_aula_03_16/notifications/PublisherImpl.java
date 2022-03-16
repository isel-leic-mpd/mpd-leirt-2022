package isel.leirt.mpd.mathapp_aula_03_16.notifications;

import java.util.ArrayList;
import java.util.List;

public class PublisherImpl implements Publisher {
    private final List<Subscriber> subscribers;
    private final Publisher ref;

    public PublisherImpl(Publisher ref) {
        this.ref = ref;
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
            s.valueChanged(ref);
    }
}
