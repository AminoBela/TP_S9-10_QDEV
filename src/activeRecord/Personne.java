package activeRecord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Personne {
    private int id;
    private String nom;
    private String prenom;

    public Personne(String nom, String prenom) {
        this.id = -1;
        this.nom = nom;
        this.prenom = prenom;
    }

    private Personne(int id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    public static void createTable() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "CREATE TABLE IF NOT EXISTS personne (id INT AUTO_INCREMENT, nom VARCHAR(40), prenom VARCHAR(40), PRIMARY KEY (id))";
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(query);
    }

    public static void deleteTable() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "DROP TABLE IF EXISTS personne";
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(query);
    }

    public static List<Personne> findAll() throws SQLException {
        List<Personne> personnes = new ArrayList<>();
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "SELECT * FROM personne";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            int id = rs.getInt("id");
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            personnes.add(new Personne(id, nom, prenom));
        }
        return personnes;
    }

    public static Personne findById(int id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "SELECT * FROM personne WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            return new Personne(id, nom, prenom);
        }
        return null;
    }

    public static List<Personne> findByName(String nom) throws SQLException {
        List<Personne> personnes = new ArrayList<>();
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "SELECT * FROM personne WHERE nom = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, nom);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String prenom = rs.getString("prenom");
            personnes.add(new Personne(id, nom, prenom));
        }
        return personnes;
    }

    public void save() throws SQLException {
        if (this.id == -1) {
            saveNew();
        } else {
            update();
        }
    }

    private void saveNew() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "INSERT INTO personne (nom, prenom) VALUES (?, ?)";
        PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, this.nom);
        stmt.setString(2, this.prenom);
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            this.id = rs.getInt(1);
        }
    }

    private void update() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "UPDATE personne SET nom = ?, prenom = ? WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, this.nom);
        stmt.setString(2, this.prenom);
        stmt.setInt(3, this.id);
        stmt.executeUpdate();
    }

    public void delete() throws SQLException {
        if (this.id != -1) {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "DELETE FROM personne WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, this.id);
            stmt.executeUpdate();
            this.id = -1;
        }
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getId() {
        return id;
    }
}
