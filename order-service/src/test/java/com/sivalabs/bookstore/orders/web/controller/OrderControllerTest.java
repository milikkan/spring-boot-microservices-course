package com.sivalabs.bookstore.orders.web.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

import com.sivalabs.bookstore.orders.AbstractIT;
import com.sivalabs.bookstore.orders.testdata.TestDataFactory;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class OrderControllerTest extends AbstractIT {

    @Nested
    class CreateOrderTests {
        @Test
        void shouldCreateOrderSuccessfully() {
            var payload = """
                    {
                        "customer": {
                            "name": "Siva",
                            "email": "siva@gmail.com",
                            "phone": "9999999999"
                        },
                        "deliveryAddress": {
                            "addressLine1": "Kukatpally",
                            "addressLine2": "KPHB",
                            "city": "Hyderabad",
                            "state": "Telangana",
                            "zipCode": "5000063",
                            "country": "India"
                        },
                        "items": [
                            {
                                "code": "P100",
                                "name": "product 1",
                                "price": 25.50,
                                "quantity": 1
                            }
                        ]
                    }
                    """;
            given().contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/orders")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("orderNumber", notNullValue());
        }

        @Test
        void shouldReturnBadRequestWhenMandatoryDataIsMissing() {
            var payload = TestDataFactory.createOrderRequestWithInvalidCustomer();
            given().contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/orders")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }
}
