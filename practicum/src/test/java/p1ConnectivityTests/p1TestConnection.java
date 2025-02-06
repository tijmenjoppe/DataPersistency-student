package p1ConnectivityTests;

import globals.Psql;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.SetupDatabase;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class p1TestConnection {
    private static SetupDatabase setupDatabase;

    @BeforeAll
    public static void beforeAll() throws SQLException, FileNotFoundException {
        setupDatabase = new SetupDatabase(Psql.dbName);

        setupDatabase.initializeDb();
    }

    @AfterAll
    public static void after() throws SQLException {
        setupDatabase.clean();
    }

    @Test
    public void testConnection() {
        assertDoesNotThrow(() -> {
            Connection conn =  setupDatabase.getConnection();

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM reiziger;");

            // There should be at least a record with ReizigerId
            rs.next();
            rs.getInt("reiziger_id");

            rs.close();
        });
    }
}
