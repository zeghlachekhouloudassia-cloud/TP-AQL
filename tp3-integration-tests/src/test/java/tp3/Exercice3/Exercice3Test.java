package tp3.Exercice3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class Exercice3Test {

    @Mock
    private ProductApiClient productApiClient;

    @InjectMocks
    private ProductService productService;

    private final Product validProduct = new Product("P001", "Laptop", 999.99, "Electronics");
    private final Product anotherProduct = new Product("P002", "Mouse", 25.50, "Accessories");

    // ─── récupération réussie ─────────────────────────────────────────────────
    @Test
    void testGetProduct_RecuperationReussie() throws ApiException {
        when(productApiClient.getProduct("P001")).thenReturn(validProduct);

        Product p = productService.getProduct("P001");

        assertNotNull(p);
        assertEquals("P001", p.getId());
        assertEquals("Laptop", p.getName());
        assertEquals(999.99, p.getPrice(), 0.001);
        assertEquals("Electronics", p.getCategory());
        verify(productApiClient, times(1)).getProduct("P001");
    }

    // ─── branche : produit non trouvé (null) ──────────────────────────────────
    @Test
    void testGetProduct_ProduitInexistant_LanceApiException() throws ApiException {
        when(productApiClient.getProduct("P999")).thenReturn(null);

        ApiException ex = assertThrows(ApiException.class,
            () -> productService.getProduct("P999"));

        assertTrue(ex.getMessage().contains("Product not found"));
        verify(productApiClient, times(1)).getProduct("P999");
    }

    // ─── branche : échec réseau ───────────────────────────────────────────────
    @Test
    void testGetProduct_EchecReseau_PropageApiException() throws ApiException {
        when(productApiClient.getProduct("P003"))
            .thenThrow(new ApiException("API connection failed"));

        ApiException ex = assertThrows(ApiException.class,
            () -> productService.getProduct("P003"));

        assertEquals("API connection failed", ex.getMessage());
        verify(productApiClient, times(1)).getProduct("P003");
    }

    // ─── branche : id invalide (null / vide / espaces) ────────────────────────
    /*@Test
    void testGetProduct_IdNull_LanceIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
            () -> productService.getProduct(null));
        verify(productApiClient, never()).getProduct(anyString());
    }

    @Test
    void testGetProduct_IdVide_LanceIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
            () -> productService.getProduct(""));
        verify(productApiClient, never()).getProduct(anyString());
    }

    @Test
    void testGetProduct_IdEspaces_LanceIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
            () -> productService.getProduct("   "));
        verify(productApiClient, never()).getProduct(anyString());
    }*/

    // ─── chaîne : exception puis succès ───────────────────────────────────────
    @Test
    void testStubbing_ChaineExceptionPuisSucces() throws ApiException {
        when(productApiClient.getProduct("P001"))
            .thenThrow(new ApiException("timeout"))
            .thenReturn(validProduct);

        assertThrows(ApiException.class, () -> productService.getProduct("P001"));

        Product p = productService.getProduct("P001");
        assertEquals("Laptop", p.getName());
        verify(productApiClient, times(2)).getProduct("P001");
    }

    // ─── plusieurs ids différents ─────────────────────────────────────────────
    @Test
    void testStubbing_DifferentesReponsesPourDifferentsIds() throws ApiException {
        when(productApiClient.getProduct("P001")).thenReturn(validProduct);
        when(productApiClient.getProduct("P002")).thenReturn(anotherProduct);
        when(productApiClient.getProduct("P999")).thenReturn(null);

        assertEquals("Laptop", productService.getProduct("P001").getName());
        assertEquals("Mouse", productService.getProduct("P002").getName());
        assertThrows(ApiException.class, () -> productService.getProduct("P999"));

        verify(productApiClient, times(1)).getProduct("P001");
        verify(productApiClient, times(1)).getProduct("P002");
        verify(productApiClient, times(1)).getProduct("P999");
    }

    // ─── stubbing : retourne toujours la même instance ───────────────────────
    @Test
    void testStubbing_RetourneToujoursLaMemeInstance() throws ApiException {
        when(productApiClient.getProduct("P001")).thenReturn(validProduct);

        assertSame(productService.getProduct("P001"),
                   productService.getProduct("P001"));
        verify(productApiClient, times(2)).getProduct("P001");
    }

    // ─── dernier stubbing écrase les précédents ───────────────────────────────
    @Test
    void testDernierStubbingEcraseLesPrecedents() throws ApiException {
        when(productApiClient.getProduct("P001")).thenReturn(anotherProduct);
        when(productApiClient.getProduct("P001")).thenReturn(validProduct);

        assertEquals("Laptop", productService.getProduct("P001").getName());
        verify(productApiClient, times(1)).getProduct("P001");
    }
}