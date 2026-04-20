package tp3.Exercice1;

import java.sql.*;

/**
 * Implémentation JDBC de UserRepository.
 * Utilisée dans les tests d'intégration avec Testcontainers (MySQL).
 *
 * La table attendue :
 *   CREATE TABLE users (id BIGINT PRIMARY KEY, name VARCHAR(255), email VARCHAR(255));
 */
public class UserRepositoryJdbc implements UserRepository {

    private final String jdbcUrl;
    private final String username;
    private final String password;

    public UserRepositoryJdbc(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    /** Initialise le schéma (appelé une fois au démarrage du conteneur). */
    public void initSchema() throws SQLException {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS users (" +
                "  id    BIGINT       PRIMARY KEY," +
                "  name  VARCHAR(255) NOT NULL," +
                "  email VARCHAR(255) NOT NULL" +
                ")"
            );
        }
    }

    /** Insère un utilisateur (helper pour les tests). */
    public void save(User user) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 "INSERT INTO users (id, name, email) VALUES (?, ?, ?)")) {
            ps.setLong(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getEmail());
            ps.executeUpdate();
        }
    }

    @Override
    public User findUserById(long id) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 "SELECT id, name, email FROM users WHERE id = ?")) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getLong("id"),
                                   rs.getString("name"),
                                   rs.getString("email"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB error", e);
        }
        return null;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, username, password);
    }
}
