# Requirements

1. [Docker 24.0.5 or higher](https://docs.docker.com/get-docker/)
2. [Docker Compose 2.23.0 or higher](https://docs.docker.com/compose/install/)

# Setup

1. Initialize 
   ```bash
   make setup
   ```
2. Start Talaria Data Plane
   Use one of these three commands
   ```bash
   make talaria-data
   ```
   ```bash
   docker-compose up -d
   ```
   ```bash
   docker compose up -d
   ```

# Configuration

will be updated

# Nginx Directory Structure

```
etc/
└── nginx/
	├── talaria_conf.d/
	│	├── api_conf.d/
	│	│	└── warehouse_api.conf
	│	├── api_bakends.conf
	│	├── api_gateway.conf
	│	├── api_json_errors.conf
	│	├── custom_format.d/
	│	│	└── date_format.conf
	│	│	└── log_format.conf
	├── conf.d/
	│	├── ...
	│	├── existing_apps.conf
	├── nginx.conf
```

