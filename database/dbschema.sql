--users table
CREATE TABLE users(
 user_id NUMBER PRIMARY KEY,
 email VARCHAR2(255) NOT NULL,
 password VARCHAR2(255) NOT NULL,
 first_name VARCHAR2(255) NOT NULL,
 last_name VARCHAR2(255) NOT NULL,
 phone VARCHAR2(20),
 role VARCHAR2(20) DEFAULT 'CUSTOMER' CHECK (role IN ('CUSTOMER','ADMIN')),
 is_active NUMBER(1) DEFAULT 1 CHECK (is_active IN (0,1)),
 created_at DATE DEFAULT SYSDATE,
 CONSTRAINT uk_users_email UNIQUE (email)
);

CREATE SEQUENCE sq_users START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE OR REPLACE TRIGGER trg_users_id_bi
BEFORE INSERT ON users 
FOR EACH ROW
BEGIN
    IF :NEW.user_id IS NULL THEN
        :NEW.user_id := seq_users.NEXTVAL;
    END IF;
END;

CREATE OR REPLACE TRIGGER trg_users_updated_at_bu
BEFORE UPDATE ON users
FOR EACH ROW
BEGIN
    :NEW.updated_at := SYSDATE;
END;

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_role ON users(role);


--categories table
CREATE TABLE categories(
 category_id NUMBER PRIMARY KEY,
 name VARCHAR2(100) NOT NULL,
 description CLOB,
 is_active NUMBER(1) DEFAULT 1 CHECK (is_active IN (0,1)),
 created_at DATE DEFAULT SYSDATE,
 CONSTRAINT uk_categories_name UNIQUE (name)
);

CREATE SEQUENCE sq_categories START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE INDEX idx_categories_name ON categories(name);

CREATE OR REPLACE TRIGGER trg_categories_id 
BEFORE INSERT ON categories
FOR EACH ROW
BEGIN
IF :NEW.category_id IS NULL THEN
   :NEW.category_id := sq_categories.NEXTVAL;
END IF;
END;

--products table
CREATE TABLE products(
 product_id NUMBER PRIMARY KEY,
 name VARCHAR2(200) NOT NULL,
 description CLOB,
 price NUMBER(5,2) NOT NULL,
 category_id NUMBER NOT NULL,
 image_url VARCHAR2(500),
 prep_time_hours NUMBER,
 is_available NUMBER(1) DEFAULT 1 CHECK (is_available IN (0,1)),
 created_at DATE DEFAULT SYSDATE,
 CONSTRAINT fk_products_category FOREIGN KEY (category_id) REFERENCES categories(category_id),
 CONSTRAINT chk_products_price CHECK (price>0),
 CONSTRAINT chk_products_prep_time CHECK (prep_time_hours>0)
);

CREATE SEQUENCE sq_products START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE INDEX idx_products_category ON products(category_id);
CREATE INDEX idx_products_available ON products(is_available);
CREATE INDEX idx_products_name ON products(name);

CREATE OR REPLACE TRIGGER trg_categories_id 
BEFORE INSERT ON categories
FOR EACH ROW
BEGIN
IF :NEW.category_id IS NULL THEN
   :NEW.category_id := sq_categories.NEXTVAL;
END IF;
END;

--orders table
CREATE TABLE orders(
 order_id NUMBER PRIMARY KEY,
 user_id NUMBER NOT NULL,
 order_number VARCHAR(50) NOT NULL,
 status VARCHAR2(20) DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'CONFIRMED', 'PREPARING', 'READY', 'CANCELED')),
 total_amount NUMBER NOT NULL,
 pickup_date DATE NOT NULL,
 pickup_time TIMESTAMP NOT NULL,
 special_instructions CLOB,
 created_at DATE DEFAULT SYSDATE,
 updated_at DATE DEFAULT SYSDATE,
 CONSTRAINT fk_orders_user FOREIGN KEY (user_id) REFERENCES users(user_id),
 CONSTRAINT uk_orders_number UNIQUE (order_number),
 CONSTRAINT chk_orders_total CHECK (total_amount >= 0)
);

