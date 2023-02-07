package com.gabia.bshop.util;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.gabia.bshop.entity.Category;
import com.gabia.bshop.entity.Item;
import com.gabia.bshop.entity.ItemImage;
import com.gabia.bshop.entity.Member;
import com.gabia.bshop.entity.OrderItem;
import com.gabia.bshop.entity.Orders;
import com.gabia.bshop.entity.enumtype.ItemStatus;
import com.gabia.bshop.entity.enumtype.MemberGrade;
import com.gabia.bshop.entity.enumtype.MemberRole;
import com.gabia.bshop.entity.enumtype.OrderStatus;
import com.gabia.bshop.repository.CategoryRepository;
import com.gabia.bshop.repository.ItemImageRepository;
import com.gabia.bshop.repository.ItemRepository;
import com.gabia.bshop.repository.MemberRepository;
import com.gabia.bshop.repository.OptionsRepository;
import com.gabia.bshop.repository.OrderItemRepository;
import com.gabia.bshop.repository.OrdersRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Profile("local")
@RequiredArgsConstructor
@Component
public class DataInit {

	private final MemberRepository memberRepository;
	private final CategoryRepository categoryRepository;
	private final ItemRepository itemRepository;
	private final ItemImageRepository itemImageRepository;
	private final OptionsRepository optionsRepository;
	private final OrderItemRepository orderItemRepository;
	private final OrdersRepository orderRepository;

