#!/bin/bash
# PostgreSQL Database Restore Script for OTK Assistant
# This script restores a database from a compressed backup

set -e

# Configuration
POSTGRES_HOST="${POSTGRES_HOST:-postgres}"
POSTGRES_DB="${POSTGRES_DB:-otk}"
POSTGRES_USER="${POSTGRES_USER:-otkreader}"
BACKUP_DIR="${BACKUP_DIR:-/backups}"

# Read password from file if specified
if [ -n "$PGPASSWORD_FILE" ] && [ -f "$PGPASSWORD_FILE" ]; then
    export PGPASSWORD=$(cat "$PGPASSWORD_FILE")
fi

# Check if backup file is provided
if [ -z "$1" ]; then
    echo "Usage: $0 <backup-file>"
    echo ""
    echo "Available backups:"
    ls -lh "$BACKUP_DIR"/${POSTGRES_DB}_*.sql.gz 2>/dev/null || echo "No backups found"
    exit 1
fi

BACKUP_FILE="$1"

# Check if backup file exists
if [ ! -f "$BACKUP_FILE" ]; then
    echo "ERROR: Backup file not found: $BACKUP_FILE" >&2
    exit 1
fi

echo "[$(date)] Starting restore from: $BACKUP_FILE"

# Verify backup integrity
echo "[$(date)] Verifying backup integrity..."
if ! gzip -t "$BACKUP_FILE"; then
    echo "ERROR: Backup file is corrupted!" >&2
    exit 1
fi

# Confirm restore
read -p "WARNING: This will overwrite the current database '$POSTGRES_DB'. Continue? (yes/no): " CONFIRM
if [ "$CONFIRM" != "yes" ]; then
    echo "Restore cancelled."
    exit 0
fi

# Drop existing connections
echo "[$(date)] Dropping existing database connections..."
psql -h "$POSTGRES_HOST" -U "$POSTGRES_USER" -d postgres <<EOF
SELECT pg_terminate_backend(pg_stat_activity.pid)
FROM pg_stat_activity
WHERE pg_stat_activity.datname = '$POSTGRES_DB'
  AND pid <> pg_backend_pid();
EOF

# Restore database
echo "[$(date)] Restoring database..."
if gunzip -c "$BACKUP_FILE" | psql -h "$POSTGRES_HOST" -U "$POSTGRES_USER" -d "$POSTGRES_DB"; then
    echo "[$(date)] Database restored successfully"
else
    echo "ERROR: Restore failed!" >&2
    exit 1
fi

echo "[$(date)] Restore process completed successfully"

exit 0
