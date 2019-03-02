package ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;

import javax.cache.configuration.FactoryBuilder;

public class Loader {

    public static void main(String[] args) {
        Ignition.setClientMode(true);

        try (final Ignite ignite = Ignition.start("cache.xml");) {
            CacheConfiguration<String, Product> cacheCfg = new CacheConfiguration<>("productCache");

            cacheCfg.setCacheStoreFactory(FactoryBuilder.factoryOf(CacheCSVStore.class));

            try (IgniteCache<String, Product> cache = ignite.getOrCreateCache(cacheCfg)) {
                cache.loadCache(null);
            }
        }
    }

}
