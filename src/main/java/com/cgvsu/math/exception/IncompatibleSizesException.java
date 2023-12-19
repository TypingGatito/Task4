package com.cgvsu.math.exception;

public class IncompatibleSizesException extends RuntimeException{
    public IncompatibleSizesException(){
        super("The sizes of data structure and given array are different. Make them equal");
    }
}
