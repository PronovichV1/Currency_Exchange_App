package com.currency.exchange.mapper;

import com.currency.exchange.dto.request.ExchangeRatesRequestDto;
import com.currency.exchange.dto.response.ExchangeRateResponseDto;
import com.currency.exchange.model.ExchangeRate;
import org.mapstruct.factory.Mappers;

import java.util.List;

@org.mapstruct.Mapper
public interface ExchangeRateMapper {
    ExchangeRateMapper INSTANCE = Mappers.getMapper(ExchangeRateMapper.class);


    ExchangeRateResponseDto toDto(ExchangeRate exchangeRate);

    List<ExchangeRateResponseDto> toDtoList(List<ExchangeRate> exchangeRateList);

}
