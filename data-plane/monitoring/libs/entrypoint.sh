#!/bin/bash

cd /home/libs

set -eu

export $(cat .env | xargs)
java -jar monitoring-0.0.1-SNAPSHOT.jar