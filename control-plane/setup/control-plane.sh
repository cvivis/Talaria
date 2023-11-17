#!/bin/bash

if [ -f "./setup/setup-nginx.sh" ]; then
  . ./setup/setup-nginx.sh
fi

COMPOSE_NOT_SUPPORTED=$(command -v docker-compose 2> /dev/null)

if [ -z "$COMPOSE_NOT_SUPPORTED" ]; then
  DOCKER_COMPOSE_COMMAND="docker compose"
else
  DOCKER_COMPOSE_COMMAND="docker-compose"
fi

$DOCKER_COMPOSE_COMMAND up -d --build