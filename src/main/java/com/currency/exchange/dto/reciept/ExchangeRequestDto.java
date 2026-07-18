package com.currency.exchange.dto.reciept;

import com.currency.exchange.dto.BaseDto;
import com.currency.exchange.exception.InvalidFormatException;

public record ExchangeRequestDto(String from, String to, String amount) implements BaseDto {
    public ExchangeRequestDto {
        if (from == null) {
            throw new InvalidFormatException("Base currency code is required");
        }

        if (to == null) {
            throw new InvalidFormatException("Target currency code is required");
        }
        try {
            if (amount == null) {
                throw new InvalidFormatException("Amount is required");
            }
            double parsedAmount = Double.parseDouble(amount);
            if (parsedAmount <= 0) {
                throw new InvalidFormatException("Amount must be greater than zero");
            }
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Amount must be a valid number");
        }

    }

    @Override
    public void validate() {
        if (from.length() != 3) {
            throw new InvalidFormatException("Base currency code must be exactly 3 characters long");
        }

        for (Character c : from.toCharArray()) {
            if (!Character.isLetter(c)) {
                throw new InvalidFormatException("Base currency code must contain letters only");
            }
        }

        if (to.length() != 3) {
            throw new InvalidFormatException("Target currency code must be exactly 3 characters long");
        }

        for (Character c : to.toCharArray()) {
            if (!Character.isLetter(c)) {
                throw new InvalidFormatException("Target currency code must contain letters only");
            }
        }
    }

    public double getParseAmount() {
        return Double.parseDouble(this.amount);
    }
}
