# API Challenge

This Spring Boot application is designed to accept requests with text parameters and return relevant books and musics based on the input term. It utilizes the iTunes API for musics and the Google Books API for books, providing a simple and efficient way to search for media related to specific terms.

#### Prerequisites
- Java 17 or newer
- Maven (if not using the Maven Wrapper included)
- Docker (optional, for containerization)
- Internet access for API calls

#### Running the Application Locally

1. **Clone the repository or extract the provided ZIP file** to your local machine.

2. **Navigate to the application directory** where the `pom.xml` file is located.

3. **Build the application** using the Maven Wrapper included in the project. Open a terminal or command prompt in the project directory and run:
   ```shell
   ./mvnw clean install
   ```
   If you are on Windows, use:
   ```shell
   mvnw.cmd clean install
   ```

4. **Run the application** with:
   ```shell
   ./mvnw spring-boot:run
   ```
   On Windows:
   ```shell
   mvnw.cmd spring-boot:run
   ```

The application will start, and you can access it typically via `http://localhost:8080` on your web browser or API testing tool like Postman. Use the appropriate endpoints to send requests with text parameters.

#### Running the Application Using Docker (Optional)

If you prefer to containerize the application using Docker, follow these steps:

1. **Build the Docker image**. Make sure you are in the project root directory and run:
   ```shell
   docker build -t api-challenge .
   ```

2. **Run the container**. After the image is built, start a container with:
   ```shell
   docker run -p 8080:8080 api-challenge
   ```

This will run the application inside a Docker container, accessible in the same way as the local setup.

#### Using the Application

To use the application, send a request to `http://localhost:8080/search?input=your_search_input`, replacing `your_search_input` with the text you're interested in. The application will return a JSON response with up to 5 books and 5 musics related to the search term, including titles and authors/artists.

#### Configuration
Before running the application, you must configure the limits for the downstream API calls in the `application.properties` file. These settings control the maximum number of books and albums returned by the application:
   
   ```shell
   # Set the maximum number of books to return from the Google Books API
   google.books.api.limit=5
   
   # Set the maximum number of albums to return from the iTunes API
   apple.musics.api.limit=5
   ```

#### Swagger UI
To access the Swagger UI for API documentation and testing, navigate to `http://localhost:8080/swagger-ui.html` in your web browser.

#### Health Check
The application's health check is available at `http://localhost:8080/actuator/health`. This endpoint provides basic application health information.

#### Metrics
To access various metrics about the application, visit `http://localhost:8080/actuator/metrics`. Specific metrics can be accessed by appending the metric name to the URL, for example to see upstream response time metric call, `http://localhost:8080/actuator/metrics/services.upstream.response.time`.