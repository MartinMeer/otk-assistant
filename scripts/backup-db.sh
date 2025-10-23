#!/bin/bash
# PostgreSQL Database Backup Script for OTK Assistant
# This script creates compressed database backups with rotation

set -e

# Configuration
POSTGRES_HOST="${POSTGRES_HOST:-postgres}"
POSTGRES_DB="${POSTGRES_DB:-otk}"
POSTGRES_USER="${POSTGRES_USER:-otkreader}"
BACKUP_DIR="${BACKUP_DIR:-/backups}"
RETENTION_DAYS="${BACKUP_RETENTION_DAYS:-7}"

# Read password from file if specified
if [ -n "$PGPASSWORD_FILE" ] && [ -f "$PGPASSWORD_FILE" ]; then
    export PGPASSWORD=$(cat "$PGPASSWORD_FILE")
fi

# Create backup directory if it doesn't exist
mkdir -p "$BACKUP_DIR"

# Generate backup filename with timestamp
TIMESTAMP=$(date +"%Y%m%d_%H%M%S")
BACKUP_FILE="${BACKUP_DIR}/${POSTGRES_DB}_${TIMESTAMP}.sql.gz"

echo "[$(date)] Starting backup of database '$POSTGRES_DB' on host '$POSTGRES_HOST'"

# Create backup
if pg_dump -h "$POSTGRES_HOST" -U "$POSTGRES_USER" -d "$POSTGRES_DB" | gzip > "$BACKUP_FILE"; then
    echo "[$(date)] Backup completed successfully: $BACKUP_FILE"
    
    # Get backup size
    BACKUP_SIZE=$(du -h "$BACKUP_FILE" | cut -f1)
    echo "[$(date)] Backup size: $BACKUP_SIZE"
else
    echo "[$(date)] ERROR: Backup failed!" >&2
    exit 1
fi

# Cleanup old backups (keep only RETENTION_DAYS days)
if [ "$RETENTION_DAYS" -gt 0 ]; then
    echo "[$(date)] Cleaning up backups older than $RETENTION_DAYS days..."
    
    # Find and delete old backups
    find "$BACKUP_DIR" -name "${POSTGRES_DB}_*.sql.gz" -type f -mtime +$RETENTION_DAYS -delete
    
    # List remaining backups
    BACKUP_COUNT=$(find "$BACKUP_DIR" -name "${POSTGRES_DB}_*.sql.gz" -type f | wc -l)
    echo "[$(date)] Total backups retained: $BACKUP_COUNT"
fi

# Verify backup integrity
echo "[$(date)] Verifying backup integrity..."
if gzip -t "$BACKUP_FILE"; then
    echo "[$(date)] Backup verification successful"
else
    echo "[$(date)] ERROR: Backup verification failed!" >&2
    exit 1
fi

echo "[$(date)] Backup process completed successfully"

# List all current backups
echo "[$(date)] Current backups:"
ls -lh "$BACKUP_DIR"/${POSTGRES_DB}_*.sql.gz 2>/dev/null || echo "No backups found"

exit 0
