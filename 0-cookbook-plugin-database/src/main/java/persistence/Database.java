package persistence;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class Database {

    private static final String url = "jdbc:sqlite:./cookbookdb.db";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.err.println("SQL ERROR: " + e.getMessage());
        }
        return connection;
    }

    public static void setupDatabase() {
        System.out.println("--Start database setup--");
        System.out.println("---Creating database---");
        getConnection();
        System.out.println("----Creating tables----");
        createTableIfNotExists();
        System.out.println("---Adding demo data---");
        addDemoData();
        System.out.println("--End database setup--");
    }

    private static void createTableIfNotExists() {
        List<String> createTables = new LinkedList<>();

        createTables.add("CREATE TABLE IF NOT EXISTS user (\n"
                + "	username VARCHAR(255) PRIMARY KEY \n"
                + ");");
        createTables.add("CREATE TABLE IF NOT EXISTS recipe (\n"
                + "	id INTEGER PRIMARY KEY autoincrement,\n"
                + "	recipeName VARCHAR(255) NOT NULL,\n"
                + "	category VARCHAR(255) NOT NULL,\n"
                + "	cookingTime integer NOT NULL,\n"
                + "	cookingInstruction VARCHAR(255) NOT NULL,\n"
                + "	username VARCHAR(255) NOT NULL,\n"
                + "	FOREIGN KEY (username) REFERENCES user(username) ON DELETE CASCADE \n"
                + ");");
        createTables.add("CREATE TABLE IF NOT EXISTS ingredient (\n"
                + "	ingredientName VARCHAR(255) NOT NULL,\n"
                + "	amount DOUBLE NOT NULL,\n"
                + "	unit VARCHAR(255) NOT NULL,\n"
                + "	recipeId INTEGER NOT NULL,\n"
                + "	FOREIGN KEY (recipeId) REFERENCES recipe(id) ON DELETE CASCADE,\n"
                + "	CONSTRAINT pk_ingredient PRIMARY KEY (ingredientName, recipeId) \n"
                + ");");
        createTables.add("CREATE TABLE IF NOT EXISTS review (\n"
                + "	reviewText VARCHAR(255) NOT NULL,\n"
                + "	reviewStar INTEGER NOT NULL,\n"
                + "	username VARCHAR(255) NOT NULL,\n"
                + "	recipeId INTEGER NOT NULL,\n"
                + "	FOREIGN KEY (recipeId) REFERENCES recipe(ID) ON DELETE CASCADE,\n"
                + "	CONSTRAINT pk_review PRIMARY KEY (username, recipeId) \n"
                + ");");

        try (Connection connection = getConnection()) {
            try (Statement stmt = connection.createStatement()) {
                for (String sqlStatement : createTables) {
                    stmt.execute(sqlStatement);
                }
            } catch (SQLException e) {
                System.err.println("SQL ERROR: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("SQL ERROR: " + e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void printMetadata() {
        DatabaseMetaData dbmd = null;
        try {
            dbmd = getConnection().getMetaData();
            ResultSet tablesResultSet = dbmd.getTables(null, null, "%", null);
            while (tablesResultSet.next()) {
                System.out.println(tablesResultSet.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            System.err.println("SQL ERROR: " + e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void addDemoData() {
        List<String> insertDemoData = new LinkedList<>();
        insertDemoData.add("INSERT INTO user(username) VALUES('Niklas123'),('Max1'),('Muster12345')");
        insertDemoData.add("INSERT INTO recipe(recipeName, username) VALUES('Pizza','family', 45, '1. Teig dann backen','Niklas123')");
        try (Connection connection = getConnection()) {
            try (Statement stmt = connection.createStatement()) {
                for (String sqlStatement : insertDemoData) {
                    stmt.execute(sqlStatement);
                }
            } catch (SQLException e) {
                System.err.println("SQL ERROR: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("SQL ERROR: " + e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
