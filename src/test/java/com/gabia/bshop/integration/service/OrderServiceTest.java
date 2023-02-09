package com.gabia.bshop.integration.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.gabia.bshop.dto.response.OrderInfoPageResponse;
import com.gabia.bshop.entity.Category;
import com.gabia.bshop.entity.Item;
import com.gabia.bshop.entity.ItemImage;
import com.gabia.bshop.entity.Member;
import com.gabia.bshop.entity.Options;
import com.gabia.bshop.entity.OrderItem;
import com.gabia.bshop.entity.Orders;
import com.gabia.bshop.entity.enumtype.ItemStatus;
import com.gabia.bshop.entity.enumtype.MemberGrade;
import com.gabia.bshop.entity.enumtype.MemberRole;
import com.gabia.bshop.entity.enumtype.OrderStatus;
import com.gabia.bshop.integration.IntegrationTest;
import com.gabia.bshop.repository.CategoryRepository;
import com.gabia.bshop.repository.ItemImageRepository;
import com.gabia.bshop.repository.ItemRepository;
import com.gabia.bshop.repository.MemberRepository;
import com.gabia.bshop.repository.OptionsRepository;
import com.gabia.bshop.repository.OrderItemRepository;
import com.gabia.bshop.repository.OrderRepository;
import com.gabia.bshop.service.OrderService;

import jakarta.persistence.EntityNotFoundException;

