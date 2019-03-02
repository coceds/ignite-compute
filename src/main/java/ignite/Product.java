package ignite;

import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class Product implements Serializable {

    private String productId;
    private String sku;
    private String nameTitle;
    private String description;
    private BigDecimal listPrice;
    private String salePrice;
    private String category;
    private String categoryTree;
    private String averageProductRating;
    private String productUrl;
    private String productImageUrls;
    private String brand;
    private String totalNumberReviews;
    private String reviews;

    public Product() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getNameTitle() {
        return nameTitle;
    }

    public void setNameTitle(String nameTitle) {
        this.nameTitle = nameTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryTree() {
        return categoryTree;
    }

    public void setCategoryTree(String categoryTree) {
        this.categoryTree = categoryTree;
    }

    public String getAverageProductRating() {
        return averageProductRating;
    }

    public void setAverageProductRating(String averageProductRating) {
        this.averageProductRating = averageProductRating;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getProductImageUrls() {
        return productImageUrls;
    }

    public void setProductImageUrls(String productImageUrls) {
        this.productImageUrls = productImageUrls;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getTotalNumberReviews() {
        return totalNumberReviews;
    }

    public void setTotalNumberReviews(String totalNumberReviews) {
        this.totalNumberReviews = totalNumberReviews;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public static Product valueOf(List<String> values) {
        Product product = new Product();
        product.productId = values.get(0);
        product.sku = values.get(1);
        product.nameTitle = values.get(2);
        product.description = values.get(3);
        String listPriceStr = values.get(4);

        try {
            product.listPrice = StringUtils.isEmpty(listPriceStr) ? null : new BigDecimal(listPriceStr);
        } catch (Exception e) {
            //ignore
        }
        product.salePrice = values.get(5);
        product.category = values.get(6);
        product.categoryTree = values.get(7);
        product.averageProductRating = values.get(8);
        product.productUrl = values.get(9);
        product.productImageUrls = values.get(10);
        product.brand = values.get(11);
        product.totalNumberReviews = values.get(12);
        product.reviews = values.get(13);
        return product;
    }
}
