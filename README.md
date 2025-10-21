# Product Database - Spring Boot Multi-language Application

## 📋 Mô tả dự án
Ứng dụng quản lý sản phẩm đa ngôn ngữ sử dụng Spring Boot, MySQL và Thymeleaf. Hỗ trợ hiển thị sản phẩm bằng tiếng Anh và tiếng Việt.

## 🛠️ Cài đặt môi trường phát triển

### 1. Yêu cầu hệ thống
- **Java**: JDK 17 hoặc cao hơn
- **Maven**: 3.6+ 
- **MySQL**: 8.0+ (hoặc sử dụng Laragon)
- **IDE**: IntelliJ IDEA, Eclipse, hoặc VS Code
- **OS**: Windows 10/11, macOS, hoặc Linux

### 2. Cài đặt Java Development Kit (JDK)

#### Windows:
1. **Tải JDK 17:**
   - Truy cập: https://adoptium.net/
   - Chọn "Temurin 17 (LTS)" → Windows x64
   - Tải file `.msi` installer

2. **Cài đặt:**
   - Chạy file `.msi` đã tải
   - Làm theo hướng dẫn cài đặt
   - Ghi nhớ đường dẫn cài đặt (thường là `C:\Program Files\Eclipse Adoptium\jdk-17.x.x`)

3. **Cấu hình biến môi trường:**
   - Mở "System Properties" → "Environment Variables"
   - Thêm biến `JAVA_HOME` = đường dẫn JDK
   - Thêm `%JAVA_HOME%\bin` vào `PATH`

4. **Kiểm tra:**
   ```cmd
   java -version
   javac -version
   ```

#### macOS:
```bash
# Sử dụng Homebrew
brew install openjdk@17

# Hoặc tải từ Oracle/Adoptium
# Cấu hình JAVA_HOME
echo 'export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home' >> ~/.zshrc
echo 'export PATH=$JAVA_HOME/bin:$PATH' >> ~/.zshrc
source ~/.zshrc
```

#### Linux (Ubuntu/Debian):
```bash
# Cài đặt OpenJDK 17
sudo apt update
sudo apt install openjdk-17-jdk

# Cấu hình JAVA_HOME
echo 'export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64' >> ~/.bashrc
echo 'export PATH=$JAVA_HOME/bin:$PATH' >> ~/.bashrc
source ~/.bashrc
```

### 3. Cài đặt Maven

#### Windows:
1. **Tải Maven:**
   - Truy cập: https://maven.apache.org/download.cgi
   - Tải "Binary zip archive" (apache-maven-3.x.x-bin.zip)

2. **Giải nén:**
   - Giải nén vào `C:\Program Files\Apache\maven`

3. **Cấu hình biến môi trường:**
   - Thêm `MAVEN_HOME` = `C:\Program Files\Apache\maven`
   - Thêm `%MAVEN_HOME%\bin` vào `PATH`

4. **Kiểm tra:**
   ```cmd
   mvn -version
   ```

#### macOS:
```bash
# Sử dụng Homebrew
brew install maven

# Hoặc tải thủ công
# Tương tự Windows nhưng giải nén vào /usr/local/
```

#### Linux:
```bash
# Ubuntu/Debian
sudo apt install maven

# CentOS/RHEL
sudo yum install maven

# Kiểm tra
mvn -version
```

### 4. Cài đặt MySQL với Laragon (Windows)

#### Tải và cài đặt Laragon:
1. **Tải Laragon:**
   - Truy cập: https://laragon.org/download/
   - Tải phiên bản "Full" (bao gồm MySQL)

2. **Cài đặt:**
   - Chạy file installer
   - Làm theo hướng dẫn cài đặt
   - Khởi động Laragon

3. **Cấu hình MySQL:**
   - Mở Laragon → Start All
   - Mở phpMyAdmin: http://localhost/phpmyadmin
   - Tạo database mới: `ProductDB`

#### Hoặc cài đặt MySQL riêng:

**Windows:**
1. Tải MySQL Installer từ: https://dev.mysql.com/downloads/installer/
2. Chọn "MySQL Server" và "MySQL Workbench"
3. Cài đặt với cấu hình mặc định
4. Ghi nhớ root password

**macOS:**
```bash
# Sử dụng Homebrew
brew install mysql
brew services start mysql

# Hoặc tải MySQL Workbench từ website chính thức
```

**Linux:**
```bash
# Ubuntu/Debian
sudo apt install mysql-server mysql-client
sudo mysql_secure_installation

# CentOS/RHEL
sudo yum install mysql-server
sudo systemctl start mysqld
sudo mysql_secure_installation
```

### 5. Cài đặt IDE (Tùy chọn)

#### IntelliJ IDEA (Khuyến nghị):
1. Tải Community Edition: https://www.jetbrains.com/idea/download/
2. Cài đặt và cấu hình JDK 17
3. Cài đặt plugin Spring Boot

