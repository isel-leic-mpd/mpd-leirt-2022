package isel.leirt.mpd.extensible_apps.exceptions;

public class ExpressionFactoryException extends RuntimeException {
    public ExpressionFactoryException(String msg)  {
        super(msg);
    }

    public ExpressionFactoryException(Exception  inner)  {
        super(inner);
    }
}
