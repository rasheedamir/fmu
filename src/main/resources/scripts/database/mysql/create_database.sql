# Create fmu database and user.
#
# Command: mysql -u root -p < create_database.sql
# 'fmu$786£2'

CREATE DATABASE fmu CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE USER 'fmu' IDENTIFIED BY 'fmuM0t0rv4g';
GRANT ALL ON fmu.* TO 'fmu'@'%' IDENTIFIED BY 'fmuM0t0rv4g';
GRANT ALL ON fmu.* TO 'fmu'@'localhost' IDENTIFIED BY 'fmuM0t0rv4g';
FLUSH PRIVILEGES;