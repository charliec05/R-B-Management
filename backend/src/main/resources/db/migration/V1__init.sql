-- Roles and Users
CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role_id BIGINT NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

-- Inventory domain
CREATE TABLE items (
    id BIGSERIAL PRIMARY KEY,
    sku VARCHAR(100) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    unit VARCHAR(50) NOT NULL,
    cost_method VARCHAR(20) NOT NULL
);

CREATE TABLE warehouses (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255)
);

CREATE TABLE inventory_lots (
    id BIGSERIAL PRIMARY KEY,
    item_id BIGINT NOT NULL REFERENCES items(id),
    warehouse_id BIGINT NOT NULL REFERENCES warehouses(id),
    quantity NUMERIC(18,3) NOT NULL,
    unit_cost NUMERIC(18,4) NOT NULL,
    received_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW()
);

-- Seed base roles
INSERT INTO roles(name) VALUES ('ADMIN') ON CONFLICT DO NOTHING;
INSERT INTO roles(name) VALUES ('USER') ON CONFLICT DO NOTHING;


