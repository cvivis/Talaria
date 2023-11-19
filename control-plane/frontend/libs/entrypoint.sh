#!/bin/bash

cd /home

set -eu

npm i
echo "Done: npm i done"

npm install env-cmd
echo "Done: npm install env-cmd"

npm run build
echo "Done: npm run build"

npm install -g serve
echo "Done: npm install -g serve"

serve build
echo "Done: serve build"