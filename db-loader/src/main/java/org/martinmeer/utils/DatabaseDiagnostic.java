package org.martinmeer.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.martinmeer.utils.DatabaseDiagnosticExample.*;

/**
 * Diagnostic utility to help troubleshoot database connection and query issues
 */
public class DatabaseDiagnostic {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseDiagnostic.class);
    
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

}