@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderServiceTest extends IntegrationTest {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OptionsRepository optionsRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private ItemImageRepository itemImageRepository;

	@Autowired
	private OrderService orderService;

	@DisplayName("주문을_한_회원이_주문목록_조회를_수행하면_주문내역들이_조회되어야한다")
	@Test
	void findOrderList() {
		//given
		LocalDateTime now = LocalDateTime.now();
		Member member1 = Member.builder()
			.name("1_test_name")
			.email("1_ckdals1234@naver.com")
			.hiworksId("1_asdfasdf")
			.phoneNumber("01000000001")
			.role(MemberRole.NORMAL)
			.grade(MemberGrade.BRONZE)
			.build();
		Category category1 = Category.builder()
			.name("카테고리1")
			.build();
		Item item1 = Item.builder()
			.category(category1)
			.name("temp_item_name1")
			.description("temp_item_1_description " + UUID.randomUUID())
			.basePrice(11111)
			.itemStatus(ItemStatus.PUBLIC)
			.openAt(now)
			.deleted(false)
			.build();
		Item item2 = Item.builder()
			.category(category1)
			.name("temp_item_name1")
			.description("temp_item_1_description " + UUID.randomUUID())
			.basePrice(22222)
			.itemStatus(ItemStatus.PUBLIC)
			.openAt(now)
			.deleted(false)
			.build();
		Options options1 = Options.builder()
			.item(item1)
			.description("temp_options_1_description")
			.optionLevel(1)
			.optionPrice(0)
			.stockQuantity(10)
			.build();
		Options options2 = Options.builder()
			.item(item2)
			.description("temp_options_2_description")
			.optionLevel(1)
			.optionPrice(1000)
			.stockQuantity(5)
			.build();
		Orders order1 = Orders.builder()
			.member(member1)
			.status(OrderStatus.ACCEPTED)
			.totalPrice(11111L)
			.build();
		OrderItem orderItem1_order1 = OrderItem.builder()
			.item(item1)
			.order(order1)
			.option(options1)
			.orderCount(1)
			.price(11111L)
			.build();
		Orders order2 = Orders.builder()
			.member(member1)
			.status(OrderStatus.ACCEPTED)
			.totalPrice(33333L)
			.build();
		OrderItem orderItem2_order2 = OrderItem.builder()
			.item(item1)
			.order(order2)
			.option(options1)
			.orderCount(1)
			.price(11111L)
			.build();
		OrderItem orderItem3_order2 = OrderItem.builder()
			.item(item2)
			.order(order2)
			.option(options2)
			.orderCount(1)
			.price(22222L)
			.build();
		ItemImage itemImage1 = ItemImage.builder()
			.item(item1)
			.url(UUID.randomUUID().toString())
			.build();
		ItemImage itemImage2 = ItemImage.builder()
			.item(item1)
			.url(UUID.randomUUID().toString())
			.build();
		ItemImage itemImage3 = ItemImage.builder()
			.item(item2)
			.url(UUID.randomUUID().toString())
			.build();
		ItemImage itemImage4 = ItemImage.builder()
			.item(item2)
			.url(UUID.randomUUID().toString())
			.build();

		memberRepository.save(member1);
		categoryRepository.save(category1);
		itemRepository.saveAll(List.of(item1, item2));
		itemImageRepository.saveAll(List.of(itemImage1, itemImage2, itemImage3, itemImage4));
		orderRepository.saveAll(List.of(order1, order2));
		optionsRepository.saveAll(List.of(options1, options2));
		orderItemRepository.saveAll(
			List.of(orderItem1_order1, orderItem2_order2, orderItem3_order2));

		PageRequest pageable = PageRequest.of(0, 10);
		//when
		OrderInfoPageResponse orderInfo = orderService.findOrdersPagination(member1.getId(),
			pageable);
		//then
		Assertions.assertThat(orderInfo.resultCount()).isEqualTo(2);
		Assertions.assertThat(orderInfo.orderInfos().get(0).orderId()).isEqualTo(order1.getId());
		Assertions.assertThat(orderInfo.orderInfos().get(0).thumbnailImage())
			.isEqualTo(itemImage1.getUrl());
		Assertions.assertThat(orderInfo.orderInfos().get(0).representativeName())
			.isEqualTo(item1.getName());
		Assertions.assertThat(orderInfo.orderInfos().get(0).itemTotalCount()).isEqualTo(1);
		Assertions.assertThat(orderInfo.orderInfos().get(0).orderStatus())
			.isEqualTo(order1.getStatus());

		Assertions.assertThat(orderInfo.orderInfos().get(1).orderId()).isEqualTo(order2.getId());
		Assertions.assertThat(orderInfo.orderInfos().get(1).thumbnailImage())
			.isEqualTo(itemImage3.getUrl());
		Assertions.assertThat(orderInfo.orderInfos().get(1).representativeName())
			.isEqualTo(item2.getName());
		Assertions.assertThat(orderInfo.orderInfos().get(1).itemTotalCount()).isEqualTo(2);
		Assertions.assertThat(orderInfo.orderInfos().get(1).orderStatus())
			.isEqualTo(order2.getStatus());
	}

	@DisplayName("존재하지_않는_회원이_주문목록_조회를_요청하면_오류가_발생해야한다")
	@Test
	void findOrderListInvalidIdFail() {
		//given
		Long invalidMemberId = 12375819347689L;
		PageRequest pageable = PageRequest.of(0, 10);
		//when & then
		Assertions.assertThatThrownBy(
				() -> orderService.findOrdersPagination(invalidMemberId, pageable))
			.isInstanceOf(EntityNotFoundException.class);
	}

	@DisplayName("주문_후_삭제된_상품도_주문목록에서_조회되어야한다")
	@Test
	void findOrderListCancelledItem() {
		//given
		LocalDateTime now = LocalDateTime.now();
		Member member1 = Member.builder()
			.name("1_test_name")
			.email("1_ckdals1234@naver.com")
			.hiworksId("1_asdfasdf")
			.phoneNumber("01000000001")
			.role(MemberRole.NORMAL)
			.grade(MemberGrade.BRONZE)
			.build();
		Category category1 = Category.builder()
			.name("카테고리1")
			.build();
		Item item1 = Item.builder()
			.category(category1)
			.name("temp_item_name1")
			.description("temp_item_1_description " + UUID.randomUUID())
			.basePrice(11111)
			.itemStatus(ItemStatus.PUBLIC)
			.openAt(now)
			.deleted(true)
			.build();
		Options options1 = Options.builder()
			.id(1L)
			.item(item1)
			.description("description")
			.optionLevel(1)
			.optionPrice(0)
			.stockQuantity(10)
			.build();
		Orders order1 = Orders.builder()
			.member(member1)
			.status(OrderStatus.ACCEPTED)
			.totalPrice(11111L)
			.build();
		OrderItem orderItem1_order1 = OrderItem.builder()
			.item(item1)
			.order(order1)
			.option(options1)
			.orderCount(1)
			.price(11111L)
			.build();

		ItemImage itemImage1 = ItemImage.builder()
			.item(item1)
			.url(UUID.randomUUID().toString())
			.build();
		ItemImage itemImage2 = ItemImage.builder()
			.item(item1)
			.url(UUID.randomUUID().toString())
			.build();
		memberRepository.save(member1);
		categoryRepository.save(category1);
		itemRepository.saveAll(List.of(item1));
		optionsRepository.save(options1);
		itemImageRepository.saveAll(List.of(itemImage1, itemImage2));
		orderRepository.saveAll(List.of(order1));
		orderItemRepository.saveAll(List.of(orderItem1_order1));
		PageRequest pageable = PageRequest.of(0, 10);

		//when
		OrderInfoPageResponse orderInfo = orderService.findOrdersPagination(member1.getId(),
			pageable);

		//then
		Assertions.assertThat(orderInfo.resultCount()).isEqualTo(1);
	}
}