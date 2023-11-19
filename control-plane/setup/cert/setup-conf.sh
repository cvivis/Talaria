#!/bin/bash

domain=$(grep "CONTROL_PLANE_DOMAIN" .env | cut -d '=' -f 2)

mkdir "./setup/conf.d"

cat > ./setup/conf.d/default.conf <<EOF
server {
    listen [::]:80;
    listen 80;

    server_name $domain;

    location ~ /.well-known/acme-challenge {
        allow all;
        root /var/www/certbot;
    }
}
EOF

echo "The ./setup/conf.d/default.conf file has been created."
