# sourdough-bakery
A full-stack e-commerce application for a sourdough bakery, enabling customers to browse products, place orders, and manage pickups while providing administrators with inventory and order management capabilities.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.0-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![Maven](https://img.shields.io/badge/Maven-3.x-red)
![License](https://img.shields.io/badge/License-MIT-yellow)

## Features

### Current Features
- **User Management**
  - User registration and authentication
  - Role-based access (Customer/Admin)
  - User profile management

- **Product Management**
  - Browse products by category
  - Search products by name
  - Filter by availability and featured items
  - Product CRUD operations (Admin)

- **Category System**
  - Multiple product categories
  - Category-based filtering

### Upcoming Features
-  JWT-based authentication
-  Shopping cart functionality
-  Order placement and tracking
-  Inventory management
-  Email notifications
-  Payment integration
- Responsive frontend

##  Tech Stack

### Backend
- **Framework:** Spring Boot 3.5.0
- **Language:** Java 17
- **Security:** Spring Security 6
- **ORM:** Spring Data JPA (Hibernate)
- **Database:** MySQL 8.0
- **Build Tool:** Maven
- **Additional Libraries:**
  - Lombok (reducing boilerplate)
  - JWT (io.jsonwebtoken 0.13.0)
  - Bean Validation
  - Thymeleaf (templating)
  - Spring Mail

##  Project Structure

```
sourdough-bakery/
├── backend/
│   └── sourdough/
│       ├── src/
│       │   ├── main/
│       │   │   ├── java/org/example/sourdough/
│       │   │   │   ├── controller/        # REST controllers
│       │   │   │   ├── model/             # Entity classes
│       │   │   │   ├── repository/        # JPA repositories
│       │   │   │   └── service/           # Business logic
│       │   │   └── resources/
│       │   │       └── application.properties
│       │   └── test/
│       └── pom.xml
├── database/
│   └── dbschema.sql                       # Database schema & sample data
├── .gitignore
├── LICENSE
└── README.md
```

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/aminatpwk/sourdough-bakery.git
   cd sourdough-bakery
   ```

2. **Set up the database**
   ```bash
   # Log into MySQL
   mysql -u root -p
   
   # Create database
   CREATE DATABASE sourdough-project;
   
   # Run schema
   mysql -u root -p sourdough-project < database/dbschema.sql
   ```

3. **Configure application properties**
   
   Update `backend/sourdough/src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/sourdough-project
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

4. **Build and run the application**
   ```bash
   cd backend/sourdough
   mvn clean install
   mvn spring-boot:run
   ```

5. **Access the application**
   
   The API will be available at `http://localhost:8080`

## API Endpoints

### Authentication
- `POST /api/v1/auth/register` - Register new user
- `POST /api/v1/auth/login` - Login user

### Users
- `GET /api/v1/users` - Get all users

### Products
- `GET /api/v1/products` - Get all products
- `POST /api/v1/products` - Add new product
- `PUT /api/v1/products/{id}` - Update product
- `DELETE /api/v1/products/{id}` - Delete product
- `GET /api/v1/products/by-category/{categoryId}` - Get products by category
- `GET /api/v1/products/availability?available=true` - Filter by availability
- `GET /api/v1/products/featured?featured=true` - Get featured products
- `GET /api/v1/products/search?q=sourdough` - Search products

## Database Schema

### Main Tables
- **users** - Customer and admin accounts
- **categories** - Product categories
- **products** - Bakery products
- **orders** - Customer orders
- **order_items** - Items within orders
- **inventory** - Daily inventory tracking

### Sample Data
The database schema includes sample data:
- 4 product categories
- 6 sample products
- Admin and customer test accounts

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

_treat people with kindness :)_