CREATE SEQUENCE sq_orders START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE INDEX idx_orders_users ON orders(user_id);
CREATE INDEX idx_orders_status ON orders(status);
CREATE INDEX idx_orders_pickup_date ON orders(pickup_date);

CREATE OR REPLACE TRIGGER trg_orders_id
    BEFORE INSERT ON orders
    FOR EACH ROW
BEGIN
    IF :NEW.order_id IS NULL THEN
        :NEW.order_id := sq_orders.NEXTVAL;
    END IF;
END;

CREATE OR REPLACE TRIGGER trg_orders_updated_at
    BEFORE UPDATE ON orders
    FOR EACH ROW
BEGIN
    :NEW.updated_at := SYSDATE;
END;


CREATE OR REPLACE TRIGGER trg_orders_number
    BEFORE INSERT ON orders
    FOR EACH ROW
BEGIN
    IF :NEW.order_number IS NULL THEN
        :NEW.order_number := 'ORD-' || TO_CHAR(SYSDATE, 'YYYYMMDD') || '-' || LPAD(sq_orders.CURRVAL, 4, '0');
    END IF;
END;


--order items table
CREATE TABLE order_items(
 id NUMBER PRIMARY KEY,
 order_id NUMBER NOT NULL,
 product_id NUMBER NOT NULL,
 quantity NUMBER NOT NULL,
 unit_price NUMBER(5,3) NOT NULL,
 subtotal NUMBER NOT NULL,
 CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES orders(order_id),
 CONSTRAINT fk_order_items_product FOREIGN KEY (product_id) REFERENCES products(product_id),
 CONSTRAINT chk_order_items_quantity CHECK (quantity > 0),
 CONSTRAINT chk_order_items_unit_price CHECK (unit_price > 0),
 CONSTRAINT chk_order_items_subtotal CHECK (subtotal >= 0)
);

CREATE SEQUENCE sq_order_items START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE INDEX idx_order_items_order ON order_items(order_id);
CREATE INDEX idx_order_items_product ON order_items(product_id);

CREATE OR REPLACE TRIGGER trg_order_items_id
    BEFORE INSERT ON order_items
    FOR EACH ROW
BEGIN
    IF :NEW.id IS NULL THEN
        :NEW.id := sq_order_items.NEXTVAL;
    END IF;
END;

CREATE OR REPLACE TRIGGER trg_order_items_subtotal
    BEFORE INSERT OR UPDATE ON order_items
    FOR EACH ROW
BEGIN
    :NEW.subtotal := :NEW.quantity * :NEW.unit_price;
END;

--inventory table
CREATE TABLE inventory(
 inventory_id NUMBER PRIMARY KEY,
 product_id NUMBER NOT NULL,
 inventory_date DATE DEFAULT SYSDATE,
 planned_quantity NUMBER DEFAULT 0,
 actual_quantity NUMBER DEFAULT 0,
 sold_quantity NUMBER DEFAULT 0,
 remaining_quantity NUMBER DEFAULT 0,
 notes CLOB,
 created_at DATE DEFAULT SYSDATE,
 updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 CONSTRAINT fk_inventory_product FOREIGN KEY (product_id) REFERENCES products(product_id),
 CONSTRAINT uk_inventory_product_date UNIQUE (product_id, inventory_date),
 CONSTRAINT chk_inventory_quantities CHECK (
        planned_quantity >= 0 AND 
        actual_quantity >= 0 AND 
        sold_quantity >= 0 AND 
        remaining_quantity >= 0
    )
);

CREATE SEQUENCE sq_inventory START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE INDEX idx_inventory_date ON inventory(inventory_date);

CREATE OR REPLACE TRIGGER trg_inventory_id
    BEFORE INSERT ON inventory
    FOR EACH ROW
BEGIN
    IF :NEW.inventory_id IS NULL THEN
        :NEW.inventory_id := sq_inventory.NEXTVAL;
    END IF;
END;

CREATE OR REPLACE TRIGGER trg_inventory_updated_at
    BEFORE UPDATE ON inventory
    FOR EACH ROW
BEGIN
    :NEW.updated_at := CURRENT_TIMESTAMP;
END;