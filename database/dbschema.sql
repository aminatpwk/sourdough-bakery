-- users table
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    role ENUM('CUSTOMER', 'ADMIN') DEFAULT 'CUSTOMER',
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_email (email),
    INDEX idx_role (role)
);

--categories table
CREATE TABLE categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    INDEX idx_name (name),
    INDEX idx_active (is_active)
);

--products table
CREATE TABLE products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    category_id BIGINT NOT NULL,
    image_url VARCHAR(500),
    prep_time_hours INT DEFAULT 24, 
    is_available BOOLEAN DEFAULT TRUE,
    is_featured BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (category_id) REFERENCES categories(id),
    INDEX idx_category (category_id),
    INDEX idx_available (is_available),
    INDEX idx_featured (is_featured),
    INDEX idx_name (name)
);

--orders table
CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    order_number VARCHAR(50) NOT NULL UNIQUE,
    status ENUM('PENDING', 'CONFIRMED', 'PREPARING', 'READY', 'COMPLETED', 'CANCELLED') DEFAULT 'PENDING',
    total_amount DECIMAL(10,2) NOT NULL,
    pickup_date DATE NOT NULL,
    pickup_time TIME NOT NULL,
    special_instructions TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (user_id) REFERENCES users(id),
    INDEX idx_user (user_id),
    INDEX idx_status (status),
    INDEX idx_pickup_date (pickup_date),
    INDEX idx_order_number (order_number)
);

--order_items table
CREATE TABLE order_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    unit_price DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id),
    INDEX idx_order (order_id),
    INDEX idx_product (product_id)
);

--inventory table
CREATE TABLE inventory (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT NOT NULL,
    date DATE NOT NULL,
    planned_quantity INT DEFAULT 0,
    actual_quantity INT DEFAULT 0,
    sold_quantity INT DEFAULT 0,
    remaining_quantity INT DEFAULT 0,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (product_id) REFERENCES products(id),
    UNIQUE KEY unique_product_date (product_id, date),
    INDEX idx_product_date (product_id, date),
    INDEX idx_date (date)
);


-- sample data
INSERT INTO categories (name, description) VALUES
('Sourdough Breads', 'Traditional sourdough breads with wild yeast fermentation'),
('Whole Grain', 'Breads made with whole grain flours'),
('Pastries', 'Sweet and savory baked goods'),
('Seasonal', 'Limited time seasonal offerings');

INSERT INTO products (name, description, price, category_id, prep_time_hours, is_featured) VALUES
('Classic Sourdough Loaf', 'Traditional white sourdough with crispy crust and tangy flavor', 8.50, 1, 48, TRUE),
('Whole Wheat Sourdough', 'Hearty whole wheat sourdough with seeds', 9.00, 2, 48, TRUE),
('Rye Sourdough', 'Dense, flavorful rye bread with caraway seeds', 9.50, 1, 48, FALSE),
('Sourdough Baguette', 'Crispy French-style baguette', 6.00, 1, 24, FALSE),
('Cinnamon Raisin Sourdough', 'Sweet sourdough with cinnamon and raisins', 10.00, 3, 48, TRUE),
('Sourdough Focaccia', 'Herb-topped flatbread perfect for sharing', 12.00, 3, 24, FALSE);

INSERT INTO users (email, password, first_name, last_name, role) VALUES
('admin@sourdoughbakery.com', '3Qershor+', 'Admin', 'User', 'ADMIN');

-- Insert Sample Customer
INSERT INTO users (email, password, first_name, last_name, phone) VALUES
('customer@example.com', '3Qershor+', 'John', 'Doe', '555-0123');


--testing queries
-- SELECT p.name, p.price, c.name as category, p.is_featured 
-- FROM products p 
-- JOIN categories c ON p.category_id = c.id 
-- WHERE p.is_available = TRUE;

-- SELECT o.order_number, o.status, o.total_amount, o.pickup_date,
--        p.name, oi.quantity, oi.unit_price
-- FROM orders o
-- JOIN order_items oi ON o.id = oi.order_id  
-- JOIN products p ON oi.product_id = p.id
-- WHERE o.user_id = 1;

-- SELECT p.name, i.date, i.planned_quantity, i.sold_quantity, i.remaining_quantity
-- FROM inventory i
-- JOIN products p ON i.product_id = p.id
-- WHERE i.date = CURDATE();