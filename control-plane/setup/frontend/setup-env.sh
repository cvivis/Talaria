#!/bin/bash

env_file=".env"
properties_file="./frontend/.env"

if [ ! -f "$env_file" ]; then
    echo ".env file not found."
    exit 1
fi

if [ -e "$properties_file" ]; then
    rm "$properties_file"
    echo "$properties_file deleted."
fi

frontend_keys=("CONTROL_PLANE_DOMAIN" "DATA_PLANE_DOMAIN")

for key in "${frontend_keys[@]}"; do
    value=$(grep "^$key=" "$env_file" | cut -d= -f2)
    if [ -n "$value" ]; then
        echo "REACT_APP_$key=$value" >> "$properties_file"
    else
        echo "Warning: Key '$key' not found in $env_file"
    fi
done

echo "Conversion successful. Created $properties_file"