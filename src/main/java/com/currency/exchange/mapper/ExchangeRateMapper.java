package com.currency.exchange.mapper;

import com.currency.exchange.dto.reciept.ExchangeRatesRequestDto;
import com.currency.exchange.dto.response.ExchangeRateResponseDto;
import com.currency.exchange.model.ExchangeRate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@org.mapstruct.Mapper
public interface ExchangeRateMapper {
    ExchangeRateMapper INSTANCE = Mappers.getMapper(ExchangeRateMapper.class);


    ExchangeRateResponseDto toDto(ExchangeRate exchangeRate);

    List<ExchangeRateResponseDto> toDtoList(List<ExchangeRate> exchangeRateList);

    ExchangeRate toEntity(ExchangeRatesRequestDto exchangeRatesRequestDto);
}
