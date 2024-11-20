package activeRecord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Film {
    private int id_rea;
    private int id;
    private String titre;

    //Constructeur qui construit un film a partir de de lchaine correspondant a son titre et a partir de l'objet Personne correspondant a son réalisateur
    public Film(String titre, Personne realisateur){
        this.id = -1;
        this.id_rea = realisateur.getId();
        this.titre = titre;
    }

    private Film(int id, String titre, int id_real) {
        this.id = id;
        this.id_rea = id_real;
        this.titre = titre;
    }

    public static Film findById(int id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "Select * from film where id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            String titre = rs.getString("titre");
            int id_rea = rs.getInt("id_rea");
            return new Film(id, titre, id_rea);
        }
        return null;
    }

    public Personne getRealisateur()throws SQLException {
        return Personne.findById(this.id_rea);
    }

    public static void createTable() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "Create table if not exists film (id int AUTO_INCREMENT, titre VARCHAR(100), id_rea int, primary key (id), foreign key (id_rea) references personne(id)";
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(query);
    }

    public static void deleteTable() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "Drop table if exists film";
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(query);
    }

    public void save() throws SQLException, RealisateurAbsentException {
        if (this.id_rea == -1) {
            throw new RealisateurAbsentException("realisateur inconnu");
        }
        if (this.id == -1) {
            saveNew();
        } else {
            update();
        }
    }

    private void saveNew() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "Insert into film (titre, id_rea) values (?,?)";
        PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, this.titre);
        stmt.setInt(2, this.id_rea);
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            this.id = rs.getInt(1);
        }
    }

    private void update() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "Update film set titre = ?, id_rea = ? where id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, this.titre);
        stmt.setInt(2, this.id_rea);
        stmt.setInt(3, this.id);
        stmt.executeUpdate();
    }

    public static List<Film> findByRealisateur(Personne p) throws SQLException {
        List<Film> films = new ArrayList<>();
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "Select * from film where id_rea = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, p.getId());
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String titre = rs.getString("titre");
            int id_rea = rs.getInt("id_rea");
            films.add(new Film(id, titre, id_rea));
        }
        return films;
    }

    public int getId() {
        return id;
    }

    public int getId_rea() {
        return id_rea;
    }

    public String getTitre() {
        return titre;
    }

    public void setId_rea(int id_rea) {
        this.id_rea = id_rea;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitre( String titre) {
        this.titre = titre;
    }



}
