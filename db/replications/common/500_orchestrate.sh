#!/usr/bin/env bash
set -euo pipefail

if [ ! -f "$PGDATA/standby.signal" ]; then
    echo "--- Running migrations on Master ---"
    run_sql_dir() {
        local dir="$1"
        [ -d "$dir" ] || return 0
        find "$dir" -maxdepth 1 -type f -name "*.sql" | sort | while read -r f; do
            psql -v ON_ERROR_STOP=1 -U "$POSTGRES_USER" -d "$POSTGRES_DB" -f "$f"
        done
        find "$dir" -maxdepth 1 -type f -name "*.sh" | sort | while read -r f; do
            bash "$f"
        done
    }

    run_sql_dir "/docker-entrypoint-migrations"
    run_sql_dir "/docker-entrypoint-replications"
fi
