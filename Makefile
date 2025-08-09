SHELL := /bin/bash

.PHONY: dev up down logs test backend-test frontend-build

dev:
	docker compose up -d --build

up:
	docker compose up -d

down:
	docker compose down -v

logs:
	docker compose logs -f | cat

test: backend-test

backend-test:
	docker run --rm -v "$(PWD)/backend":/app -w /app maven:3.9-eclipse-temurin-17 mvn -q -B test

frontend-build:
	docker run --rm -v "$(PWD)/frontend":/app -w /app node:20-alpine sh -lc "npm ci && npm run build"


