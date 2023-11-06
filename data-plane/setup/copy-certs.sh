#!/bin/bash

domain=$(grep "DOMAIN" .env | cut -d '=' -f 2)

mkdir "./nginx/ssl"

fullchain_src_path="./setup/certbot/conf/live/${domain}/fullchain.pem"
fullchain_dest_path="./nginx/ssl/fullchain.pem"

if [ -e "$fullchain_src_path" ]; then
  cp "$fullchain_path" "$fullchain_dest_path"

  if [ $? -eq 0 ]; then
    echo "File Copied: $fullchain_src_path -> $fullchain_dest_path"
  fi
fi

privkey_src_path="./setup/certbot/conf/live/${domain}/privkey.pem"
privkey_dest-path="./nginx/ssl/privkey.pem"

if [ -e "$privkey_src_path" ]; then
  cp "$privkey_src_path" "$privkey_dest"

  if [ $? -eq 0 ]; then
    echo "File Copied: $privkey_src_path -> $privkey_dest"
  fi
fi


