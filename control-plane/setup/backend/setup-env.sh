#!/bin/bash

env_file=".env"
properties_file="./backend/libs/.env"

if [ ! -f "$env_file" ]; then
    echo ".env file not found."
    exit 1
fi

if [ -e "$properties_file" ]; then
    rm "$properties_file"
    echo "$properties_file deleted."
fi

cp "$env_file" "$properties_file"
echo "Copy successful. Created $properties_file"