-- Add new roles
INSERT INTO roles(name) VALUES ('WAREHOUSE_OPS') ON CONFLICT DO NOTHING;
INSERT INTO roles(name) VALUES ('READONLY') ON CONFLICT DO NOTHING;

-- Add code to warehouses
ALTER TABLE warehouses ADD COLUMN IF NOT EXISTS code VARCHAR(50);
-- Backfill code from name
UPDATE warehouses SET code = UPPER(REPLACE(COALESCE(name, 'WH'), ' ', '_')) WHERE code IS NULL;
-- Enforce constraints
ALTER TABLE warehouses ALTER COLUMN code SET NOT NULL;
ALTER TABLE warehouses ADD CONSTRAINT uq_warehouses_code UNIQUE (code);

-- Extend inventory_lots
ALTER TABLE inventory_lots ADD COLUMN IF NOT EXISTS lot_no VARCHAR(100);
ALTER TABLE inventory_lots ADD COLUMN IF NOT EXISTS qty_on_hand NUMERIC(18,3);
ALTER TABLE inventory_lots ADD COLUMN IF NOT EXISTS cost_basis NUMERIC(18,4);

-- Backfill from old columns if present
UPDATE inventory_lots SET qty_on_hand = quantity WHERE qty_on_hand IS NULL AND quantity IS NOT NULL;
UPDATE inventory_lots SET cost_basis = unit_cost WHERE cost_basis IS NULL AND unit_cost IS NOT NULL;

-- Indexes for fast lookups
CREATE INDEX IF NOT EXISTS idx_inventory_lots_item ON inventory_lots(item_id);
CREATE INDEX IF NOT EXISTS idx_inventory_lots_wh ON inventory_lots(warehouse_id);
CREATE INDEX IF NOT EXISTS idx_inventory_lots_lotno ON inventory_lots(lot_no);


