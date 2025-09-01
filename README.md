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
<div align="center"> 
  <img src="assets\frugalFlyerSwaggerScreenshot.png" alt="screenshot" />
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
  git https://github.com/AdeolaS/frugal-flyer-AdeolaS.git
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
