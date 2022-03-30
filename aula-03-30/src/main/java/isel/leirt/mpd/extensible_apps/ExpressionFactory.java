package isel.leirt.mpd.extensible_apps;

import isel.leirt.mpd.aula_03_23.reflection_intro.ReflexUtils;
import isel.leirt.mpd.extensible_apps.exceptions.DuplicatedOperException;
import isel.leirt.mpd.extensible_apps.exceptions.ExpressionFactoryException;
import isel.leirt.mpd.mathapp_aula_03_16.expressions.Expr;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ExpressionFactory {
    // the package where must be the .class for new operations
    private static final String DYNAMIC_OPERS_PKG =
            "isel.leirt.mpd.extensible_apps.dynamic";

    // the various parts of an operation description of a line of the configuration file
    private static final int DESCRIPTION_PARTS  = 3;
    private static final int NAME_PART = 0;
    private static final int SYMBOL_PART = 1;
    private static final int ARITY_PART = 2;

    // to disable class instantiation
    private ExpressionFactory() {
    }

    // factory for an operation descriptor given the line description
    private static OpDescriptor fromDescription(String opStr) {
        String[] parts = opStr.split(",");
        if (parts.length != DESCRIPTION_PARTS)
            throw new IllegalStateException("Bad operation registration!");

        for(int i=0; i < parts.length; ++i)
            parts[i] = parts[i].trim();

        int arity = Integer.parseInt(parts[ARITY_PART]);
        OpDescriptor opd = new  OpDescriptor(parts[NAME_PART], parts[SYMBOL_PART], arity);
        return opd;
    }

    // inner class to represent a new operation descriptor
    private static class OpDescriptor {
        public final String name;
        public final String opSymb;
        public final int arity;


        private  OpDescriptor(String name, String opSymb, int arity) {
            this.name = name;
            this.opSymb = opSymb;
            this.arity = arity;
        }

        private void show() {
            System.out.printf("Name: %s; Symbol: %s;  Arity: %d\n", name, opSymb, arity);
        }
    }

    /**
     * Retrieve API-KEY from resources
     * @return BufferedReader
     */
    public static BufferedReader getRegistReader() throws IOException {
        URL registFile =
                ClassLoader.getSystemResource("expression_types.txt");
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(registFile.openStream()));
        return reader;
    }

    /**
     * Load the  configuration file resource
     */
    private static void loadOpRegistrations() {
        try(BufferedReader reader = getRegistReader()) {
            String line;
            while((line= reader.readLine()) != null) {
                if (!line.startsWith("#")) { // ignore comments
                    OpDescriptor opd = fromDescription(line);
                    if (operations.containsKey(opd.name))
                        throw new DuplicatedOperException();
                    operations.put(opd.name, opd);
                }
            }
        }
        catch(IOException e) {
            throw new ExpressionFactoryException("I/O error loading operation registrations!");
        }
        catch(Exception e) {
            throw new ExpressionFactoryException(e);
        }
    }

    // static constructor for loading the configuration resource file into the hashmap
    static {
        operations = new HashMap<>();
        creator = new FactoryByName(DYNAMIC_OPERS_PKG);
        loadOpRegistrations();
    }


    // the map of (operation name, operation descriptor) pairs
    private static final Map<String,OpDescriptor> operations;

    // the creator used to create the Expr implementations
    private static final FactoryByName creator;

    /**
     * This is the factory method for retuning a new Expr given is name and
     * construction arguments
     * @param name the operation name
     * @param args the expressions passed to constructor (the size must match the operation arity)
     * @return a new expression of the given type
     */
    public static Expr createFrom(String name, Expr... args)  {
        try {
            OpDescriptor opd = operations.get(name);

            if (opd == null)
                throw new ExpressionFactoryException("Inexistent operation name");

            // check if arity matches the args length
            if (opd.arity != args.length)
                throw new ExpressionFactoryException("Try create an expression with an invalid number of arguments");

            // get the operation class representant
            Class<?> expClass = creator.getClassOfName(opd.name);

            // check if the operation class implements Expr
            if (!ReflexUtils.clsImplements(expClass, Expr.class))
                throw new ExpressionFactoryException("Operation class is not an Expr");

            // create the constructor parameters
            Class<?>[] parms = new Class[args.length];
            Arrays.fill(parms, Expr.class);
            return (Expr) creator.createInstance(expClass, parms, args);
        }
        catch(ClassNotFoundException e) {
            throw new ExpressionFactoryException(e);
        }

    }
}
