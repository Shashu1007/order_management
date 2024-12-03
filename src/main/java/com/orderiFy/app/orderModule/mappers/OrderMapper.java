package com.orderiFy.app.orderModule.mappers;

import com.orderiFy.app.orderModule.dto.OrderDto;
import com.orderiFy.app.orderModule.dto.OrderItemDto;
import com.orderiFy.app.orderModule.entity.Order;
import com.orderiFy.app.orderModule.entity.OrderItems;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "orderItems", source = "orderItems")
    OrderDto toDTO(Order order);

    @Mapping(target = "orderItems", source = "orderItems")
    Order toEntity(OrderDto orderDto);

    List<OrderItemDto> toOrderItemDtos(List<OrderItems> orderItems);

    List<OrderItems> toOrderItemEntities(List<OrderItemDto> orderItemDtos);
}
