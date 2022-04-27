package isel.leic.mpd.lazy4;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static isel.leirt.mpd.lazy4.PipeIterable.*;

public class CombTests {

    private class Pair<V1,V2> {
        public final V1 v1;
        public final V2 v2;

        Pair(V1 v1, V2 v2) {
            this.v1 = v1;
            this.v2 = v2;
        }

        @Override
        public String toString() {
            return "(" + v1 + "," + v2 + ")";
        }
    }

    private List<Pair<Integer,Integer>> comb2GenEager(int start, int end)  {
        List<Pair<Integer,Integer>> combs = new ArrayList<>();

        for(int i= start; i < end; ++i) {
            for(int j = i +1 ; j <= end; j++) {
                combs.add(new Pair<>(i, j));
            }
        }
        return combs;
    }

    private List<Pair<Integer,Integer>>
    comb2GenLazy(int start, int end)  {

        var res =
            range(start,end)
            .flatMap(
                 n1 -> range(n1+1, end)
                       .map(n2 -> new Pair<>(n1, n2))
            );

        for(Pair value : res.toIterable())
            System.out.println(value);
        return null;
    }

    @Test
    public void comb2Imperative() {
        var combs = comb2GenEager(1,10);

        combs.forEach(System.out::println);

    }

    @Test
    public void comb2Lazy() {
        comb2GenLazy(1,10);
    }
}
