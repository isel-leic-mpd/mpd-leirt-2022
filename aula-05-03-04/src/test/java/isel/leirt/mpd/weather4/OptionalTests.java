package isel.leirt.mpd.weather4;

import org.junit.jupiter.api.Test;

import java.util.Optional;

public class OptionalTests {

    @Test
    public void optional_tests() {

        Optional<String> optionalName = Optional.empty();

        System.out.println(optionalName.get());
    }
}
