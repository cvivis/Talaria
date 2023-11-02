#!/bin/bash

if [ -f .env ]; then
    source .env
else
    echo "Error: .env file not found."
    exit 1
fi

INDEX_PATH=./elasticsearch/index

curl -X DELETE -u elastic:$ELK_PASSWORD "http://localhost:3200/genre"
echo ""
curl -X DELETE -u elastic:$ELK_PASSWORD "http://localhost:3200/keyword"
echo ""
curl -X DELETE -u elastic:$ELK_PASSWORD "http://localhost:3200/company"
echo ""
curl -X DELETE -u elastic:$ELK_PASSWORD "http://localhost:3200/channel"
echo ""
curl -X DELETE -u elastic:$ELK_PASSWORD "http://localhost:3200/field"
echo ""
curl -X DELETE -u elastic:$ELK_PASSWORD "http://localhost:3200/movie"
echo ""
curl -X DELETE -u elastic:$ELK_PASSWORD "http://localhost:3200/drama"
echo ""
curl -X DELETE -u elastic:$ELK_PASSWORD "http://localhost:3200/people"
echo ""