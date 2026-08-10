package com.currency.exchange.mapper;

import com.currency.exchange.dto.response.ExchangeResponseDto;
import com.currency.exchange.model.Exchange;
import org.mapstruct.factory.Mappers;

@org.mapstruct.Mapper

public interface ExchangeMapper {

    ExchangeMapper INSTANCE = Mappers.getMapper(ExchangeMapper.class);

    ExchangeResponseDto toDto(Exchange exchange);
}
