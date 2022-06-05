package br.com.karate.model.util.controller.output;

public class DefaultError {

    public String title;
    public String error;

    public DefaultError() {}

    public DefaultError(String error) {
        this.error = error;
    }

    public DefaultError(String title, String error) {
        this.title = title;
        this.error = error;
    }

}
