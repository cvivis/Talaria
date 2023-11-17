# Requirements

1. [Docker 24.0.5 or higher](https://docs.docker.com/get-docker/)
2. [Docker Compose 2.23.0 or higher](https://docs.docker.com/compose/install/)

# Getting Started

1. Get SSL/TLS certificate for Data Plane
   ```bash
   make setup
   ```
2. Start Talaria Data Plane
   ```bash
   make data-plane
   ```

# Nginx Directory Structure

```
etc/
└── nginx/
    ├── talaria_conf.d/
    │	├── api_conf.d/
    │	│	└── warehouse_api.conf
    │	├── api_bakends.conf - 완료
    │	├── api_gateway.conf - 완료
    │	├── api_keys.conf - 완료
    │	├── api_json_errors.conf - 변경 없음, 완료
    │	├── moninoring.conf
    │	├── json_validation.js - 변경 없음, 완료
    │	├── custom_format.d/ - 변경 없음, 완료
    │	│	└── date_format.conf - 변경 없음, 완료
    │	│	└── log_format.conf - 변경 없음, 완료
    ├── conf.d/
    │	├── ...
    │	├── existing_apps.conf
    ├── nginx.conf
```

