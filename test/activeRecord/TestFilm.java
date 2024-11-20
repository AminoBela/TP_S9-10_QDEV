package activeRecord;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class TestFilm {

    @BeforeEach
    void setUp() throws SQLException, RealisateurAbsentException {
        Film.deleteTable();
        Personne.deleteTable();
        Personne.createTable();
        Film.createTable();

        Personne p1 = new Personne("Lambert", "Valentino");
        Personne p2 = new Personne("Bella", "Amin");
        p1.save();
        p2.save();

        new Film("Film 1", p1).save();
        new Film("Film 2", p2).save();
    }

    @AfterEach
    void reini() throws SQLException {
        Film.deleteTable();
        Personne.deleteTable();
    }

    @Test
    void testFindById() throws SQLException {
        Film f = Film.findById(1);
        assertNotNull(f);
        assertEquals("Film 1", f.getTitre());
    }

    @Test
    void testGetRealisateur() throws SQLException {
        Film f = Film.findById(1);
        Personne real = f.getRealisateur();
        assertNotNull(real);
        assertEquals("Lambert", real.getNom());
    }

    @Test
    void testSaveNew() throws SQLException, RealisateurAbsentException {
        Personne p = Personne.findById(1);
        Film f = new Film("Film 3", p);
        f.save();
        assertNotEquals(-1, f.getId());
        assertEquals(3, Film.findByRealisateur(p).size());
    }

    @Test
    void testDelete() throws SQLException {
        Film f = Film.findById(1);
        f.delete();
        assertNull(Film.findById(1));
    }

    @Test
    void testFindByRealisateur() throws SQLException {
        Personne p = Personne.findById(2);
        assertEquals(1, Film.findByRealisateur(p).size());
    }
}
