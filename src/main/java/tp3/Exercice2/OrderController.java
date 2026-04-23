package tp3.Exercice2;



public class OrderController {
    private final OrderService orderService;
    
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    public Commande createOrder(Commande order) {
        return orderService.createOrder(order);
    }
}