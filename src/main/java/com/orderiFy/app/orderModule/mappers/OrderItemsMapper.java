package com.orderiFy.app.orderModule.mappers;

import com.orderiFy.app.orderModule.dto.OrderItemDto;
import com.orderiFy.app.orderModule.entity.OrderItems;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;




@Mapper(componentModel = "spring")
public interface OrderItemsMapper {

    OrderItemDto toDTO(OrderItems orderItem);

    OrderItems toEntity(OrderItemDto orderItemDto);
}

