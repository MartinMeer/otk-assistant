package org.martinmeer.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * Diagnostic utility to help troubleshoot database connection and query issues
 */
public class DatabaseDiagnosticExample {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseDiagnosticExample.class);
    
    public static void main(String[] args) {
        System.out.println("=== Database Diagnostic Tool ===");
        
        // Step 1: Test basic connection
        testBasicConnection();
        
        // Step 2: Test database info
        testDatabaseInfo();
        
        // Step 3: Test schema access
        testSchemaAccess();
        
        // Step 4: Test specific table
        testSpecificTable();
        
        // Cleanup
        DatabaseConnection.closeConnection();
        System.out.println("\n=== Diagnostic Complete ===");
    }
    
    static void testBasicConnection() {
        System.out.println("\n--- Step 1: Basic Connection Test ---");
        
        try {
            //System.out.println("Database URL: " + DatabaseConnection.getUrl());
            //System.out.println("Username: " + DatabaseConnection.getUsername());

            boolean canConnect = DatabaseConnection.testConnection();
            System.out.println("Can establish connection: " + canConnect);

            if (canConnect) {
                boolean isValid = DatabaseConnection.isConnectionValid();
                System.out.println("Connection is valid: " + isValid);
            }
            
        } catch (Exception e) {
            System.err.println("Basic connection test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    static void testDatabaseInfo() {
        System.out.println("\n--- Step 2: Database Info Test ---");
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();

            System.out.println("Database Product: " + metaData.getDatabaseProductName());
            System.out.println("Database Version: " + metaData.getDatabaseProductVersion());
            System.out.println("Driver Name: " + metaData.getDriverName());
            System.out.println("Driver Version: " + metaData.getDriverVersion());
            System.out.println("Connection URL: " + metaData.getURL());
            System.out.println("User Name: " + metaData.getUserName());
            
            // Test basic query
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT current_database(), current_user, version()")) {

                if (rs.next()) {
                    System.out.println("Current Database: " + rs.getString(1));
                    System.out.println("Current User: " + rs.getString(2));
                    System.out.println("PostgreSQL Version: " + rs.getString(3));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Database info test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    static void testSchemaAccess() {
        System.out.println("\n--- Step 3: Schema Access Test ---");
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            
            // List all schemas
            System.out.println("Available schemas:");
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT schema_name FROM information_schema.schemata ORDER BY schema_name")) {

                int schemaCount = 0;
                while (rs.next()) {
                    String schemaName = rs.getString(1);
                    System.out.println("  - " + schemaName);
                    schemaCount++;
                }
                System.out.println("Total schemas: " + schemaCount);
            }
            
            // Check if 'esdp' schema exists
            System.out.println("\nChecking for 'esdp' schema:");
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT 1 FROM information_schema.schemata WHERE schema_name = 'esdp'")) {

                if (rs.next()) {
                    System.out.println("✓ 'esdp' schema exists");
                } else {
                    System.out.println("✗ 'esdp' schema does NOT exist");
                }
            }

            // List tables in esdp schema (if it exists)
            System.out.println("\nTables in 'esdp' schema:");
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema = 'esdp' ORDER BY table_name")) {

                int tableCount = 0;
                while (rs.next()) {
                    String tableName = rs.getString(1);
                    System.out.println("  - " + tableName);
                    tableCount++;
                }
                System.out.println("Total tables in esdp: " + tableCount);

                if (tableCount == 0) {
                    System.out.println("⚠ No tables found in 'esdp' schema or schema doesn't exist");
                }
            }

        } catch (SQLException e) {
            System.err.println("Schema access test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    static void testSpecificTable() {
        System.out.println("\n--- Step 4: Specific ElementTypeTable Test ---");
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            
            // Check if element_type table exists
            System.out.println("Checking for 'esdp.element_type' table:");
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(
                     "SELECT 1 FROM information_schema.tables " +
                     "WHERE table_schema = 'esdp' AND table_name = 'element_type'")) {

                if (rs.next()) {
                    System.out.println("✓ 'esdp.element_type' table exists");
                    
                    // Get table structure
                    System.out.println("\nElementTypeTable structure:");
                    try (ResultSet columns = stmt.executeQuery(
                         "SELECT column_name, data_type, is_nullable " +
                         "FROM information_schema.columns " +
                         "WHERE table_schema = 'esdp' AND table_name = 'element_type' " +
                         "ORDER BY ordinal_position")) {

                        while (columns.next()) {
                            System.out.printf("  - %s (%s) %s%n",
                                columns.getString("column_name"),
                                columns.getString("data_type"),
                                "YES".equals(columns.getString("is_nullable")) ? "NULL" : "NOT NULL");
                        }
                    }
                    
                    // Test query with row count
                    System.out.println("\nTesting data query:");
                    try (ResultSet dataRs = stmt.executeQuery("SELECT COUNT(*) FROM esdp.element_type")) {
                        if (dataRs.next()) {
                            int rowCount = dataRs.getInt(1);
                            System.out.println("Total rows in table: " + rowCount);
                            
                            if (rowCount > 0) {
                                // Get sample data
                                try (ResultSet sampleRs = stmt.executeQuery("SELECT * FROM esdp.element_type LIMIT 3")) {
                                    System.out.println("\nSample data:");
                                    int sampleCount = 0;
                                    while (sampleRs.next()) {
                                        String elType = sampleRs.getString("el_type");
                                        System.out.println("  Row " + (++sampleCount) + ": el_type = " + elType);
                                    }
                                }
                            } else {
                                System.out.println("⚠ ElementTypeTable exists but contains no data");
                            }
                        }
                    }
                    
                } else {
                    System.out.println("✗ 'esdp.element_type' table does NOT exist");
                    
                    // Suggest alternatives
                    System.out.println("\nSearching for similar table names:");
                    try (ResultSet similarTables = stmt.executeQuery(
                         "SELECT table_schema, table_name FROM information_schema.tables " +
                         "WHERE table_name ILIKE '%element%' OR table_name ILIKE '%type%' " +
                         "ORDER BY table_schema, table_name")) {
                        
                        boolean foundSimilar = false;
                        while (similarTables.next()) {
                            foundSimilar = true;
                            System.out.printf("  - %s.%s%n", 
                                similarTables.getString("table_schema"),
                                similarTables.getString("table_name"));
                        }
                        
                        if (!foundSimilar) {
                            System.out.println("  No similar tables found");
                        }
                    }
                }
                
            }
            
        } catch (SQLException e) {
            System.err.println("Specific table test failed: " + e.getMessage());
            e.printStackTrace();
            
            // Additional error analysis
            System.out.println("\nError analysis:");
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            
            if (e.getMessage().contains("permission denied")) {
                System.out.println("⚠ This appears to be a permission issue. Check user privileges.");
            } else if (e.getMessage().contains("does not exist")) {
                System.out.println("⚠ This appears to be a table/schema existence issue.");
            }
        }
    }
}