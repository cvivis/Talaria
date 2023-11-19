#!/bin/bash

if [ -f "./setup/nginx/setup-nginx.sh" ]; then
  . ./setup/nginx/setup-nginx.sh
fi

if [ -f "./setup/frontend/setup-env.sh" ]; then
  . ./setup/frontend/setup-env.sh
fi

if [ -f "./setup/backend/setup-env.sh" ]; then
  . ./setup/backend/setup-env.sh
fi

COMPOSE_NOT_SUPPORTED=$(command -v docker-compose 2> /dev/null)

if [ -z "$COMPOSE_NOT_SUPPORTED" ]; then
  DOCKER_COMPOSE_COMMAND="docker compose"
else
  DOCKER_COMPOSE_COMMAND="docker-compose"
fi

$DOCKER_COMPOSE_COMMAND up -d --build