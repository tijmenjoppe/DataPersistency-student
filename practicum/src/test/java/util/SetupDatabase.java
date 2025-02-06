package util;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Properties;

public class SetupDatabase {
    private static Connection connection;
    private static Properties props;
    private String dbName;
    private String testDatabaseConnection;

    public SetupDatabase(String dbName) {
        this.dbName = dbName;
        props = new Properties();
        props.setProperty("user", globals.Database.dbUserName);
        props.setProperty("password", globals.Database.dbPassword);
        testDatabaseConnection = globals.Database.rootDbConnection+dbName;
    }

    public void initializeDb() throws SQLException, FileNotFoundException {
        Connection dbRootconnection = createConnection(globals.Database.rootDbConnection);

        dbRootconnection.setAutoCommit(true);

        deleteDatabaseIfExists(dbRootconnection);
        createDatabase(dbRootconnection);

        dbRootconnection.close();

        connection = createConnection(testDatabaseConnection);
        connection.setAutoCommit(false);
        populateDatabase();
    }

    private void deleteDatabaseIfExists(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        // https://dba.stackexchange.com/questions/45143/check-if-postgresql-database-exists-case-insensitive-way
        String selectDbNameSql = String.format("SELECT datname FROM pg_catalog.pg_database WHERE datname='%s'", dbName);
        ResultSet result = stmt.executeQuery(selectDbNameSql);
        if (result.next()) {
            String dropSql = String.format("DROP DATABASE %s WITH (FORCE)", dbName);
            stmt.executeUpdate(dropSql);
        }
    }

    private void createDatabase(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        String sql = String.format("CREATE DATABASE %s", dbName);
        stmt.executeUpdate(sql);
    }

    private void populateDatabase() throws FileNotFoundException {
        ScriptRunner scriptRunner = new ScriptRunner(connection);

        scriptRunner.runScript(new java.io.FileReader("src/test/ovchip/ovchip_postgresql_create_student.sql"));
        scriptRunner.runScript(new java.io.FileReader("src/test/ovchip/ovchip_postgresql_insert_student.sql"));
    }

    private Connection createConnection(String url) throws SQLException {
        return DriverManager.getConnection(url, props);
    }

    public Connection getConnection() {
        return connection;
    }

    public void clean() throws SQLException {
        Connection dbRootconnection = createConnection(globals.Database.rootDbConnection);
        deleteDatabaseIfExists(dbRootconnection);

        dbRootconnection.close();
    }
}
