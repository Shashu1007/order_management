package com.orderiFy.app.orderModule.mappers;

import com.orderiFy.app.orderModule.dto.OrderDto;
import com.orderiFy.app.orderModule.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = OrderItemsMapper.class)
public interface OrderMapper {


    OrderDto toDTO(Order order);

    Order toEntity(OrderDto orderDto);
}
