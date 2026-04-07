package tp3.Exercice2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class Exercice2Test {

    @Mock
    private OrderDao orderDao;
    
    private OrderService orderService;
    private OrderController orderController;
    
    @BeforeEach
    void setUp() {
        orderService = new OrderService(orderDao);
        orderController = new OrderController(orderService);
    }

    @Test
    void testCreateOrder_ChaineCompleteControllerServiceDao() {
        Order order = new Order(1L, "Laptop", 2, 999.99);
        Order saved = new Order(1L, "Laptop", 2, 999.99);
        saved.setId(100L);

        when(orderDao.saveOrder(order)).thenReturn(saved);

        Order result = orderController.createOrder(order);

        assertNotNull(result);
        assertEquals(100L, result.getId());
        assertEquals("Laptop", result.getProductName());
        
        verify(orderDao, times(1)).saveOrder(order);
    }

    @Test
    void testCreateOrder_OrderNull_LanceIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            orderController.createOrder(null);
        });
        verify(orderDao, never()).saveOrder(any());
    }

    @Test
    void testCreateOrder_QuantityNegative_LanceIllegalArgumentException() {
        Order order = new Order(1L, "Product", -1, 10.0);
        
        assertThrows(IllegalArgumentException.class, () -> {
            orderController.createOrder(order);
        });
        verify(orderDao, never()).saveOrder(any());
    }

    @Test
    void testCreateOrder_PriceNegative_LanceIllegalArgumentException() {
        Order order = new Order(1L, "Product", 1, -10.0);
        
        assertThrows(IllegalArgumentException.class, () -> {
            orderController.createOrder(order);
        });
        verify(orderDao, never()).saveOrder(any());
    }

    @Test
    void testCreateOrder_DaoRetourneNull() {
        Order order = new Order(2L, "Ghost", 1, 10.0);
        when(orderDao.saveOrder(order)).thenReturn(null);

        Order result = orderController.createOrder(order);

        assertNull(result);
        verify(orderDao, times(1)).saveOrder(order);
    }

    @Test
    void testCreateOrder_DaoLanceException_PropageeAuCaller() {
        Order order = new Order(3L, "Fail", 1, 10.0);
        when(orderDao.saveOrder(order))
            .thenThrow(new RuntimeException("DB connection failed"));

        RuntimeException ex = assertThrows(RuntimeException.class,
            () -> orderController.createOrder(order));

        assertEquals("DB connection failed", ex.getMessage());
        verify(orderDao, times(1)).saveOrder(order);
    }

    @Test
    void testStubbing_ChaineExceptionPuisValeur() {
        Order success = new Order(1L, "Success", 1, 100.0);
        when(orderDao.saveOrder(any(Order.class)))
            .thenThrow(new RuntimeException("DB connection failed"))
            .thenReturn(success);

        assertThrows(RuntimeException.class,
            () -> orderController.createOrder(new Order(2L, "Test", 1, 50.0)));

        Order result = orderController.createOrder(new Order(3L, "Test2", 1, 50.0));
        assertEquals("Success", result.getProductName());
        verify(orderDao, times(2)).saveOrder(any(Order.class));
    }

    @Test
    void testVerify_ProprietesOrder() {
        Order order = new Order(5L, "Keyboard", 1, 75.0);
        when(orderDao.saveOrder(order)).thenReturn(order);

        Order result = orderController.createOrder(order);

        assertNotNull(result);
        assertEquals(5L, result.getId());
        assertEquals("Keyboard", result.getProductName());
        assertEquals(1, result.getQuantity());
        assertEquals(75.0, result.getPrice(), 0.001);
        
        verify(orderDao).saveOrder(order);
    }

    @Test
    void testStubbing_DernierStubbingEcraseLePrecedent() {
        Order order1 = new Order(1L, "First", 1, 10.0);
        Order order2 = new Order(2L, "Second", 1, 20.0);
        
        when(orderDao.saveOrder(any(Order.class)))
            .thenReturn(order1)
            .thenReturn(order2);

        Order result1 = orderController.createOrder(new Order(99L, "Test", 1, 1.0));
        Order result2 = orderController.createOrder(new Order(99L, "Test", 1, 1.0));

        assertEquals("First", result1.getProductName());
        assertEquals("Second", result2.getProductName());
        verify(orderDao, times(2)).saveOrder(any(Order.class));
    }
    
    // ✅ Version CORRIGÉE
    @Test
    void testStubbing_VerificationOrdreAppels() {
        Order order1 = new Order(1L, "First", 1, 10.0);
        Order order2 = new Order(2L, "Second", 1, 20.0);
        
        when(orderDao.saveOrder(any(Order.class)))
            .thenReturn(order1)
            .thenReturn(order2);

        Order input1 = new Order(99L, "Test1", 1, 1.0);
        Order input2 = new Order(99L, "Test2", 1, 1.0);
        
        orderController.createOrder(input1);
        orderController.createOrder(input2);

        // ✅ Vérification que saveOrder a été appelé 2 fois
        verify(orderDao, times(2)).saveOrder(any(Order.class));
    }
    
    // ✅ Test supplémentaire : vérification de l'ordre avec InOrder
    @Test
    void testStubbing_VerificationOrdreAvecInOrder() {
        Order order1 = new Order(1L, "First", 1, 10.0);
        Order order2 = new Order(2L, "Second", 1, 20.0);
        
        when(orderDao.saveOrder(any(Order.class)))
            .thenReturn(order1)
            .thenReturn(order2);

        Order input1 = new Order(99L, "Test1", 1, 1.0);
        Order input2 = new Order(99L, "Test2", 1, 1.0);
        
        orderController.createOrder(input1);
        orderController.createOrder(input2);

        // Vérification de l'ordre précis des appels
        InOrder inOrder = inOrder(orderDao);
        inOrder.verify(orderDao).saveOrder(input1);
        inOrder.verify(orderDao).saveOrder(input2);
    }
}