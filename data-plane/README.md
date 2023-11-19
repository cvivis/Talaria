# Talaria Data Plane

![](https://user-images.githubusercontent.com/55385934/283648899-ebf5f8f5-de51-4d98-a769-41c87b0deb27.png)

This is data plane repository of Talaria. Data plane is a server where API gateway and load balancer, monitoring services are deployed. <br>
If you want to see more informations, visit [here](https://polyester-winter-cd5.notion.site/Talaria-b3a6d015748747f3b8d6dfa5264583d4)

## Requirements

1. [Docker 24.0.5 or higher](https://docs.docker.com/get-docker/)
2. [Docker Compose 2.23.0 or higher](https://docs.docker.com/compose/install/)
3. `vim`, `make`, `openssl`, `mod_ssl`, `git` 

## Getting Started
1. git clone
   ```bash
   git clone https://lab.ssafy.com/s09-final/S09P31A107.git
   ```
   ```bash
   cd S09P31A107/data-plane
   ```
2. Set your information
   See details on [configurations](#configurations)
   ```bash
   vim .env
   ```
3. Get SSL/TLS certification
   ```bash
   make setup
   ```
4. Start your data plane server
   ```bash
   make data-plane
   ```

## Configurations

You must set your own information such as server domains. Below variables must be configured without exception.

```.env
CONTROL_PLANE_DOMAIN=your.control.domain
DATA_PLANE_DOMAIN=your.data.domain
```

---

## Nginx Directory Structure

Configurator service generates Nginx configs and reload Nginx.

```
etc/
└── nginx/
    ├── talaria.d/
    │	├── api_conf.d/
    │	│	└── your_api.conf
    │	├── api_bakends.conf
    │	├── api_json_errors.conf
    │	├── api_keys.conf
    │	├── api_limits.conf
    │	├── api_logs.conf
    │	├── moninoring.conf
    ├── nginx.conf
    ├── api_gateway.conf
```

