package com.orderiFy.app.orderModule.mappers;

import com.orderiFy.app.orderModule.dto.OrderItemDto;
import com.orderiFy.app.orderModule.entity.OrderItems;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;




@Mapper(componentModel = "spring" ,uses =  OrderMapper.class)
public interface OrderItemsMapper {

    // Maps OrderItems entity to OrderItemDto
    OrderItemDto toDTO(OrderItems orderItems);

    // Maps OrderItemDto to OrderItems entity
    OrderItems toEntity(OrderItemDto orderItemDto);
}

