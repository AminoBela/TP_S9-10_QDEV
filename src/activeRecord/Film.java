package activeRecord;

import java.sql.*;


public class Film {
    private int id_real;
    private int id;
    private String titre;

    //Constructeur qui construit un film a partir de de lchaine correspondant a son titre et a partir de l'objet Personne correspondant a son réalisateur
    public Film(String titre, Personne realisateur){
        this.id = -1;
        this.id_real = realisateur.getId();
        this.titre = titre;
    }

    private Film(int id, String titre, int id_real) {
        this.id = id;
        this.id_real = id_real;
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
        return Personne.findById(this.id_real);
    }

    public int getId() {
        return id;
    }

    public int getId_real() {
        return id_real;
    }

    public String getTitre() {
        return titre;
    }

    public void setId_real(int id_real) {
        this.id_real = id_real;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitre( String titre) {
        this.titre = titre;
    }



}
