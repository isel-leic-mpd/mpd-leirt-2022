package isel.leirt.mpd.extensible_apps.exceptions;

public class DuplicatedOperException extends ExpressionFactoryException {
    public DuplicatedOperException()  {
        super("Operation already registered!");
    }
}
