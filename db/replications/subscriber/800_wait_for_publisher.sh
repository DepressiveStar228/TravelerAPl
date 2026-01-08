until pg_isready -h db -p 5432 -U repuser -d TravelerDB; do
  sleep 2
done