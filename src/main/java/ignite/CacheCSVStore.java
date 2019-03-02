package ignite;

import de.siegmar.fastcsv.reader.CsvContainer;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;
import org.apache.ignite.cache.store.CacheStoreAdapter;
import org.apache.ignite.lang.IgniteBiInClosure;
import org.springframework.core.io.ClassPathResource;

import javax.cache.Cache;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

public class CacheCSVStore extends CacheStoreAdapter<String, Product> {

    public CacheCSVStore() {
    }

    @Override
    public void loadCache(IgniteBiInClosure<String, Product> clo, Object... args) {
        List<Product> products = loadProducts();
        products.stream()
                .forEach(p -> clo.apply(p.getProductId(), p));
    }

    @Override
    public Product load(String productId) throws CacheLoaderException {
        throw new IllegalArgumentException("not supported");
    }

    @Override
    public void write(Cache.Entry<? extends String, ? extends Product> entry) throws CacheWriterException {
        throw new IllegalArgumentException("not supported");
    }

    @Override
    public void delete(Object o) throws CacheWriterException {
        throw new IllegalArgumentException("not supported");
    }

    public static List<Product> loadProducts() {
        try (
                InputStream inputStream = new ClassPathResource("data.csv").getInputStream();
                Reader targetReader = new InputStreamReader(inputStream);
        ) {
            CsvReader csvReader = new CsvReader();
            csvReader.setContainsHeader(true);
            CsvContainer csv = csvReader.read(targetReader);
            List<CsvRow> rows = csv.getRows();
            return rows.stream()
                    .map(r -> Product.valueOf(r.getFields()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

