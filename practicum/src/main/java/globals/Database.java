package globals;

public class Database {
    //(root) User name van postgresql database, waarschijnlijk postgres, maar kan anders zijn afhankelijk van hoe je het geinstalleerd hebt
    public static final String dbUserName = "postgres";
    //(root) password van postgresql database
    public static final String dbPassword = "";
    // Waar je db draait, staat by deafult goed, tenzij je ergens anders (of een andere poort) hebt geinstalleerd
    public static final String rootDbConnection = "jdbc:postgresql://localhost/";
}
