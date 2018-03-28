#!/bin/bash

# This would need to be run once for any new install of MySQL
# Otherwise, don't bother with this
echo "Creating user/password in MySQL"

mysql -u root << EOF
CREATE USER 'user'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON *.* TO 'user'@'localhost';
EOF