	@PostConstruct
	public void init() {
		Member member1 = Member.builder()
			.name("1_test_name")
			.email("1_ckdals1234@naver.com")
			.hiworksId("1_asdfasdf")
			.phoneNumber("01000000001")
			.role(MemberRole.ADMIN)
			.grade(MemberGrade.DIAMOND)
			.build();
		Member member2 = Member.builder()
			.name("2_test_name")
			.email("2_ckdals1234@naver.com")
			.hiworksId("2_asdfasdf")
			.phoneNumber("01000000002")
			.role(MemberRole.ADMIN)
			.grade(MemberGrade.DIAMOND)
			.build();
		Member member3 = Member.builder()
			.name("3_test_name")
			.email("3_ckdals1234@naver.com")
			.hiworksId("3_asdfasdf")
			.phoneNumber("01000000003")
			.role(MemberRole.ADMIN)
			.grade(MemberGrade.DIAMOND)
			.build();
		Member member4 = Member.builder()
			.name("4_test_name")
			.email("4_ckdals1234@naver.com")
			.hiworksId("4_asdfasdf")
			.phoneNumber("01000000004")
			.role(MemberRole.ADMIN)
			.grade(MemberGrade.DIAMOND)
			.build();
		Member member5 = Member.builder()
			.name("5_test_name")
			.email("5_ckdals1234@naver.com")
			.hiworksId("5_asdfasdf")
			.phoneNumber("01000000005")
			.role(MemberRole.ADMIN)
			.grade(MemberGrade.DIAMOND)
			.build();
		Member member6 = Member.builder()
			.name("6_test_name")
			.email("6_ckdals1234@naver.com")
			.hiworksId("6_asdfasdf")
			.phoneNumber("01000000006")
			.role(MemberRole.NORMAL)
			.grade(MemberGrade.BRONZE)
			.build();
		Member member7 = Member.builder()
			.name("7_test_name")
			.email("7_ckdals1234@naver.com")
			.hiworksId("7_asdfasdf")
			.phoneNumber("01000000007")
			.role(MemberRole.NORMAL)
			.grade(MemberGrade.SILVER)
			.build();
		Member member8 = Member.builder()
			.name("8_test_name")
			.email("8_ckdals1234@naver.com")
			.hiworksId("8_asdfasdf")
			.phoneNumber("01000000008")
			.role(MemberRole.NORMAL)
			.grade(MemberGrade.GOLD)
			.build();
		Member member9 = Member.builder()
			.name("9_test_name")
			.email("9_ckdals1234@naver.com")
			.hiworksId("9_asdfasdf")
			.phoneNumber("01000000009")
			.role(MemberRole.NORMAL)
			.grade(MemberGrade.PLATINUM)
			.build();
		Member member10 = Member.builder()
			.name("10_test_name")
			.email("10_ckdals1234@naver.com")
			.hiworksId("10_asdfasdf")
			.phoneNumber("01000000010")
			.role(MemberRole.NORMAL)
			.grade(MemberGrade.DIAMOND)
			.build();

		memberRepository.saveAll(
			List.of(member1, member2, member3, member4, member5, member6, member7, member8,
				member9, member10));

		Category category1 = Category.builder()
			.name("카테고리1")
			.build();
		Category category2 = Category.builder()
			.name("카테고리2")
			.build();
		Category category3 = Category.builder()
			.name("카테고리3")
			.build();
		Category category4 = Category.builder()
			.name("카테고리4")
			.build();
		Category category5 = Category.builder()
			.name("카테고리5")
			.build();
		Category category6 = Category.builder()
			.name("카테고리6")
			.build();
		Category category7 = Category.builder()
			.name("카테고리7")
			.build();
		Category category8 = Category.builder()
			.name("카테고리8")
			.build();
		Category category9 = Category.builder()
			.name("카테고리9")
			.build();
		Category category10 = Category.builder()
			.name("카테고리10")
			.build();

		categoryRepository.saveAll(
			List.of(category1, category2, category3, category4, category5, category6, category7,
				category8, category9, category10));

		LocalDateTime now = LocalDateTime.now();
		Item item1 = Item.builder()
			.category(category1)
			.name("temp_item_name1")
			.description("temp_item_1_description " + UUID.randomUUID().toString())
			.basePrice(11111)
			.itemStatus(ItemStatus.PUBLIC)
			.openAt(now)
			.deleted(false)
			.build();
		Item item2 = Item.builder()
			.category(category1)
			.name("temp_item_name2")
			.description("temp_item_2_description " + UUID.randomUUID().toString())
			.basePrice(22222)
			.itemStatus(ItemStatus.PUBLIC)
			.openAt(now)
			.deleted(false)
			.build();
		Item item3 = Item.builder()
			.category(category1)
			.name("temp_item_name3")
			.description("temp_item_3_description " + UUID.randomUUID().toString())
			.basePrice(33333)
			.itemStatus(ItemStatus.PUBLIC)
			.openAt(now)
			.deleted(false)
			.build();
		Item item4 = Item.builder()
			.category(category1)
			.name("temp_item_name4")
			.description("temp_item_4_description " + UUID.randomUUID().toString())
			.basePrice(44444)
			.itemStatus(ItemStatus.PUBLIC)
			.openAt(now)
			.deleted(false)
			.build();
		Item item5 = Item.builder()
			.category(category1)
			.name("temp_item_name5")
			.description("temp_item_5_description " + UUID.randomUUID().toString())
			.basePrice(55555)
			.itemStatus(ItemStatus.PUBLIC)
			.openAt(now)
			.deleted(false)
			.build();
		Item item6 = Item.builder()
			.category(category1)
			.name("temp_item_name6")
			.description("temp_item_6_description " + UUID.randomUUID().toString())
			.basePrice(66666)
			.itemStatus(ItemStatus.PRIVATE)
			.openAt(now.minusDays(1L))
			.deleted(false)
			.build();
		Item item7 = Item.builder()
			.category(category1)
			.name("temp_item_name7")
			.description("temp_item_7_description " + UUID.randomUUID().toString())
			.basePrice(77777)
			.itemStatus(ItemStatus.PRIVATE)
			.openAt(now.minusDays(1L))
			.deleted(false)
			.build();
		Item item8 = Item.builder()
			.category(category1)
			.name("temp_item_name8")
			.description("temp_item_8_description " + UUID.randomUUID().toString())
			.basePrice(88888)
			.itemStatus(ItemStatus.RESERVED)
			.openAt(now.plusDays(1L))
			.deleted(false)
			.build();
		Item item9 = Item.builder()
			.category(category1)
			.name("temp_item_name9")
			.description("temp_item_9_description " + UUID.randomUUID().toString())
			.basePrice(99999)
			.itemStatus(ItemStatus.RESERVED)
			.openAt(now.plusDays(1L))
			.deleted(false)
			.build();
		Item item10 = Item.builder()
			.category(category1)
			.name("temp_item_name10")
			.description("temp_item_10_description " + UUID.randomUUID().toString())
			.basePrice(12345)
			.itemStatus(ItemStatus.PUBLIC)
			.openAt(now)
			.deleted(true)
			.build();

		itemRepository.saveAll(
			List.of(item1, item2, item3, item4, item5, item6, item7, item8, item9, item10));

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
		ItemImage itemImage5 = ItemImage.builder()
			.item(item3)
			.url(UUID.randomUUID().toString())
			.build();
		ItemImage itemImage6 = ItemImage.builder()
			.item(item3)
			.url(UUID.randomUUID().toString())
			.build();
		ItemImage itemImage7 = ItemImage.builder()
			.item(item4)
			.url(UUID.randomUUID().toString())
			.build();
		ItemImage itemImage8 = ItemImage.builder()
			.item(item4)
			.url(UUID.randomUUID().toString())
			.build();
		ItemImage itemImage9 = ItemImage.builder()
			.item(item5)
			.url(UUID.randomUUID().toString())
			.build();
		ItemImage itemImage10 = ItemImage.builder()
			.item(item5)
			.url(UUID.randomUUID().toString())
			.build();
		ItemImage itemImage11 = ItemImage.builder()
			.item(item6)
			.url(UUID.randomUUID().toString())
			.build();
		ItemImage itemImage12 = ItemImage.builder()
			.item(item6)
			.url(UUID.randomUUID().toString())
			.build();
		ItemImage itemImage13 = ItemImage.builder()
			.item(item7)
			.url(UUID.randomUUID().toString())
			.build();
		ItemImage itemImage14 = ItemImage.builder()
			.item(item7)
			.url(UUID.randomUUID().toString())
			.build();
		ItemImage itemImage15 = ItemImage.builder()
			.item(item8)
			.url(UUID.randomUUID().toString())
			.build();
		ItemImage itemImage16 = ItemImage.builder()
			.item(item8)
			.url(UUID.randomUUID().toString())
			.build();
		ItemImage itemImage17 = ItemImage.builder()
			.item(item9)
			.url(UUID.randomUUID().toString())
			.build();
		ItemImage itemImage18 = ItemImage.builder()
			.item(item9)
			.url(UUID.randomUUID().toString())
			.build();
		ItemImage itemImage19 = ItemImage.builder()
			.item(item10)
			.url(UUID.randomUUID().toString())
			.build();
		ItemImage itemImage20 = ItemImage.builder()
			.item(item10)
			.url(UUID.randomUUID().toString())
			.build();

		itemImageRepository.saveAll(
			List.of(itemImage1, itemImage2, itemImage3, itemImage4, itemImage5, itemImage6, itemImage7, itemImage8,
				itemImage9, itemImage10, itemImage11, itemImage12, itemImage13, itemImage14, itemImage15, itemImage16,
				itemImage17, itemImage18, itemImage19, itemImage20));

		Orders order1 = Orders.builder()
			.member(member6)
			.status(OrderStatus.PENDING)
			.totalPrice(11111L)
			.build();
		Orders order2 = Orders.builder()
			.member(member6)
			.status(OrderStatus.PENDING)
			.totalPrice(33333L)
			.build();
		Orders order3 = Orders.builder()
			.member(member7)
			.status(OrderStatus.ACCEPTED)
			.totalPrice(22222L)
			.build();

		Orders order4 = Orders.builder()
			.member(member7)
			.status(OrderStatus.COMPLETED)
			.totalPrice(44444L)
			.build();

		orderRepository.saveAll(List.of(order1, order2, order3, order4));

		OrderItem orderItem1_order1 = OrderItem.builder()
			.item(item1)
			.order(order1)
			.orderCount(1)
			.price(11111L)
			.build();
		OrderItem orderItem2_order2 = OrderItem.builder()
			.item(item1)
			.order(order2)
			.orderCount(1)
			.price(11111L)
			.build();
		OrderItem orderItem3_order2 = OrderItem.builder()
			.item(item2)
			.order(order2)
			.orderCount(1)
			.price(22222L)
			.build();
		OrderItem orderItem4_order3 = OrderItem.builder()
			.item(item2)
			.order(order3)
			.orderCount(1)
			.price(22222L)
			.build();
		OrderItem orderItem5_order4 = OrderItem.builder()
			.item(item2)
			.order(order4)
			.orderCount(2)
			.price(22222L)
			.build();

		orderItemRepository.saveAll(
			List.of(orderItem1_order1, orderItem2_order2, orderItem3_order2, orderItem4_order3, orderItem5_order4));
	}
}