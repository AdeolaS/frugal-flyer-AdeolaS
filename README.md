<div align="center">
<img src="assets\FrugalFlightsPlane.png" alt="logo" width="200" height="auto" />
<h1>Frugal Flights API</h1>
<p>Helps you find flights to the places you want to go for prices that won't empty your pocket!</p>

![Static Badge](https://img.shields.io/badge/LANGUAGE-JAVA-brightgreen)

___


</div>

### :rocket: Features

- Search for flights with filters for:
    - Arrival Airport
    - Climate
    - Tag
    - Budget
    - Departure date (With flexible option)
- Price anomaly detection
- Random flight suggestions

___

<!-- TechStack -->
### :space_invader: Tech Stack
<li><a href="https://www.java.com/en/">Java</a></li>
<li><a href="https://maven.apache.org/">Maven</a></li>
<li><a href="https://www.mysql.com/">MySQL</a></li>
<li><a href="https://spring.io/projects/spring-boot">Spring Boot</a></li>
<li><a href="https://springdoc.org/">Spring Data JPA</a></li>
<li><a href="https://spring.io/projects/spring-data-jpa">Springdoc</a></li>
<li><a href="https://swagger.io/specification/">Swagger/Open API</a></li>
<li><a href="https://junit.org/">JUnit5</a></li>
<li><a href="https://site.mockito.org/">Mockito</a></li>

___

<!-- Documentation -->
### :book: API Documentation

Swagger UI is available at:

```bash
  http://localhost:8080/swagger-ui/index.html
```
(Make sure that the API is running before you navigate here.)
<div align="center"> 
  <img src="assets\frugalFlyerSwaggerScreenshot.png" alt="screenshot" />
</div>

Java Doc:

Detailed class and method documentation can be found in the Javadoc here: 

<a href="https://adeolas.github.io/frugal-flyer-AdeolaS/">https://adeolas.github.io/frugal-flyer-AdeolaS/</a>

<div align="center"> 
  <img src="assets\frugalFlyerJavaDocScreenshot.png" alt="screenshot" />
</div>

___
   
<!-- Running Tests -->
### :test_tube: Running Tests

To run tests, run the following command

```bash
  ./mvnw clean test
```

___
   

<!-- Run Locally -->
### :running: Getting Started

1. Clone the project

```bash
  git clone https://github.com/AdeolaS/frugal-flyer-AdeolaS.git
```

2. Go to the project directory

```bash
  cd frugal-flyer
```

3. Install dependencies

```bash
  ./mvnw clean install
```

4. Start the server

```bash
  ./mvnw spring-boot:run
```

___
   

<!-- Example Endpoints -->
### :microscope: Example Enpoints

```http
  GET /api/flights/cheap-flights
```

Query Parameters:

- `departureAirport` (required)
- `arrivalAirport` (required)
- `threshold` (default = 0.5)

Example Request:

```sql
    GET /api/flights/cheap-flights?departureAirport=LHR&arrivalAirport=CDG
```

---

<!-- Error Handling -->
### :warning: Error Handling

The API returns structures error responses:

```json
{
    "message": "null - The threshold cannot be a negative value.",
    "status": 400,
    "timeOfError": "2025-09-02T01:40:29.2960159"
}
```

```json
{
    "message": "null - Invalid Airport code: CD. Please insert an airport that is recognised by this application.",
    "status": 404,
    "timeOfError": "2025-09-02T01:41:18.3871361"
}
```