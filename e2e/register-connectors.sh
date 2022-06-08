#!/bin/sh

http POST http://localhost:8083/connectors < order-connector.json
http POST http://localhost:8083/connectors < voyage-connector.json
http POST http://localhost:8083/connectors < container-connector.json
http POST http://localhost:8083/connectors < sagastate-connector.json

