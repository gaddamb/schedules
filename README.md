# schedule-service

Maintains schedule of a zone. The application is built on springboot framework and is written on Java. The database has been used as h2 (in memory database) and can be configured to any sql db.

## Installation

Java version 17 with maven has been used. 
to run tests : 
```bash
mvn test
```

to run application :
```bash
mvn spring-boot:run
```

to invoke the api: You can use postman or any tool to hit the api. you can check the port configured for the application in src\main\resources\application.properties.
```bash
http://localhost:8080/
```

the database tables and data can be viewed on the console at below url:
```bash
http://localhost:8080/h2-console/
```
username / password to log in to the console are defined in src\main\resources\application.properties. Have a look at the below properties in the file:
```bash
spring.datasource.username
spring.datasource.password
```
## Database design
![image](https://user-images.githubusercontent.com/36534803/186546175-f15b356d-6d87-485f-ba12-ecdaf50731c6.png)


## end points
1 - Post (/schedule/) - create schedule - HTTP Response Code: **200**
request :
```javascript
    HTTP/1.1 200
    Content-Type: application/json
    {
        "name" : "scheduleA",
        "schedule" : ["from 10:00 to 11:00 each Monday", "from 13:00 to 13:30 each weekday", "from 09:00 to 09:30 everyday"]
    }
```

response:
```javascript
    {
        "name": "scheduleA",
        "schedule": [
            "from 10:00 to 11:00 each Monday",
            "from 13:00 to 13:30 each weekday",
            "from 09:00 to 09:30 everyday"
        ],
        "error": null
    }
```
2 - Post (/zone/) - create zone - HTTP Response Code: **200**
request :
```javascript
    HTTP/1.1 200
    Content-Type: application/json
    { }
```

response:
```javascript
    {
    "zoneId": 1,
    "schedule": null,
    "offender": null
}
```
3 - Post (/offender/) - create offender - HTTP Response Code: **200**
request :
```javascript
    HTTP/1.1 200
    Content-Type: application/json
    {
        "firstName" : "Alberto",
        "lastName": "Isaq"
    }
```

response:
```javascript
{
    "offenderId": 1,
     "firstName" : "Alberto",
     "lastName": "Isaq"
}
```
4 - Post (/zone/configureSchedule/) - configure zone to schedule - HTTP Response Code: **200**
request :
```javascript
    HTTP/1.1 200
    Content-Type: application/json
    {
        "zoneId" : "1",
        "scheduleName" : "scheduleA"
    }
```

response:
```javascript
    {
        "scheduleId": 2,
        "name": "scheduleA",
        "zone": {
            "zoneId": 1,
            "schedule": null,
            "offender": null
        },
        "createDate": "2022-08-24T23:12:54.741+00:00",
        "modifyDate": "2022-08-24T23:13:12.215+00:00"
    }
```
5 - Post (/zone/verifyStatus/) - verify zone status on given date - HTTP Response Code: **200**
request :
```javascript
    HTTP/1.1 200
    Content-Type: application/json
    {
        "zoneId" : "1",
        "scheduleName" : "scheduleA",
        "date" : "20220829100205"
    }
```

response:
```javascript
    {
        "zoneId": "1",
        "allowedSchedules": [
            "from 10:00 to 11:00 each Monday"
        ],
        "date": "20220829100205",
        "isOffenderAllowedAtZoneOnDate": true,
        "error": null
    }
```
## Things to do
1. password encryption logic using spring security
2. updating schedule based on exception
3. updating schedule in general
4. not allowing updates on past date
5. verify zone status for schedules that are on intervals
