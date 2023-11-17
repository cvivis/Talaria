.DEFAULT_GOAL:=help

COMPOSE := -f docker-compose.yml

compose_v2_not_supported = $(shell command docker compose 2> /dev/null)
ifeq (,$(compose_v2_not_supported))
	DOCKER_COMPOSE_COMMAND = docker-compose
else
	DOCKER_COMPOSE_COMMAND = docker compose
endif

# --------------------------
.PHONY: setup control-plane down stop help

setup: ## Generate Let's Encrypt SSL/TLS certificates Automatically
	. ./setup/setup-certs.sh

control-plane:	## Start control-plane.
	. ./setup/control-plane.sh

down: ## Down control-plane and all its extra components.
	. ./setup/down.sh

stop: ## Stop control-plane and all its extra components.
	$(DOCKER_COMPOSE_COMMAND) ${COMPOSE} stop

help: ## Show this help.
	@echo "Make Talaria control plane with docker-compose files"
	@awk 'BEGIN {FS = ":.*##"; printf "\nUsage:\n  make \033[36m<target>\033[0m (default: help)\n\nTargets:\n"} /^[a-zA-Z_-]+:.*?##/ { printf "  \033[36m%-12s\033[0m %s\n", $$1, $$2 }' $(MAKEFILE_LIST)
