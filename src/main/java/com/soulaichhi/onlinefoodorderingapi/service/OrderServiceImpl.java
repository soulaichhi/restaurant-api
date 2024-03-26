package com.soulaichhi.onlinefoodorderingapi.service;

import com.soulaichhi.onlinefoodorderingapi.model.*;
import com.soulaichhi.onlinefoodorderingapi.repository.AddressRepository;
import com.soulaichhi.onlinefoodorderingapi.repository.OrderItemRepository;
import com.soulaichhi.onlinefoodorderingapi.repository.OrderRepository;
import com.soulaichhi.onlinefoodorderingapi.repository.UserRepository;
import com.soulaichhi.onlinefoodorderingapi.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private CartService cartService;
    @Override
    public Order createOrder(OrderRequest order, User user) throws Exception {
        Address shippAddress = order.getDeliveryAddress();
        Address savedAddress = addressRepository.save(shippAddress);
        if (!user.getAddresses().contains(savedAddress)){
            user.getAddresses().add(savedAddress);
            userRepository.save(user);
        }
        Restaurant restaurant =restaurantService.findRestaurantById(order.getRestaurantId());
        Order createdOrder = new Order();
        createdOrder.setCustomer(user);
        createdOrder.setRestaurant(restaurant);
        createdOrder.setDeliveryAddress(savedAddress);
        createdOrder.setCreatedAt(new Date());
        createdOrder.setOrderStatus("PENDING");
        Cart cart=cartService.findCartByUserId(user.getId());
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem:cart.getItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setTotalPrice(cartItem.getTotalPrice());
            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
        }
        Long totalPrice = cartService.calculateCartTotals(cart);
        createdOrder.setItems(orderItems);
        createdOrder.setTotalPrice(totalPrice);
        Order savedOrder = orderRepository.save(createdOrder);
        restaurant.getOrders().add(savedOrder);
        return createdOrder;
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Order order = findOrderById(orderId);

        if (orderStatus.equals("OUT_FOR_DELIVERY")
                || orderStatus.equals("DELIVERED")
                || orderStatus.equals("COMPLETED")
                || orderStatus.equals("PENDING")){
            order.setOrderStatus(orderStatus);
            return orderRepository.save(order);
        }
        throw new Exception("Please select a valid order status");
    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<Order> getUsersOrder(Long userId) throws Exception {
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()){
            throw  new Exception("Order not found: " + orderId);
        }
        return order.get();
    }

    @Override
    public List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus) throws Exception {
        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
        if (orderStatus!=null){
            orders = orders.stream().filter(order -> order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());

        }
        return orders;
    }
}
