package mapper;
import dto.reciept.CurrencyPostDto;
import dto.response.CurrencyResponseDto;
import model.Currency;
import org.mapstruct.factory.Mappers;

@org.mapstruct.Mapper
public interface CurrencyMapper {
    CurrencyMapper INSTANCE = Mappers.getMapper(CurrencyMapper.class);

    Currency toEntity(CurrencyPostDto dto);

    CurrencyResponseDto toResponseDto(Currency currency);
}
