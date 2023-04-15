DROP DATABASE pistas;
CREATE DATABASE IF NOT EXISTS pistas;

USE pistas;

CREATE USER IF NOT EXISTS 'pistas'@'localhost' IDENTIFIED BY 'pistas';
GRANT ALL PRIVILEGES ON pistas.* TO 'root'@'localhost';
GRANT ALL PRIVILEGES ON pistas.* TO 'pistas'@'localhost';
FLUSH PRIVILEGES;

SHOW GRANTS FOR root@'localhost';
SHOW GRANTS FOR pistas@'localhost';

SELECT * FROM pista;
SELECT * FROM hibernate_sequence;