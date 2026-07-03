package com.currency.exchange.dto;

import com.currency.exchange.exception.InvalidFormatException;

public interface Validatable {

    void validate() throws InvalidFormatException;
}
