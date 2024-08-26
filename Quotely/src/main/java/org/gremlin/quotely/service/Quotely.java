package org.gremlin.quotely.service;

import com.google.gson.Gson;
import org.gremlin.quotely.model.Quote;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Quotely {

    protected static final String QUOTE_SERVICE = "http://api.forismatic.com/api/1.0/";

    protected static final String METHOD_KEY = "method";
    protected static final String METHOD_GET_QUOTE = "getQuote";
    protected static final String FORMAT_KEY = "format";
    protected static final String FORMAT_JSON = "json";

    protected static final String LANG_KEY = "lang";
    protected QuotelyLanguage quotelyLanguage;

    protected Map<String, String> apiRequestParameters = new HashMap<>(
            Map.of(METHOD_KEY, METHOD_GET_QUOTE,
                    FORMAT_KEY, FORMAT_JSON)
    );

    public enum QuotelyLanguage {
        ENGLISH("en"),
        RUSSIAN("ru");

        public final String languageCode;

        QuotelyLanguage(String langaugeCode) {
            this.languageCode = langaugeCode;
        }
    }

    /*
     * Constructors
     */
    public Quotely(QuotelyLanguage language) {
        this.quotelyLanguage = language;
        this.apiRequestParameters.put(LANG_KEY, quotelyLanguage.languageCode);
    }

    public Quotely() {
        this(QuotelyLanguage.ENGLISH);
    }

    /*
     * Methods
     */
    public String fetchQuote() {
        String form = apiRequestParameters.entrySet()
                .stream()
                .map(e -> e.getKey() + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));
        HttpRequest request = createRequest(QUOTE_SERVICE, form);

        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> response = sendRequest(client, request);

        if (200 == response.statusCode()) {
            return parseQuote(response.body());
        }
        return "No quote found";
    }

    protected HttpRequest createRequest(String requestUri, String form) {
        HttpRequest request;
        try {
            request = HttpRequest.newBuilder(new URI(requestUri))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(form))
                    .build();

            return request;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    protected HttpResponse<String> sendRequest(HttpClient client, HttpRequest request) {
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected String parseQuote(String body) {
        Gson gson = new Gson();
        Quote quote = gson.fromJson(body, Quote.class);

        return quote.getQuoteText() + "\n-" + quote.getQuoteAuthor();
    }
}
