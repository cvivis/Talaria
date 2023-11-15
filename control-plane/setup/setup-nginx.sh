#!/bin/bash

frontend_port=$(grep "FRONTEND_PORT" .env | cut -d '=' -f 2)
backend_port=$(grep "BACKEND_PORT" .env | cut -d '=' -f 2)
control_plane_domain=$(grep "CONTROL_PLANE_DOMAIN" .env | cut -d '=' -f 2)

mkdir "./nginx"

mkdir "./nginx/conf.d"

cat > ./nginx/conf.d/talaria.conf <<EOF
upstream frontend {
    server localhost:$frontend_port;
}

upstream backend {
    server localhost:$backend_port;
}

server {
    listen 443 ssl;
    server_name $control_plane_domain;

    location /api {
        rewrite ^/api(/.*)$ \$1 break;
        proxy_pass http://backend;
        proxy_http_version 1.1;
        proxy_set_header Upgrade \$http_upgrade;
        proxy_set_header Connection "upgrade";
        proxy_set_header Host \$host;
        proxy_set_header X-Real-IP \$remote_addr;
        proxy_set_header X-Forwarded-For \$proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto \$scheme;
    }

    location / {
        proxy_pass http://frontend;
        proxy_set_header Host \$host;
        proxy_set_header X-Real-IP \$remote_addr;
        proxy_set_header X-Forwarded-For \$proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto \$scheme;
    }

    ssl_certificate      /usr/share/nginx/certs/fullchain.pem;
    ssl_certificate_key  /usr/share/nginx/certs/privkey.pem;
}

server {
    if (\$host = $control_plane_domain) {
        return 301 https://\$host\$request_uri;
    }

    listen 80;
    server_name $control_plane_domain;
    return 404;
}
EOF

echo "The ./nginx/conf.d/talaria.conf file has been created."
