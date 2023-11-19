env_file=".env"
properties_file="./monitoring/libs/.env"

if [ ! -f "$env_file" ]; then
    echo ".env file not found."
    exit 1
fi

if [ -e "$properties_file" ]; then
    rm "$properties_file"
    echo "$properties_file deleted."
fi

monitoring_keys=("CONTROL_PLANE_DOMAIN" "NGINX_CONTAINER_NAME" "POSTGRES_PORT" "POSTGRES_USER" "POSTGRES_PASSWORD" "POSTGRES_DB")

for key in "${monitoring_keys[@]}"; do
    value=$(grep "^$key=" "$env_file" | cut -d= -f2)
    if [ -n "$value" ]; then
        echo "$key=$value" >> "$properties_file"
    else
        echo "Warning: Key '$key' not found in $env_file"
    fi
done

echo "Conversion successful. Created $properties_file"