#### Eclipse:
1. Tải Eclipse IDE for Enterprise Java: https://www.eclipse.org/downloads/
2. Cài đặt Spring Tools 4 plugin

#### Visual Studio Code:
1. Tải VS Code: https://code.visualstudio.com/
2. Cài đặt extensions:
   - Extension Pack for Java
   - Spring Boot Extension Pack
   - MySQL

### 6. Cấu hình Database

#### Tạo database và bảng:
```sql
-- Kết nối MySQL và chạy các lệnh sau:

CREATE DATABASE ProductDB;
USE ProductDB;

-- 1. Bảng ngôn ngữ
CREATE TABLE language (
    id CHAR(2) PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

INSERT INTO language (id, name) VALUES
('EN', 'English'),
('VN', 'Tiếng Việt');

-- 2. Bảng sản phẩm
CREATE TABLE product (
    id INT AUTO_INCREMENT PRIMARY KEY,
    price DECIMAL(10,2) NOT NULL
);

-- 3. Bảng bản dịch sản phẩm
CREATE TABLE product_translation (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    language_id CHAR(2) NOT NULL,
    product_name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE,
    FOREIGN KEY (language_id) REFERENCES language(id) ON DELETE CASCADE
);
```

### 7. Cấu hình ứng dụng

#### Cập nhật file `application.properties`:
```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/ProductDB?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=your_mysql_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.properties.hibernate.format_sql=true

# Thymeleaf Configuration
spring.thymeleaf.cache=false
spring.thymeleaf.enabled=true
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html

# Server Configuration
server.port=8080
```

### 8. Chạy ứng dụng

#### Sử dụng Maven:
```bash
# Clone hoặc tải project
cd productdb

# Compile project
mvn clean compile

# Chạy ứng dụng
mvn spring-boot:run
```

#### Sử dụng IDE:
1. Import project vào IDE
2. Cấu hình JDK 17
3. Chạy class `ProductdbApplication.java`

### 9. Truy cập ứng dụng

- **URL**: http://localhost:8080
- **Trang chủ**: Hiển thị danh sách sản phẩm
- **Thêm sản phẩm**: http://localhost:8080/add-product

### 10. Kiểm tra cài đặt

#### Kiểm tra Java:
```bash
java -version
# Kết quả mong đợi: openjdk version "17.x.x"
```

#### Kiểm tra Maven:
```bash
mvn -version
# Kết quả mong đợi: Apache Maven 3.x.x
```

#### Kiểm tra MySQL:
```bash
mysql -u root -p
# Nhập password và kiểm tra kết nối
```

### 11. Xử lý lỗi thường gặp

#### Lỗi "JAVA_HOME not set":
- Kiểm tra biến môi trường JAVA_HOME
- Restart terminal/command prompt

#### Lỗi kết nối MySQL:
- Kiểm tra MySQL đang chạy
- Kiểm tra username/password trong application.properties
- Kiểm tra port 3306 có bị chặn không

#### Lỗi "Port 8080 already in use":
- Thay đổi port trong application.properties: `server.port=8081`
- Hoặc kill process đang sử dụng port 8080

### 12. Cấu trúc dự án

```
📁 productdb/                           # Root project directory
├── 📄 pom.xml                         # Maven configuration file
├── 📄 README.md                       # Project documentation
├── 📄 HELP.md                         # Spring Boot help file
├── 📄 mvnw                            # Maven wrapper (Unix/Linux)
├── 📄 mvnw.cmd                        # Maven wrapper (Windows)
│
├── 📁 src/                            # Source code directory
│   ├── 📁 main/                       # Main source code
│   │   ├── 📁 java/                   # Java source files
│   │   │   └── 📁 com/example/productdb/
│   │   │       ├── 📄 ProductdbApplication.java     # Main Spring Boot class
│   │   │       ├── 📄 ServletInitializer.java       # Servlet initializer
│   │   │       │
│   │   │       ├── 📁 entity/                        # JPA Entity classes
│   │   │       │   ├── 📄 Language.java             # Language entity
│   │   │       │   ├── 📄 Product.java              # Product entity
│   │   │       │   └── 📄 ProductTranslation.java   # Product translation entity
│   │   │       │
│   │   │       ├── 📁 repository/                   # Data Access Layer
│   │   │       │   ├── 📄 LanguageRepository.java   # Language repository
│   │   │       │   ├── 📄 ProductRepository.java    # Product repository
│   │   │       │   └── 📄 ProductTranslationRepository.java # Translation repository
│   │   │       │
│   │   │       ├── 📁 service/                      # Business Logic Layer
│   │   │       │   └── 📄 ProductService.java       # Product business logic
│   │   │       │
│   │   │       └── 📁 controller/                   # Web Layer
│   │   │           └── 📄 ProductController.java    # REST/Web controllers
│   │   │
│   │   └── 📁 resources/                            # Resources directory
│   │       ├── 📄 application.properties            # Application configuration
│   │       ├── 📁 static/                          # Static web resources (CSS, JS, images)
│   │       └── 📁 templates/                       # Thymeleaf templates
│   │           ├── 📄 index.html                   # Home page template
│   │           └── 📄 add-product.html             # Add product form template
│   │
│   └── 📁 test/                                     # Test source code
│       └── 📁 java/com/example/productdb/
│           └── 📄 ProductdbApplicationTests.java    # Spring Boot test class
│
└── 📁 target/                                       # Compiled classes and build output
    ├── 📁 classes/                                  # Compiled Java classes
    ├── 📁 generated-sources/                        # Generated source files
    └── 📁 maven-archiver/                          # Maven archive files
```

