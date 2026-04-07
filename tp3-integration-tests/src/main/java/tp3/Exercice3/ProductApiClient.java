package tp3.Exercice3;


public interface ProductApiClient {
    Product getProduct(String productId) throws ApiException;
}