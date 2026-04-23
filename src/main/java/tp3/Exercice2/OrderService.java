package tp3.Exercice2;
public class OrderService {
    private final OrderDao orderDao;
    
    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }
    
    public Commande createOrder(Commande order) {
        // Validation
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        if (order.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (order.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        
        return orderDao.saveOrder(order);
    }
}
