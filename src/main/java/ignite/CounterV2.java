package ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CachePeekMode;
import org.apache.ignite.cluster.ClusterGroup;
import org.apache.ignite.internal.util.lang.GridTuple4;

import javax.cache.Cache;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

public class CounterV2 {

    public static void main(String[] args) throws IgniteException {
        Ignition.setClientMode(true);
        try (Ignite ignite = Ignition.start("cache.xml")) {
            ClusterGroup clusterGroup = ignite.cluster().forCacheNodes("productCache");
            GridTuple4 result = ignite.compute(ignite.cluster().forCacheNodes("productCache")).call(
                    () -> {
                        final long start = System.nanoTime();
                        final AtomicInteger categoryOne = new AtomicInteger();
                        final AtomicInteger categoryTwo = new AtomicInteger();
                        final AtomicInteger categoryThree = new AtomicInteger();
                        try (IgniteCache<String, Product> cache = ignite.getOrCreateCache("productCache")) {
                            final BigDecimal one = new BigDecimal("49.99");
                            final BigDecimal two = new BigDecimal("99.99");
                            Iterable<Cache.Entry<String, Product>> values = cache.localEntries(CachePeekMode.ALL);
                            values.forEach(p -> {
                                if (p.getValue().getListPrice() == null) {
                                    //do nothing
                                } else if (p.getValue().getListPrice().compareTo(one) < 0) {
                                    categoryOne.incrementAndGet();
                                } else if (p.getValue().getListPrice().compareTo(two) < 0) {
                                    categoryTwo.incrementAndGet();
                                } else {
                                    categoryThree.incrementAndGet();
                                }
                            });
                        }
                        return new GridTuple4(categoryOne.get(), categoryTwo.get(), categoryThree.get(), System.nanoTime() - start);
                    }
            );


            System.out.println("Results: " + result);
        }
    }

    public static class Result {
    }
}
