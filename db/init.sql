DELETE FROM mysql.user WHERE User='';

UPDATE mysql.user SET Host='%' WHERE User='root';

FLUSH PRIVILEGES;