# REST-assured API Framework

[![CI](https://github.com/dilshana-dasthakeer/restassured-api-framework/actions/workflows/ci.yml/badge.svg)](https://github.com/dilshana-dasthakeer/restassured-api-framework/actions/workflows/ci.yml)
![Java 17](https://img.shields.io/badge/Java-17-orange)
![REST-assured 5](https://img.shields.io/badge/REST--assured-5.5-green)
![TestNG](https://img.shields.io/badge/TestNG-7.11-red)
![License: MIT](https://img.shields.io/badge/License-MIT-blue)

An API test framework in Java for a REST service: full create, read, update and delete coverage, response contract checks, security cases and automatic cleanup.

It tests the public [Restful Booker](https://restful-booker.herokuapp.com/apidoc/index.html) hotel booking API.

## What it shows

| Area | How it is done |
|---|---|
| **Typed models** | Requests and responses are Java records, so tests assert on objects, not on strings or JSON paths. |
| **Client layer** | `BookingClient` has one method per endpoint and returns the raw response. The same client serves happy-path and negative tests, and tests hold no URLs. |
| **Lifecycle tests** | Create then read back, full update, partial update, delete then confirm it is gone. |
| **Contract test** | A JSON Schema checks field names, types and date format, so a changed response shape fails even when values look right. |
| **Security and negative cases** | Wrong credentials, update, patch and delete without a token, unknown id, and a malformed request. |
| **Search** | Filtering by name finds the right booking and an unknown name returns nothing. |
| **Safe on a shared API** | Test data uses unique names, and every booking a test creates is deleted afterwards, even if the test fails. |
| **Suites** | `smoke` is a small gate, `regression` is everything. Both are TestNG suite files selected by `-Dsuite`. |
| **CI** | GitHub Actions: smoke gate, then regression. Runs on every push, pull request and nightly. Reports are uploaded as artifacts. |

## Project layout

```
src/test/
  resources/
    schemas/                 JSON Schema for the booking response
    suites/                  smoke.xml, regression.xml
  java/com/dilshana/api/
    client/                  AuthClient, BookingClient
    model/                   Booking, BookingDates, CreatedBooking
    data/                    BookingFactory (unique, valid test data)
    config/                  Settings (base URL, credentials)
    tests/                   BaseApiTest and the test classes
```

## Run it

You need Java 17 and Maven.

```bash
mvn test                                        # regression suite
mvn test -Dsuite=smoke                          # smoke suite only
mvn test -DbaseUrl=https://staging.example.com  # a different environment
```

Reports are written to `target/surefire-reports`.

## Example

```java
@Test(groups = "regression")
public void deletedBookingIsGone() {
    CreatedBooking created = createBooking(BookingFactory.valid());

    client.delete(created.bookingid()).then().statusCode(201);

    client.get(created.bookingid()).then().statusCode(404);
}
```

## Notes on the target API

- The API signals a successful delete with `201 Created`, and a failed login with `200` and a `reason` field. The tests assert those real behaviours.
- A request missing required fields returns `500` rather than a `4xx`. One test records this current behaviour on purpose, so any change is noticed.
- It is a shared public service, so it can be slow or briefly unavailable. A failed run is worth re-running before assuming a code problem.

## Related

- [selenium-java-testng-framework](https://github.com/dilshana-dasthakeer/selenium-java-testng-framework)
- [cucumber-bdd-framework](https://github.com/dilshana-dasthakeer/cucumber-bdd-framework)
- [playwright-typescript-framework](https://github.com/dilshana-dasthakeer/playwright-typescript-framework)

## Licence

MIT
