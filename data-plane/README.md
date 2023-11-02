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