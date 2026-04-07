package tp3.Exercice3;

public class ProductService {
    private final ProductApiClient productApiClient;
    
    public ProductService(ProductApiClient productApiClient) {
        this.productApiClient = productApiClient;
    }
    
    public Product getProduct(String productId) throws ApiException {
        if (productId == null || productId.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty");
        }
        
        Product product = productApiClient.getProduct(productId);
        
        if (product == null) {
            throw new ApiException("Product not found from API");
        }
        
        return product;
    }
}