package com.example.delivery.application.service;

import com.example.delivery.domain.exception.client.ClientNotFoundException;
import com.example.delivery.domain.exception.order.OrderNotFoundException;
import com.example.delivery.domain.exception.product.ProductNotFoundException;
import com.example.delivery.domain.model.Category;
import com.example.delivery.domain.model.Order;
import com.example.delivery.domain.model.PaymentMethod;
import com.example.delivery.domain.model.Product;
import com.example.delivery.domain.port.out.ClientRepositoryPort;
import com.example.delivery.domain.port.out.OrderRepositoryPort;
import com.example.delivery.domain.port.out.ProductRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@DisplayName("OrdenInteractorService - Tests unitarios")
class OrdenInteractorServiceTest {

    @Mock
    private OrderRepositoryPort orderRepositoryPort;

    @Mock
    private ClientRepositoryPort clientRepositoryPort;

    @Mock
    private ProductRepositoryPort productRepositoryPort;

    @InjectMocks
    private OrdenInteractorService ordenInteractorService;

    private UUID orderUuid;
    private UUID productUuid;
    private Order orderRequest;
    private Product productInDb;

    @BeforeEach
    void setUp() {
        orderUuid = UUID.randomUUID();
        productUuid = UUID.randomUUID();

        orderRequest = new Order();
        orderRequest.setClientDocument("12345678");
        orderRequest.setProductUuid(productUuid);
        orderRequest.setQuantity(2);
        orderRequest.setExtraInformation("Sin cebolla");
        orderRequest.setPaymentMethod(PaymentMethod.TARJETA);

        productInDb = new Product(
                productUuid,
                "HAMBURGUESA CLASICA",
                Category.HAMBURGERS_AND_HOTDOGS,
                "Carne y queso",
                new BigDecimal("20000.00"),
                true
        );
    }

    @Nested
    @DisplayName("createOrder")
    class CreateOrder {

        @Test
        @DisplayName("debe lanzar ClientNotFoundException cuando el cliente no existe")
        void shouldThrowClientNotFoundException_whenClientDoesNotExist() {
            given(clientRepositoryPort.existsByDocument("12345678")).willReturn(false);

            assertThatThrownBy(() -> ordenInteractorService.createOrder(orderRequest))
                    .isInstanceOf(ClientNotFoundException.class)
                    .hasMessageContaining("no existe");

            then(productRepositoryPort).should(never()).getProductByUuid(any(UUID.class));
            then(orderRepositoryPort).should(never()).createOrder(any(Order.class));
        }

        @Test
        @DisplayName("debe lanzar ProductNotFoundException cuando el producto no existe")
        void shouldThrowProductNotFoundException_whenProductDoesNotExist() {
            given(clientRepositoryPort.existsByDocument("12345678")).willReturn(true);
            given(productRepositoryPort.getProductByUuid(productUuid)).willReturn(null);

            assertThatThrownBy(() -> ordenInteractorService.createOrder(orderRequest))
                    .isInstanceOf(ProductNotFoundException.class)
                    .hasMessageContaining("producto");

            then(orderRepositoryPort).should(never()).createOrder(any(Order.class));
        }

        @Test
        @DisplayName("debe crear orden cuando cliente y producto existen")
        void shouldCreateOrder_whenClientAndProductExist() {
            given(clientRepositoryPort.existsByDocument("12345678")).willReturn(true);
            given(productRepositoryPort.getProductByUuid(productUuid)).willReturn(productInDb);
            given(orderRepositoryPort.createOrder(any(Order.class))).willAnswer(invocation -> invocation.getArgument(0));

            Order result = ordenInteractorService.createOrder(orderRequest);

            assertThat(result).isNotNull();
            assertThat(result.getUuid()).isNotNull();
            assertThat(result.getCreationDateTime()).isNotNull();
            assertThat(result.getPrice()).isEqualByComparingTo("20000.00");
            assertThat(result.getSubTotal()).isEqualByComparingTo("40000.00");
            assertThat(result.getTax()).isEqualByComparingTo("7600.00");
            assertThat(result.getGrandTotal()).isEqualByComparingTo("47600.00");
            assertThat(result.isDelivered()).isFalse();
            assertThat(result.getDeliveredDate()).isNull();

            then(orderRepositoryPort).should(times(1)).createOrder(any(Order.class));
        }
    }

    @Nested
    @DisplayName("deleteOrder")
    class DeleteOrder {

        @Test
        @DisplayName("debe eliminar orden cuando existe")
        void shouldDeleteOrder_whenExists() {
            given(orderRepositoryPort.existsByUUID(orderUuid)).willReturn(true);
            willDoNothing().given(orderRepositoryPort).deleteOrder(orderUuid);

            ordenInteractorService.deleteOrder(orderUuid);

            then(orderRepositoryPort).should(times(1)).deleteOrder(orderUuid);
        }

        @Test
        @DisplayName("debe lanzar OrderNotFoundException cuando la orden no existe")
        void shouldThrowOrderNotFoundException_whenOrderDoesNotExist() {
            given(orderRepositoryPort.existsByUUID(orderUuid)).willReturn(false);

            assertThatThrownBy(() -> ordenInteractorService.deleteOrder(orderUuid))
                    .isInstanceOf(OrderNotFoundException.class)
                    .hasMessageContaining("no existe");

            then(orderRepositoryPort).should(never()).deleteOrder(any(UUID.class));
        }
    }

    @Nested
    @DisplayName("deliverOrder")
    class DeliverOrder {

        @Test
        @DisplayName("debe lanzar OrderNotFoundException cuando la orden no existe")
        void shouldThrowOrderNotFoundException_whenOrderDoesNotExist() {
            LocalDateTime deliveredDate = LocalDateTime.now();
            given(orderRepositoryPort.existsByUUID(orderUuid)).willReturn(false);

            assertThatThrownBy(() -> ordenInteractorService.deliverOrder(orderUuid, deliveredDate))
                    .isInstanceOf(OrderNotFoundException.class)
                    .hasMessageContaining(orderUuid.toString());

            then(orderRepositoryPort).should(never()).deliverOrder(any(UUID.class), any(LocalDateTime.class));
        }

        @Test
        @DisplayName("debe retornar orden entregada cuando existe")
        void shouldReturnDeliveredOrder_whenOrderExists() {
            LocalDateTime deliveredDate = LocalDateTime.of(2026, 3, 12, 15, 30, 0);
            Order deliveredOrder = new Order();
            deliveredOrder.setUuid(orderUuid);
            deliveredOrder.setDelivered(true);
            deliveredOrder.setDeliveredDate(deliveredDate);

            given(orderRepositoryPort.existsByUUID(orderUuid)).willReturn(true);
            given(orderRepositoryPort.deliverOrder(orderUuid, deliveredDate)).willReturn(deliveredOrder);

            Order result = ordenInteractorService.deliverOrder(orderUuid, deliveredDate);

            assertThat(result).isNotNull();
            assertThat(result.isDelivered()).isTrue();
            assertThat(result.getDeliveredDate()).isEqualTo(deliveredDate);
            then(orderRepositoryPort).should(times(1)).deliverOrder(orderUuid, deliveredDate);
        }
    }
}

