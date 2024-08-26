package org.gremlin.quotely;

import org.gremlin.quotely.service.Quotely;

public class Main {
    public static void main(String[] args) {
        Quotely quotely = null;
        Quotely.QuotelyLanguage quotelyLanguage = null;

        if (args.length > 0) {
            String language = args[0].strip().toUpperCase();
            if (language.isBlank()) {
                quotely = new Quotely();
            } else {

                try {
                    quotelyLanguage = Quotely.QuotelyLanguage.valueOf(language);
                    quotely = new Quotely(quotelyLanguage);
                } catch (IllegalArgumentException e) {
                    System.err.println("\nUsage: Language argument must be English or Russian\n");
                    System.exit(1);
                }

            }
        } else {
            quotely = new Quotely();
        }

        System.out.println(quotely.fetchQuote());
    }
}