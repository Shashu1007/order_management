package com.orderiFy.app.orderModule.mappers;

import com.orderiFy.app.orderModule.dto.OrderDto;
import com.orderiFy.app.orderModule.entity.Order;
import com.orderiFy.app.orderModule.entity.OrderItems;
import com.orderiFy.app.productModule.mappers.ProductMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;





@Mapper(componentModel = "spring")
public interface OrderMapper {



    OrderDto toDTO(Order order);

    Order toEntity(OrderDto orderDto);

    List<OrderDto> toDtoList(List<Order> ordersList);

    List<Order> toEntityList(List<OrderDto> orderDtoList);
}