package isel.leirt.mpd.sequences;

import org.junit.jupiter.api.Test;

import java.util.List;

public class SequenceTests {

    @Test
    public void empty_sequence() {
        Sequence<String> seq = Sequence.empty();

        System.out.println(
            seq.tryAdvance(__ -> {
                System.out.println("called!");
            })
        );


    }

    @Test
    public void empty_sequence_count() {
        Sequence<String> seq = Sequence.empty();


        System.out.println(
            seq.count()
        );

    }

    @Test
    public void sequence_flatMap() {

    }
}
