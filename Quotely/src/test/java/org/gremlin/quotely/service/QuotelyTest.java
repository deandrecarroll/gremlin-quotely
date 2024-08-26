package org.gremlin.quotely.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class QuotelyTest {

    private Quotely quotely;


    @BeforeEach
    void setUp() {
        quotely = new Quotely();
    }

//    @Test
//    void fetchQuote() {
//    }

    @Test
    void createRequest() {
        String requestUrl = "http://";
        assertThrows(RuntimeException.class, () -> quotely.createRequest(requestUrl, ""));
    }

    @Test
    void sendRequest() {
        HttpClient mockClient = mock(HttpClient.class);
        HttpRequest mockRequest = mock(HttpRequest.class);

        assertThrows(RuntimeException.class, () -> {
            when(mockClient.send(any(HttpRequest.class),
                    any(HttpResponse.BodyHandler.class))).thenThrow(new IOException());

            quotely.sendRequest(mockClient, mockRequest);
        });
    }

    @Test
    void parseQuote() {
        var body = """
                {
                    "quoteText": "No one has a finer command of language than the person who keeps his mouth shut. ",
                    "quoteAuthor": "Sam Rayburn",
                    "senderName": "",
                    "senderLink": "",
                    "quoteLink": "http://forismatic.com/en/4832d694c6/"
                }
                """;

        String quote = quotely.parseQuote(body);

        assertTrue(quote.contains("Sam Rayburn"));
    }
}