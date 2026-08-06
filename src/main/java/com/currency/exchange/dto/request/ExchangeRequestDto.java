package com.currency.exchange.dto.request;

import com.currency.exchange.exception.ValidationException;

public record ExchangeRequestDto(String from, String to, String amount) {
    public ExchangeRequestDto {
        if (from == null) {
            throw new ValidationException("Base currency code is required");
        }

        if (to == null) {
            throw new ValidationException("Target currency code is required");
        }
        try {
            if (amount == null) {
                throw new ValidationException("Amount is required");
            }
            double parsedAmount = Double.parseDouble(amount);
            if (parsedAmount <= 0) {
                throw new ValidationException("Amount must be greater than zero");
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("Amount must be a valid number");
        }

    }
}
