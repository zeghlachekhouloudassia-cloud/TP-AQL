package tp3.Exercice2;

import java.sql.*;

/**
 * Implémentation JDBC de OrderDao.
 * Utilisée dans les tests d'intégration avec Testcontainers (MySQL).
 *
 * Table attendue :
 *   CREATE TABLE orders (id BIGINT AUTO_INCREMENT PRIMARY KEY,
 *                        product_name VARCHAR(255), quantity INT, price DOUBLE);
 */
public class OrderDaoJdbc implements OrderDao {

    private final String jdbcUrl;
    private final String username;
    private final String password;

    public OrderDaoJdbc(String jdbcUrl, String username, String password) {
        this.jdbcUrl  = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    /** Crée la table orders si elle n'existe pas encore. */
    public void initSchema() throws SQLException {
        try (Connection conn = getConnection();
             Statement stmt  = conn.createStatement()) {
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS orders (" +
                "  id           BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "  product_name VARCHAR(255) NOT NULL," +
                "  quantity     INT          NOT NULL," +
                "  price        DOUBLE       NOT NULL" +
                ")"
            );
        }
    }

    @Override
    public Order saveOrder(Order order) {
        String sql = "INSERT INTO orders (product_name, quantity, price) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, order.getProductName());
            ps.setInt(2,    order.getQuantity());
            ps.setDouble(3, order.getPrice());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    order.setId(keys.getLong(1));
                }
            }
            return order;

        } catch (SQLException e) {
            throw new RuntimeException("DB error while saving order", e);
        }
    }

    /** Helper de test : retrouve une commande par son ID généré. */
    public Order findById(long id) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 "SELECT id, product_name, quantity, price FROM orders WHERE id = ?")) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Order(
                        rs.getLong("id"),
                        rs.getString("product_name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                    );
                }
            }
        }
        return null;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, username, password);
    }
}
