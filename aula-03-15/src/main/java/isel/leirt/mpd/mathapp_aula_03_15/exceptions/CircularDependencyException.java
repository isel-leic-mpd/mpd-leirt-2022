package isel.leirt.mpd.mathapp_aula_03_15.exceptions;

import isel.leirt.mpd.mathapp_aula_03_15.exceptions.MathAppException;

public class CircularDependencyException extends MathAppException {
    public CircularDependencyException() {
        super("Circular dependency between variables");
    }
}