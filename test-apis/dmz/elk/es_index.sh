#!/bin/bash

if [ -f .env ]; then
    source .env
else
    echo "Error: .env file not found."
    exit 1
fi

INDEX_PATH=./elasticsearch/index

rm -r ./logs/index
mkdir ./logs/index

curl -X PUT -u elastic:$ELK_PASSWORD "http://localhost:3200/genre" -H "Content-Type: application/json" -d @$INDEX_PATH/genre.json -o ./logs/index/genre.log
echo ""
curl -X PUT -u elastic:$ELK_PASSWORD "http://localhost:3200/keyword" -H "Content-Type: application/json" -d @$INDEX_PATH/keyword.json -o ./logs/index/keyword.log
echo ""
curl -X PUT -u elastic:$ELK_PASSWORD "http://localhost:3200/company" -H "Content-Type: application/json" -d @$INDEX_PATH/company.json -o ./logs/index/company.log
echo ""
curl -X PUT -u elastic:$ELK_PASSWORD "http://localhost:3200/channel" -H "Content-Type: application/json" -d @$INDEX_PATH/channel.json -o ./logs/index/channel.log
echo ""
curl -X PUT -u elastic:$ELK_PASSWORD "http://localhost:3200/field" -H "Content-Type: application/json" -d @$INDEX_PATH/field.json -o ./logs/index/field.log
echo ""
curl -X PUT -u elastic:$ELK_PASSWORD "http://localhost:3200/movie" -H "Content-Type: application/json" -d @$INDEX_PATH/movie.json -o ./logs/index/movie.log
echo ""
curl -X PUT -u elastic:$ELK_PASSWORD "http://localhost:3200/drama" -H "Content-Type: application/json" -d @$INDEX_PATH/drama.json -o ./logs/index/drama.log
echo ""
curl -X PUT -u elastic:$ELK_PASSWORD "http://localhost:3200/people" -H "Content-Type: application/json" -d @$INDEX_PATH/people.json -o ./logs/index/people.log
echo ""