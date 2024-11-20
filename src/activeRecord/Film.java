package activeRecord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Film {
    private int id;
    private String titre;
    private int id_real;

    public Film(String titre, Personne realisateur) {
        if (realisateur.getId() == -1) {
            throw new IllegalArgumentException("Le réalisateur doit provenir de la base de données");
        }
        this.id = -1;
        this.titre = titre;
        this.id_real = realisateur.getId();
    }

    private Film(int id, String titre, int id_real) {
        this.id = id;
        this.titre = titre;
        this.id_real = id_real;
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public int getIdReal() {
        return id_real;
    }

    public static void createTable() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String query = """
                CREATE TABLE IF NOT EXISTS film (
                    id INT AUTO_INCREMENT,
                    titre VARCHAR(100),
                    id_real INT,
                    PRIMARY KEY (id),
                    FOREIGN KEY (id_real) REFERENCES personne(id) ON DELETE CASCADE
                )
                """;
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(query);
    }

    public static void deleteTable() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "DROP TABLE IF EXISTS film";
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(query);
    }

    public static Film findById(int id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "SELECT * FROM film WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            String titre = rs.getString("titre");
            int id_real = rs.getInt("id_real");
            return new Film(id, titre, id_real);
        }
        return null;
    }

    public Personne getRealisateur() throws SQLException {
        return Personne.findById(this.id_real);
    }

    public static List<Film> findByRealisateur(Personne realisateur) throws SQLException {
        if (realisateur.getId() == -1) {
            throw new IllegalArgumentException("Le réalisateur doit provenir de la base de données");
        }

        List<Film> films = new ArrayList<>();
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "SELECT * FROM film WHERE id_real = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, realisateur.getId());
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String titre = rs.getString("titre");
            int id_real = rs.getInt("id_real");
            films.add(new Film(id, titre, id_real));
        }
        return films;
    }

    public void save() throws SQLException, RealisateurAbsentException {
        if (this.id_real == -1) {
            throw new RealisateurAbsentException("Le réalisateur est absent ou invalide.");
        }
        if (this.id == -1) {
            saveNew();
        } else {
            update();
        }
    }

    private void saveNew() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "INSERT INTO film (titre, id_real) VALUES (?, ?)";
        PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, this.titre);
        stmt.setInt(2, this.id_real);
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            this.id = rs.getInt(1);
        }
    }



    private void update() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "UPDATE film SET titre = ?, id_real = ? WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, this.titre);
        stmt.setInt(2, this.id_real);
        stmt.setInt(3, this.id);
        stmt.executeUpdate();
    }

    public void delete() throws SQLException {
        if (this.id != -1) {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "DELETE FROM film WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, this.id);
            stmt.executeUpdate();
            this.id = -1;
        }
    }
}
