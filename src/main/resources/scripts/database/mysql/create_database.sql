# Create fmu database and user.
#
# Command: mysql -u root -p < create_database.sql
#

CREATE DATABASE fmu CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE USER 'fmu' IDENTIFIED BY 'fmu$786£2';
GRANT ALL ON fmu.* TO 'fmu'@'%' IDENTIFIED BY 'fmu$786£2';
GRANT ALL ON fmu.* TO 'fmu'@'localhost' IDENTIFIED BY 'fmu$786£2';
FLUSH PRIVILEGES;