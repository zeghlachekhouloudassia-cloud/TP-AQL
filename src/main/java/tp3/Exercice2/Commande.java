package tp3.Exercice2;
public class Commande {
    private long id;
    private String productName;
    private int quantity;
    private double price;
    
    public Commande(long id, String productName, int quantity, double price) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }
    
    // Getters et setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    
    public double getTotalPrice() {
        return quantity * price;
    }
}