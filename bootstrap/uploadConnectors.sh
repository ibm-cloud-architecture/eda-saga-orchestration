#!/bin/sh

curl -H 'Content-Type: application/json' -X PUT http://localhost:8083/connectors/order-outbox-connector/config  -d@register-order-connector.json
curl -H 'Content-Type: application/json' -X PUT http://localhost:8083/connectors/payment-outbox-connector/config -d@register-voyage-connector.json
curl -H 'Content-Type: application/json' -X PUT http://localhost:8083/connectors/credit-outbox-connector/config -d@register-container-connector.json
curl -H 'Content-Type: application/json' -X PUT http://localhost:8083/connectors/order-sagastate-connector/config -d@register-sagastate-connector.json