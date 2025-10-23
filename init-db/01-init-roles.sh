#!/bin/bash
set -e

# Create the otkreader role if it doesn't exist
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    DO \$\$
    BEGIN
        IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'otkreader') THEN
            CREATE ROLE otkreader;
        END IF;
    END
    \$\$;
EOSQL