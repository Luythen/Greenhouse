# GreenHouse

### Project descripton


# Server
## Technical stack
* Spring boot 4.1.1
* JsonWebTokens
* Mongodb
* Java 21

## Configuration
You can find these fillable field in the .env.exemple
```
ACCESS_TOKEN_SECRET_KEY=--Access-Token--
REFRESH_TOKEN_SECRET_KEY=--Refresh-Token--
JWT_TOKEN_ISSUER=--Token-Issur--
MONGO_URI=--Mongo_uri--
ALLOWED_ORIGIN=--Frontend-url---
```

## User endpoints
### Auth
* `POST` /auth/register - register a new user
   * Statuscode: `409`, `400` and `200`
* `POST` /auth/login - login a user
   * Statuscode: `400` and `200`
* `POST` /auth/refresh - get a new accesstoken through refreshtoken
   * Statuscode: `401` and `200`
* `POST` /auth/logout - logout a user and clears user cookies
   * Statuscode: `200`
### GreenHouse
* `GET` /greenhouse/dashboard - get user greenhouse
   * Statuscode: `400` and `200`
### PlantCrate
* `POST` /plantcrate/add - add a new plantable crate
   * Statuscode: `400` and `200`
* `POST` /plantcrate/remove/{id} - remove plant crate by id
   * Statuscode: `400`, `401` and `200`
* `PUT` /plantcrate/edit/{id} - edit plant crate by id
   * Statuscode: `400`, `401` and `200`
### Plant
* `POST` /plant/vegetable - plant a vegetable in the crate
   * Statuscode: `400`, `401` and `200`
* `POST` /plant/unplant/{id} - remove plant from crate
   * Statuscode: `400`, `401` and `200`
### API/Key
* `POST` /key/generate - generate a api key used so the arduino can send correct data to correct user
   * Statuscode: `400` and `200`
## Arduino endpoint
### Soilmoisture measurments
* `POST` /soil/add - register new soilmoisture measurements
   * Statuscode: `400` and `200`
