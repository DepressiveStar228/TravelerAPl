CREATE SUBSCRIPTION tp_sub
CONNECTION 'host=db port=5432 dbname=TravelerDB user=repuser password=rep_pass'
PUBLICATION tp_pub;