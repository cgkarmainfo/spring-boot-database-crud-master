Spring Boot Product Management
This Spring Boot project facilitates product management through CRUD APIs and includes an optional approval queue. It empowers users to execute operations such as create, read, update, and delete on products. Additionally, the system supports managing products through an approval queue.

Table of Contents
Prerequisites
Getting Started
Installation
Configuration
Usage
API Endpoints
Approval Queue
Development
Testing
Deployment
Contributing
License
Prerequisites
Before commencing, ensure that the following prerequisites are met:

Java Development Kit (JDK) 8 or higher is installed.
Apache Maven is installed for building the project.
[Optional] A database (e.g., MySQL, PostgreSQL) is configured for storing product data.
[Optional] A configured approval queue system (e.g., JMS) if the approval queue feature is intended for use.
Getting Started
Installation
Clone the repository:

bash
Copy code
git clone https://github.com/yourusername/spring-boot-product-management.git
Build the project:

bash
Copy code
cd spring-boot-product-management
mvn clean install
Configuration
Open the application.properties or application.yml file and configure the database connection properties.

[Optional] If using an approval queue, configure the queue system (e.g., JMS) properties.

Usage
API Endpoints
1. List Active Products
   Endpoint: GET /api/products
   Description: Get the list of active products sorted by the latest first.
2. Search Products
   Endpoint: GET /api/products/search
   Parameters: productName (optional), minPrice (optional), maxPrice (optional), minPostedDate (optional), maxPostedDate (optional)
   Description: Search for products based on the given criteria (product name, price range, and posted date range).
3. Create a Product
   Endpoint: POST /api/products
   Request Body: Product object (name, price, status)
   Description: Create a new product, but the price must not exceed $10,000. If the price is more than $5,000, the product should be pushed to the approval queue.
4. Update a Product
   Endpoint: PUT /api/products/{productId}
   Request Body: Product object (name, price, status)
   Description: Update an existing product, but if the price is more than 50% of its previous price, the product should be pushed to the approval queue.
5. Delete a Product
   Endpoint: DELETE /api/products/{productId}
   Description: Delete a product, and it should be pushed to the approval queue.
   Approval Queue
1. Get Products in Approval Queue
   Endpoint: GET /api/products/approval-queue
   Description: Get all the products in the approval queue, sorted by request date (earliest first).
2. Approve a Product
   Endpoint: PUT /api/products/approval-queue/{approvalId}/approve
   Description: Approve a product from the approval queue. The product state should be updated, and it should no longer appear in the approval queue.
3. Reject a Product
   Endpoint: PUT /api/products/approval-queue/{approvalId}/reject
   Description: Reject a product from the approval queue. The product state should remain the same, and it should no longer appear in the approval queue.
   Development
   To contribute to the development of this project, follow these steps:

Fork the repository.
Create a new branch for your feature or bug fix.
Commit your changes.
Push the branch to your fork.
Open a pull request.
Testing
Execute tests using the following command:

bash
Copy code
mvn test
Deployment
Deploy the Spring Boot application to your preferred environment.

Contributing
Contributions are welcome! Feel free to open issues and pull requests.

License
This project is licensed under the MIT License.