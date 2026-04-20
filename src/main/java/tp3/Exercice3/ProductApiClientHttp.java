package tp3.Exercice3;

import java.io.*;
import java.net.*;

/**
 * Implémentation HTTP réelle de ProductApiClient.
 * Utilisée dans les tests d'intégration avec un MockServer Testcontainers.
 *
 * Elle appelle GET http://{host}:{port}/products/{productId}
 * et attend une réponse JSON du type :
 *   {"id":"P001","name":"Laptop","price":999.99,"category":"Electronics"}
 *
 * Si le serveur répond 404, retourne null.
 * Si le serveur répond autre chose que 200/404, lève une ApiException.
 */
public class ProductApiClientHttp implements ProductApiClient {

    private final String baseUrl; // ex: "http://localhost:1080"

    public ProductApiClientHttp(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public Product getProduct(String productId) throws ApiException {
        try {
            URL url = new URL(baseUrl + "/products/" + productId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5_000);
            conn.setReadTimeout(5_000);

            int status = conn.getResponseCode();

            if (status == 404) {
                return null; // produit non trouvé
            }
            if (status != 200) {
                throw new ApiException("API returned HTTP " + status);
            }

            // Lire le corps de la réponse
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) sb.append(line);
                return parseProduct(sb.toString());
            }

        } catch (ApiException e) {
            throw e;
        } catch (IOException e) {
            throw new ApiException("API connection failed: " + e.getMessage(), e);
        }
    }

    /**
     * Parser JSON minimal sans dépendance externe.
     * Format attendu : {"id":"...","name":"...","price":...,"category":"..."}
     */
    private Product parseProduct(String json) throws ApiException {
        try {
            String id       = extractString(json, "id");
            String name     = extractString(json, "name");
            double price    = extractDouble(json, "price");
            String category = extractString(json, "category");
            return new Product(id, name, price, category);
        } catch (Exception e) {
            throw new ApiException("Format JSON incompatible: " + e.getMessage());
        }
    }

    private String extractString(String json, String key) {
        // cherche "key":"value"
        String pattern = "\"" + key + "\":\"";
        int start = json.indexOf(pattern) + pattern.length();
        int end   = json.indexOf("\"", start);
        return json.substring(start, end);
    }

    private double extractDouble(String json, String key) {
        String pattern = "\"" + key + "\":";
        int start = json.indexOf(pattern) + pattern.length();
        int end   = json.indexOf(",", start);
        if (end == -1) end = json.indexOf("}", start);
        return Double.parseDouble(json.substring(start, end).trim());
    }
}
