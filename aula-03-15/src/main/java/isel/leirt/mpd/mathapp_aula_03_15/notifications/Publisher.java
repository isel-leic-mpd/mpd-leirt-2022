package isel.leirt.mpd.mathapp_aula_03_15.notifications;



public interface Publisher {
    void subscribe(Subscriber s);
    void unsubscribe(Subscriber s);
    void notifyChange();
}
