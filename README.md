# cinema app
### Backend system for managing network of cinemas with multiple rooms.  
#### Project is almost ready, some refactorings must be done, security layer in API and proper documentation
### Planned finish - 20 september  

The data is stored in MySQL database, communication with frontend will be achieved through REST API.  
Till end of the year I will make complete frontend layer (actually I make Javascript course) and deployment on AWS.


Technology stack:  

- Java 16
- Spring framework
- Jdbi
- MySQL
- Spark web framework
- Lombok
- Google Guava

Build tool: Maven

Testing:

- Junit 5
- AssertJ
- Mockito

## Building and running of project

What you need: Java 16, Maven, Docker  
  
After downloading of source files, project must be compiled to jar file with command "mvn clean install".
Whole environment (app + MySQL database) can be build with docker compose with command:  

- docker compose up -d --build

By starting of containers main class App is launched. At the moment main class loaded sample data to database that is used to test new features.  
In the future App class will be starting rest api controllers.


## Some statistics

Progress in development: ~80%  
Lines of code: 9600  
Classes: 145  
Unit test: 148  
Test-coverage: ~65%  

## Implemented domain objects

- Address  
- Cinema  
- Cinema Room  
- Movie  
- Screening  
- Seat  
- Ticket  
- User  


## Working features at the moment

### REST Api (port 8000):

Cinema routing:

- adding new cinema (POST method, url - /cinemas)  
- finding all cinemas (GET method, urls - /cinemas)
- finding cinemas by city (GET, url - /cinemas/city/:city)  
- finding cinema by name (GET, url - /cinemas/name/:name)

Screening routing:

- adding new screening  
- finding screenings by date  
- updating existing service  
- searching screenings by keyword

Movies  
  
- adding new movie  
- displaying all movies  
- finding movies by genre  
- deleting movie  
- updating movie  
- finding movie by title  

Users

- creating new user  
- displaying all users
- updating user  
- deleting user  
- finding user by mail  
- finding user by username  

### Services

Cinemas service:

- creating new cinema with multiple rooms
- adding new rooms to existing cinema
- searching cinema by name
- searching all cinemas from city

Movies service:

- adding new movie
- searching movie by name
- finding all movies from given genre

Screenings service:  

Finding of screening operates on view that contains most important info about screening fetched from tables using join statement.   

- adding new screening with availability checking
- finding screening by keyword
- finding screening by movie
- finding screening by date  
- finding screening by cinema  
- deleting screening  
- updating screening  

Tickets service:  

- creating tickets with availability checking for given screening
- generating map with booked seat of given screening
- deleting ticket  

Users service:  

- creating new user with password encryption
- updating user  
- deleting user  

## NEXT TASKS TO DO  

- add tickets routing 
- fix deleting cinema !!
- some rafctorings (look at TODO in project)
- write all javadoc stubs  
- security of REST API
- postman documentation
