package com.gabia.bshop.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabia.bshop.dto.request.OrderCreateRequestDto;
import com.gabia.bshop.dto.response.OrderCreateResponseDto;
import com.gabia.bshop.dto.response.OrderInfoPageResponse;
import com.gabia.bshop.entity.Item;
import com.gabia.bshop.entity.ItemImage;
import com.gabia.bshop.entity.Options;
import com.gabia.bshop.entity.OrderItem;
import com.gabia.bshop.entity.Orders;
import com.gabia.bshop.mapper.OrderInfoMapper;
import com.gabia.bshop.mapper.OrderMapper;
import com.gabia.bshop.repository.ItemImageRepository;
import com.gabia.bshop.repository.ItemRepository;
import com.gabia.bshop.repository.MemberRepository;
import com.gabia.bshop.repository.OptionsRepository;
import com.gabia.bshop.repository.OrderItemRepository;
import com.gabia.bshop.repository.OrderRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;
	private final ItemRepository itemRepository;
	private final ItemImageRepository itemImageRepository;
	private final MemberRepository memberRepository;
	private final OptionsRepository optionsRepository;

	@Transactional(readOnly = true)
	public OrderInfoPageResponse findOrdersPagination(final Long memberId,
		final Pageable pageable) {
		memberRepository.findById(memberId)
			.orElseThrow(() -> new EntityNotFoundException("해당하는 id의 회원이 존재하지 않습니다."));

		final List<Orders> orders = orderRepository.findByMemberIdPagination(memberId, pageable);
		final List<OrderItem> orderItems = orderItemRepository.findByOrderIds(
			orders.stream().map(o -> o.getId()).collect(Collectors.toList()));
		final List<ItemImage> itemImagesWithItem = itemImageRepository.findWithItemByItemIds(
			orderItems.stream().map(oi -> oi.getItem().getId()).collect(Collectors.toList()));

		return OrderInfoMapper.INSTANCE.orderInfoRelatedEntitiesToOrderInfoPageResponse(orders,
			orderItems,
			itemImagesWithItem);
	}

	/**
	 * 주문 생성
	 */
	public OrderCreateResponseDto createOrder(final OrderCreateRequestDto orderCreateRequestDto) {

		memberRepository.findById(orderCreateRequestDto.memberId())
			.orElseThrow(EntityNotFoundException::new);
		Orders orders = OrderMapper.INSTANCE.ordersCreateDtoToEntity(orderCreateRequestDto);

		List<OrderItem> orderItemEntityList = orderCreateRequestDto.orderItemDtoList()
			.stream()
			.map(oi -> {
				Item item = itemRepository.findById(oi.id()).get();
				Options options = optionsRepository.findByItem_Id(oi.id());
				return OrderItem.createOrderItem(item, options, orders, oi.orderCount());
			})
			.collect(Collectors.toList());

		orders.createOrder(orderItemEntityList);

		orderRepository.save(orders);

		return OrderMapper.INSTANCE.ordersCreateResponseDto(orders);
	}

	/**
	 * 주문 취소(제거)
	 */
	public void cancelOrder(final Long id) {
		Orders orders = orderRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 주문 ID 입니다."));

		orders.cancel();
	}
}
