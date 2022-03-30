package isel.leirt.mpd.extensible_apps.exceptions;

public class ObjectCreationException extends RuntimeException {
    public ObjectCreationException(String msg)  {
        super(msg);
    }

    public ObjectCreationException(Exception  inner)  {
        super(inner);
    }
}
