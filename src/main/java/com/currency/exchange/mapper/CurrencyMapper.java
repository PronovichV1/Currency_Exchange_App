package com.currency.exchange.mapper;
import com.currency.exchange.dto.reciept.CurrencyPostDto;
import com.currency.exchange.dto.response.CurrencyResponseDto;
import com.currency.exchange.model.Currency;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@org.mapstruct.Mapper
public interface CurrencyMapper {
    CurrencyMapper INSTANCE = Mappers.getMapper(CurrencyMapper.class);

    Currency toEntity(CurrencyPostDto dto);

    @Mapping(source = "dtoName", target = "name")
    CurrencyResponseDto toResponseDto(Currency currency);
    List<CurrencyResponseDto> toCurrencyResponseDtoList(List<Currency> currencyList);

}
