ALTER SYSTEM SET synchronous_commit = 'on';
ALTER SYSTEM SET synchronous_standby_names = 'ANY 2 (traveler_replica, traveler_replica_2)';
