# Drop fmu database and user.
#
# Command: mysql -u root -p < drop_database.sql
#

DROP DATABASE IF EXISTS fmu;
DROP USER 'fmu'@'localhost';
DROP USER 'fmu'@'%';