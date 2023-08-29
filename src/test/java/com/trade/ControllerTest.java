package com.trade;


import com.trade.config.TestExtendedScalarConfiguration;
import com.trade.controller.TestTradeController;
import com.trade.model.entity.Trade;
import com.trade.service.TestTradeService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;
import static org.junit.jupiter.api.Assertions.assertEquals;


@GraphQlTest(TestTradeController.class)
@Import({TestTradeService.class, TestExtendedScalarConfiguration.class,})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ControllerTest {


    @Autowired
    GraphQlTester graphQlTester;

    @Autowired
    TestTradeService tradeTestService;



    @Test
    @Order(1)
    void validIdShouldReturnCoffee() {

        // language=GraphQL
        String document = """
        query QueryTradeByTradeId($id: String){
            tradeByTradeId(tradeId: $id) {
                tradeId
                tradeInfo {
                    customerInfo {
                       customerInfoId      
                    }                
                }
                height
                status
                created
            }
        }            
        """;

        graphQlTester.document(document)
                .variable("id", "trade-1")
                .execute()
                .path("tradeByTradeId")
                .entity(Trade.class)
                .satisfies(trade -> {
                    assertEquals("customer-1",trade.getTradeInfo().getCustomerInfo().getCustomerInfoId());
                });
    }
}
