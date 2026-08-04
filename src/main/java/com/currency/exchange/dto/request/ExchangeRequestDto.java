package com.currency.exchange.dto.request;

import com.currency.exchange.dto.BaseDto;
import com.currency.exchange.exception.ValidationException;

public record ExchangeRequestDto(String from, String to, String amount) implements BaseDto {
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

    @Override
    public void validate() {
        if (from.length() != 3) {
            throw new ValidationException("Base currency code must be exactly 3 characters long");
        }

        for (Character c : from.toCharArray()) {
            if (!Character.isLetter(c)) {
                throw new ValidationException("Base currency code must contain letters only");
            }
        }

        if (to.length() != 3) {
            throw new ValidationException("Target currency code must be exactly 3 characters long");
        }

        for (Character c : to.toCharArray()) {
            if (!Character.isLetter(c)) {
                throw new ValidationException("Target currency code must contain letters only");
            }
        }
    }

    public double getParseAmount() {
        return Double.parseDouble(this.amount);
    }
}
