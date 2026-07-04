package com.currency.exchange.mapper;
import com.currency.exchange.dto.reciept.CurrencyRequestDto;
import com.currency.exchange.dto.reciept.CurrencyRequestForPostDto;
import com.currency.exchange.dto.response.CurrencyResponseDto;
import com.currency.exchange.model.Currency;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@org.mapstruct.Mapper
public interface CurrencyMapper {
    CurrencyMapper INSTANCE = Mappers.getMapper(CurrencyMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "code", target = "code")
    @Mapping(source = "sign", target = "sign")
    Currency toEntity(CurrencyRequestForPostDto dto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "code", target = "code")
    @Mapping(source = "sign", target = "sign")
    CurrencyResponseDto toCurrencyResponseDto(Currency currency);
    List<CurrencyResponseDto> toCurrencyResponseDtoList(List<Currency> currencyList);
}
