package pl.complaint.app.controller.exception.model;

public class InvalidSortParamException extends RuntimeException {

    public InvalidSortParamException(String message) {
        super(message);
    }
}
