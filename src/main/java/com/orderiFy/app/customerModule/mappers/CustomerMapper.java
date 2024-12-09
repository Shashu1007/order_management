package com.orderiFy.app.customerModule.mappers;

import com.orderiFy.app.customerModule.dto.CustomerDto;
import com.orderiFy.app.customerModule.entity.Customer;
import lombok.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "dob", dateFormat = "dd-MM-yyyy")
    CustomerDto toDTO(Customer customer);

    @Mapping(target = "dob", dateFormat = "dd-MM-yyyy")
    Customer toEntity(CustomerDto customerDto);
}
