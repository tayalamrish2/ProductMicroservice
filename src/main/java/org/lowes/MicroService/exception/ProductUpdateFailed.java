package org.lowes.MicroService.exception;

public class ProductUpdateFailed extends RuntimeException {
    public ProductUpdateFailed(String message){
        super(message);
    }
}
