package vn.com.assistant.fqcbackend.exception;

public class UnexpectedException extends RuntimeException {
    public UnexpectedException() {
        super("Xay ra loi khong xac dinh");
    }
}
