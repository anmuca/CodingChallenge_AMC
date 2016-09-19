package eu.antoniomunoz.codingchallenge.exceptions;

/**
 * Exception launched when a problem raises from the web service of rates.
 */
public class RatesWSNotAvailable extends RuntimeException {
    public RatesWSNotAvailable(Exception e) {
        super(e);
    }

    public RatesWSNotAvailable(String s) {
        super(s);
    }
}
