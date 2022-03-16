package isel.leirt.mpd.mathapp_aula_03_16.exceptions;

public class CircularDependencyException extends MathAppException {
    public CircularDependencyException() {
        super("Circular dependency between variables");
    }
}