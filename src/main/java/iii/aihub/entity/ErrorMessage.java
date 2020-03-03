package iii.aihub.entity;

public class ErrorMessage {

    public String errorMessage;

    public Integer httpStatus;

    public ErrorMessage() {
        this.errorMessage = "";
        this.httpStatus = 500;
    }

    public ErrorMessage(String errorMessage, Integer httpStatus) {
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    public ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        this.httpStatus = 500;
    }

    public String toString(){
        return org.apache.commons.lang3.builder.ReflectionToStringBuilder.reflectionToString(this);
    }
}
