#!/bin/bash

cd /home/libs

set -eux

export $(cat .env | xargs)
java -jar talaria-0.0.1-SNAPSHOT.jar