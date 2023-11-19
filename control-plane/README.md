# Talaria Control Plane

![](https://user-images.githubusercontent.com/55385934/283648899-ebf5f8f5-de51-4d98-a769-41c87b0deb27.png)

This is control plane repository of Talaria. Control plane is a server where portals are deployed. <br>
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
   cd S09P31A107/control-plane
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
4. Start your control plane server
   ```bash
   make control-plane
   ```
## Configurations

You must set your own information such as server domains. Below variables must be configured without exception.

```.env
CONTROL_PLANE_DOMAIN=your.control.domain
DATA_PLANE_DOAMIN=your.data.domain
```
