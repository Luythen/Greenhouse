# GreenHouse

### Project descripton
Easy way to manage your greenhouse while also begin able to see youre how moist youre soil is so you can know when you need to water youre plants

### AI Usage
I have use claude AI to troubleshoot my code when it crashes if i can't find the problem and this means that i paste the error stack trace into claude

### Known bug
* Sometimes the websocket connect before the `POST` /greenhouse/dashboard finsihed so the websocket will subscribe to null but on refresh it work fine
* Arduino have only been tested to send data to the deployed version which means it probably wont work locally (not tested)

### Good to know
* When you set the crate sensor Id not write A0 beacuse A0 is not a correct id and the same goes for the other analog ids.
```
A0 = 14
A1 = 15
A2 = 16
A3 = 17
A4 = 18
A5 = 19
```

# Arduino
### Technical stack
* Arduino uno rev4
* [Capaitive Soil Moisture Sensor v1.2](https://www.amazon.se/dp/B08GCRZVSR?ref=ppx_yo2ov_dt_b_fed_asin_title)
* Wires
* Breadboard

## Configuration
Copy this and create a file named settings.h
```
#define SECERT_SSID "--wifi--"
#define SECERT_PASSWORD "--password--"
#define SECRET_API_KEY "--api-key--"
#define SECRET_SERVER_IP "--server-ip---"
#define PORT 443
```

Air and water value most be calibrated to your soil moisture sensor and you must also declare which analog pins you are using for youre sensors. <br />
You will find this in the moisture.h file.
```
const int AIR_VALUE[] = {505, 510};
const int WATER_VALUE[] = {250, 250};

const int PINS[] = {A0, A1};
```

# Frontend
### Technical stack
* Vite
* React
* Sockjs
* [Nodejs](https://nodejs.org/en)
  
## Configuration
```
VITE_API_URL=--server-url--
```

## Get started
* First clone the project
* Then renamed .env.example to .env
* Change the ``VITE_API_URL` to the server url
* Then open a `cmd` in the GreenHouseFrontend folder
* Then run `npm install`
* Then run `npm run dev`

# Server
## Technical stack
* Spring boot 4.1.1
* JsonWebTokens
* Mongodb
* Java 21
* Websocket

## Configuration
You can find these fillable field in the .env.example <br />
And for extra security don't not have the samme access token secret as the refresh token secret and also use a random UUID for your jwt token. <br />
JWT token Issuer can be whatever.
```
ACCESS_TOKEN_SECRET_KEY=--Access-Token--
REFRESH_TOKEN_SECRET_KEY=--Refresh-Token--
JWT_TOKEN_ISSUER=--Token-Issur--
MONGO_URI=--Mongo_db_connection_string--
ALLOWED_ORIGIN=--Frontend-url---
```

## Get started
* First clone the project (if you have not done it yet)
* Then renamed the .env.example to .env and fill in the fields
* Then change `--Access-Token--` to a random uuid
* Then change `--Refresh-Token--` to a random uuid not the same as access token
* Then change `--Mongo_db_connection_string--` to your mongodb connection string
* Then change `--Frontend-url---` to the url for the frontend
* Then either open a `cmd` or VS Code and then type `./mvnw spring-boot:run` or click on the play button

## User endpoints
### Auth
* `POST` /auth/register - register a new user
   * Statuscode: `409`, `400` and `200`
     ```
       {
         Statuscode: 200,
         message: "Registreringen lyckades" // Registration successful
       }
     ```
     ```
       {
         Statuscode: 400,
         message: "Tomt fält" // Field empty
       }
     ```
     ```
       {
         Statuscode: 409,
         message: "E-postadressen används redan" // Email already in use
       }
     ```
   * Expected `POST` data
      ```
       {
         firstName: "--name--",
         lastName: "--lastName--",
         email: "--email--",
         password: "--password--" // refreshtoken is empty
       }
     ```
* `POST` /auth/login - login a user
   * Statuscode: `400`, `401` and `200`
     ```
       {
          "accessToken": "--access-token--",
          "firstName": "--name--",
          "lastName": "--lastName--",
          "role": "User"
      }
     ```
     ```
       {
         Statuscode: 400,
         message: "E-postadressen är ej giltig" // Email is invalid
       }
     ```
     ```
       {
         Statuscode: 401,
         message: "Lösenord matchar inte" // Wrong password
       }
     ```
   * Expected `POST` data
      ```
       {
         email: "--email--",
         password: "--password--" // refreshtoken is empty
       }
     ```
* `POST` /auth/refresh - get a new accesstoken through refreshtoken
   * Statuscode: `401` and `200`
     ```
       {
          "accessToken": "--access-token--",
          "firstName": "--name--",
          "lastName": "--lastName--",
          "role": "User"
      }
     ```
     ```
       {
         Statuscode: 401,
         message: "refreshtoken är tom" // refreshtoken is empty
       }
     ```
* `POST` /auth/logout - logout a user and clears user cookies
   * Required header: `Authorization`
   * Statuscode: `200`
### GreenHouse
* `GET` /greenhouse/dashboard - get user greenhouse
   * Required header: `Authorization`
   * Statuscode: `400` and `200`
     ```
       {
          "id": "--greenhouseId--",
          "name": "--FirstName-- GreenHouse",
          "crates": [
              {
                  "id": "--crate-id--",
                  "label": "crate 11",
                  "moistureSensorConnectionId": "14",
                  "plants": [
                      {
                          "RemovedAt": "2026-10-24T16:20:45.873Z",
                          "active": false,
                          "id": "--plant-id--",
                          "plantCrateId": "--crate-id--",
                          "plantType": "Tomato",
                          "plantedAt": "2026-10-24T16:17:19.962Z"
                      }
                  ]
              },
              {
                  "id": "--crate-id--",
                  "label": "Crate 2",
                  "moistureSensorConnectionId": "15",
                  "plants": [
                      {
                          "RemovedAt": "2026-10-24T21:03:23.476Z",
                          "active": false,
                          "id": "--plant-id--",
                          "plantCrateId": "--crate-id--",
                          "plantType": "Cucumber",
                          "plantedAt": "2026-10-24T16:17:27.578Z"
                      },
                      {
                          "RemovedAt": null,
                          "active": true,
                          "id": "--plant-id--",
                          "plantCrateId": "--crate-id--",
                          "plantType": "Tomato",
                          "plantedAt": "2026-10-29T12:21:10.246Z"
                      }
                  ]
              },
              {
                  "id": "--crate-id--",
                  "label": "Crate 123",
                  "moistureSensorConnectionId": null,
                  "plants": []
              }
          ]
      }
     ```
     ```
       {
         Statuscode: 400,
         message: "Kunde inte hitta ett växthus som tillhör användaren" // Could not find a greenhouse belonging to the user
       }
     ```
### PlantCrate
* `POST` /plantcrate/add - add a new plantable crate
   * Required header: `Authorization`
   * Statuscode: `200`
     ```
       {
          "id": "--id--",
          "label": "Crate 1",
          "moistureSensorConnectionId": null,
          "plants": []
      }
     ```
* `POST` /plantcrate/remove/{id} - remove plant crate by id
   * Required header: `Authorization`
   * Statuscode: `400`, `401` and `200`
     ```
       {
         Statuscode: 200,
         message: "Lådan har blivit bort tagen" // Plantcrate have successfully been removed
       }
     ```
     ```
       {
         Statuscode: 400,
         message: "Kunde inte hitta någon låda med den idn" // Couldn't find crate with that idn
       }
     ```
     ```
       {
         Statuscode: 401,
         message: "Lådan tillhör ej växthuset" // The crate does not belong to the greenhouse.
       }
     ```
* `PUT` /plantcrate/edit/{id} - edit plant crate by id
   * Required header: `Authorization`
   * Statuscode: `400`, `401` and `200`
     ```
       OK Response
       {
          "id": "--id--",
          "label": "Låda 11",
          "moistureSensorConnectionId": "14",
          "plants": [---ALL-PLANTS-ASSOCIATE-WITH-THE-CRATE---]
      }
     ```
     ```
       {
         Statuscode: 400,
         message: "Kunde inte hitta någon låda med den idn" // Couldn't find a crate with that idn
       }
     ```
     ```
       {
         Statuscode: 401,
         message: "Lådan tillhör ej växthuset" // The crate does not belong to the greenhouse.
       }
     ```
   * Expected `PUT` data
     ```
        {
          label: "--new-name--",
          sensorId: "15"
        } 
     ```
### Plant
* `POST` /plant/vegetable - plant a vegetable in the crate
   * Required header: `Authorization`
   * Statuscode: `400`, `401`, `409` and `200`
     ```
       OK Response
       {
        "RemovedAt": null,
        "active": true,
        "id": "--id--",
        "plantCrateId": "--crate-Id--",
        "plantType": "Tomato",
        "plantedAt": "2026-10-29T12:21:10.246286763Z"
        }
     ```
     ```
       {
         Statuscode: 400,
         message: "Kunde inte hitta en växthus låda med den idn" // Couldn't find a greenhouse crate with that idn
       }
     ```
     ```
       {
         Statuscode: 401,
         message: "Du har inte tillgång till denna låda" // You don't have access to that crate
       }
     ```
     ```
       {
         Statuscode: 409,
         message: "Lådan innehåller redan en växt" // Crate already contains a plant
       }
     ```
    * Expected `POST` data
      ```
        {
          crateId: "--crate-Id--",
          plantType: "Cucumber"
        } 
      ```
* `POST` /plant/unplant/{id} - remove plant from crate
   * Required header: `Authorization`
   * Statuscode: `400`, `401` and `200`
     ```
       {
         Statuscode: 200,
         message: "Växt har blivit rensad" // The plant has been removed
       }
     ```
     ```
       {
         Statuscode: 400,
         message: "Kunde inte hitta en växthus låda med den idn" // Couldn't find a greenhouse crate with that idn
       }
     ```
     ```
       {
         Statuscode: 401,
         message: "Du har inte tillgång till denna låda" // You don't have access to that crate
       }
     ```
### API/Key
* `POST` /key/generate - generate a api key used so the arduino can send correct data to correct user
   * Required header: `Authorization`
   * Statuscode: `400` and `200`
     ```
       {
         apiKey: "--JWT-API-KEY--"
       }
     ```
     ```
       {
         Statuscode: 400,
         message: "Kunde inte hitta ett växthus som tillhör användaren" // Could not find a greenhouse belonging to the user
       }
     ```
## Arduino endpoint
### Soilmoisture measurments
* `POST` /soil/add - register new soilmoisture measurements
   * Required header: `X-API-KEY`
   * Statuscode: `400` and `200`
     ```
       {
         Statuscode: 200,
         message: "Fuktmätningar har registrerats" // Moist measurement have been registerd
       }
     ```
     ```
       {
         Statuscode: 400,
         message: "Kunde inte hitta ett växthus med den idn" // Could not find a greenhouse with that id
       }
     ```
     ```
       {
         Statuscode: 400,
         message: "Listan är tom" // The list is empty
       }
     ```
   * Expected `POST` data
     ```
     [
      { 
          "sensorId": "14",
          "measurement": 29
      },
      { 
          "sensorId": "15",
          "measurement": 25
      },
      { 
          "sensorId": "16",
          "measurement": 23
      },
      { 
          "sensorId": "17",
          "measurement": 27
      }
     ]
     ```
## WebSocket
* `SUBSCRIBE` /topic/soil/{greenhouseId}
   * Example data
   ```
   [
    { 
        "sensorId": "14",
        "measurement": 29
    },
    { 
        "sensorId": "15",
        "measurement": 25
    },
    { 
        "sensorId": "16",
        "measurement": 23
    },
    { 
        "sensorId": "17",
        "measurement": 27
    }
   ]
   ```
