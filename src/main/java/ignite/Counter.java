package ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CachePeekMode;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.internal.util.lang.GridTuple4;

import javax.cache.Cache;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Counter {

    public static void main(String[] args) throws IgniteException {
        Ignition.setClientMode(true);
        try (Ignite ignite = Ignition.start("cache.xml")) {
            UUID localNodeId = ignite.cluster().localNode().id();
            Collection<ClusterNode> nodes = ignite.cluster().nodes().stream()
                    .filter(n -> !n.id().equals(localNodeId))
                    .collect(Collectors.toList());

            List<GridTuple4> results = new ArrayList<>();
            for (ClusterNode node : nodes) {
                GridTuple4 result = ignite.compute(ignite.cluster().forNode(node)).call(
                        () -> {
                            final long start = System.nanoTime();
                            final AtomicInteger categoryOne = new AtomicInteger();
                            final AtomicInteger categoryTwo = new AtomicInteger();
                            final AtomicInteger categoryThree = new AtomicInteger();
                            try (IgniteCache<String, Product> cache = ignite.getOrCreateCache("productCache")) {
                                final BigDecimal one = new BigDecimal("49.99");
                                final BigDecimal two = new BigDecimal("99.99");
                                Iterable<Cache.Entry<String, Product>> values = cache.localEntries(CachePeekMode.PRIMARY);
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
                results.add(result);
            }
            results.stream().forEach(System.out::println);
            System.out.println("Results: " + results.size());
        }
    }

    public static class Result {
    }
}
