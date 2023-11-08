#!/bin/bash

docker exec -it nginx nginx -s reload
echo "nginx reloaded"
