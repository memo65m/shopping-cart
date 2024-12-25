package com.indra.shoppingcart.domain.exception;

public class UnauthorizedException extends RuntimeException {


    private static final long serialVersionUID = -5866016175616779854L;

    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(Throwable cause) {
        super(cause);
    }
}
