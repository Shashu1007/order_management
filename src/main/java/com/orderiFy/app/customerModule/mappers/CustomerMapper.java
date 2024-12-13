package com.orderiFy.app.customerModule.mappers;

import com.orderiFy.app.customerModule.dto.CustomerDto;
import com.orderiFy.app.customerModule.entity.Customer;
import com.orderiFy.app.productModule.mappers.ProductMapper;
import lombok.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
@Mapper(componentModel = "spring")
public interface CustomerMapper {


    CustomerDto toDTO(Customer customer);

    Customer toEntity(CustomerDto customerDto);
}