## 🏗️ Kiến trúc ứng dụng (Architecture)

```
┌─────────────────────────────────────────────────────────────────┐
│                        PRESENTATION LAYER                       │
├─────────────────────────────────────────────────────────────────┤
│  🌐 Web Browser                                                 │
│  ├── http://localhost:8080/          (Home page)               │
│  ├── http://localhost:8080/add-product  (Add product form)     │
│  └── http://localhost:8080/?lang=VN   (Language switching)     │
└─────────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                        CONTROLLER LAYER                         │
├─────────────────────────────────────────────────────────────────┤
│  🎮 ProductController.java                                      │
│  ├── @GetMapping("/")              → index()                   │
│  ├── @GetMapping("/add-product")   → addProductForm()          │
│  ├── @PostMapping("/add-product")  → addProduct()              │
│  └── @PostMapping("/delete-product/{id}") → deleteProduct()    │
└─────────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                         SERVICE LAYER                           │
├─────────────────────────────────────────────────────────────────┤
│  🔧 ProductService.java                                         │
│  ├── getProductsByLanguage()      → Get products by language   │
│  ├── getAllLanguages()            → Get all languages          │
│  ├── addProduct()                 → Add new product            │
│  ├── deleteProduct()              → Delete product             │
│  └── initializeData()             → Initialize sample data     │
└─────────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                        REPOSITORY LAYER                         │
├─────────────────────────────────────────────────────────────────┤
│  📊 JPA Repositories                                            │
│  ├── LanguageRepository           → Language data access       │
│  ├── ProductRepository            → Product data access        │
│  └── ProductTranslationRepository → Translation data access    │
└─────────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                          DATABASE LAYER                         │
├─────────────────────────────────────────────────────────────────┤
│  🗄️ MySQL Database (ProductDB)                                 │
│  ├── 📋 language table           (id, name)                    │
│  ├── 📋 product table            (id, price)                   │
│  └── 📋 product_translation table (id, product_id, language_id, │
│                                    product_name, description)   │
└─────────────────────────────────────────────────────────────────┘
```

## 🔄 Data Flow (Luồng dữ liệu)

```
1. 🌐 User Request
   ↓
2. 🎮 Controller receives request
   ↓
3. 🔧 Service processes business logic
   ↓
4. 📊 Repository queries database
   ↓
5. 🗄️ Database returns data
   ↓
6. 📊 Repository returns entities
   ↓
7. 🔧 Service returns processed data
   ↓
8. 🎮 Controller adds data to model
   ↓
9. 🎨 Thymeleaf renders template
   ↓
10. 🌐 Response sent to browser
```

## 📦 Dependencies (Maven)

```
📦 Spring Boot Dependencies
├── 🍃 spring-boot-starter-web        # Web MVC framework
├── 🍃 spring-boot-starter-data-jpa   # JPA/Hibernate ORM
├── 🍃 spring-boot-starter-thymeleaf  # Template engine
├── 🍃 spring-boot-starter-test       # Testing framework
├── 🐬 mysql-connector-j              # MySQL database driver
└── 🧪 spring-boot-maven-plugin       # Maven plugin
```

## 🎯 Key Features Implementation

```
🎯 Multi-language Support
├── Language Entity (EN, VN)
├── ProductTranslation Entity
├── Language switching in UI
└── Dynamic content loading

🎯 CRUD Operations
├── Create: Add new products with translations
├── Read: Display products by language
├── Update: (Can be extended)
└── Delete: Remove products

🎯 Web Interface
├── Bootstrap 5 responsive design
├── Thymeleaf template engine
├── Form validation
└── User-friendly navigation
```

## 🚀 Tính năng

- ✅ Hiển thị danh sách sản phẩm đa ngôn ngữ
- ✅ Chuyển đổi ngôn ngữ (English/Tiếng Việt)
- ✅ Thêm sản phẩm mới với bản dịch
- ✅ Xóa sản phẩm
- ✅ Giao diện responsive với Bootstrap 5

## 📞 Hỗ trợ

Nếu gặp vấn đề trong quá trình cài đặt, vui lòng kiểm tra:
1. Phiên bản Java (phải là JDK 17+)
2. Kết nối MySQL
3. Cấu hình application.properties
4. Logs của ứng dụng trong console
#   p r o d u c t d b  
 