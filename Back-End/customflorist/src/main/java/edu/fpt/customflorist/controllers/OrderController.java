package edu.fpt.customflorist.controllers;

import edu.fpt.customflorist.dtos.Order.OrderDTO;
import edu.fpt.customflorist.exceptions.DataNotFoundException;
import edu.fpt.customflorist.models.Order;
import edu.fpt.customflorist.responses.ResponseObject;
import edu.fpt.customflorist.services.Order.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO orderDTO, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Order order = orderService.createOrder(orderDTO);
            return ResponseEntity.ok().body(
                    ResponseObject.builder()
                            .message("Order created successfully")
                            .data(order)
                            .status(HttpStatus.CREATED)
                            .build()
            );
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<?> updateOrder(@PathVariable Long orderId, @Valid @RequestBody OrderDTO orderDTO, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Order order = orderService.updateOrder(orderId, orderDTO);
            return ResponseEntity.ok().body(
                    ResponseObject.builder()
                            .message("Order updated successfully")
                            .data(order)
                            .status(HttpStatus.OK)
                            .build()
            );
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderId) {
        try {
            orderService.deleteOrder(orderId);
            return ResponseEntity.ok().body(
                    ResponseObject.builder()
                            .message("Order deleted successfully")
                            .status(HttpStatus.NO_CONTENT)
                            .build()
            );
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable Long orderId) {
        try {
            Order order = orderService.getOrderById(orderId);
            return ResponseEntity.ok().body(
                    ResponseObject.builder()
                            .message("Order retrieved successfully")
                            .data(order)
                            .status(HttpStatus.OK)
                            .build()
            );
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "50") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orders = orderService.getAllOrders(pageable);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .message("Orders retrieved successfully")
                        .data(orders)
                        .status(HttpStatus.OK)
                        .build()
        );
    }
}
