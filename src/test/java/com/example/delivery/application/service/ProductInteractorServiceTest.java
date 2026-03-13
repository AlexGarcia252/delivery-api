package com.example.delivery.application.service;

import com.example.delivery.domain.exception.product.ProductNotFoundException;
import com.example.delivery.domain.model.Category;
import com.example.delivery.domain.model.Product;
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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductInteractorService - Tests unitarios")
class ProductInteractorServiceTest {

    @Mock
    private ProductRepositoryPort productRepositoryPort;

    @InjectMocks
    private ProductInteractorService productInteractorService;

    private Product productRequest;
    private UUID productUuid;

    @BeforeEach
    void setUp() {
        productUuid = UUID.randomUUID();
        productRequest = new Product(
                null,
                "Hamburguesa especial",
                Category.HAMBURGERS_AND_HOTDOGS,
                "Carne, queso y tocineta",
                new BigDecimal("25000.00"),
                true
        );
    }

    @Nested
    @DisplayName("createProduct")
    class CreateProduct {

        @Test
        @DisplayName("debe crear producto cuando el nombre no existe")
        void shouldCreateProduct_whenNameDoesNotExist() {
            given(productRepositoryPort.existsByName("HAMBURGUESA ESPECIAL")).willReturn(false);
            given(productRepositoryPort.createProduct(any(Product.class))).willAnswer(invocation -> invocation.getArgument(0));

            Product result = productInteractorService.createProduct(productRequest);

            assertThat(result).isNotNull();
            assertThat(result.getUuid()).isNotNull();
            assertThat(result.getName()).isEqualTo("HAMBURGUESA ESPECIAL");
            assertThat(result.getBasePrice()).isEqualByComparingTo("25000.00");

            then(productRepositoryPort).should(times(1)).existsByName("HAMBURGUESA ESPECIAL");
            then(productRepositoryPort).should(times(1)).createProduct(any(Product.class));
        }

        @Test
        @DisplayName("debe lanzar IllegalArgumentException cuando el nombre ya existe")
        void shouldThrowIllegalArgumentException_whenNameAlreadyExists() {
            given(productRepositoryPort.existsByName("HAMBURGUESA ESPECIAL")).willReturn(true);

            assertThatThrownBy(() -> productInteractorService.createProduct(productRequest))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("producto ya existe");

            then(productRepositoryPort).should(never()).createProduct(any(Product.class));
        }
    }

    @Nested
    @DisplayName("execute (getProductByUuid)")
    class Execute {

        @Test
        @DisplayName("debe retornar producto cuando existe")
        void shouldReturnProduct_whenExists() {
            Product productInDb = new Product(
                    productUuid,
                    "HAMBURGUESA ESPECIAL",
                    Category.HAMBURGERS_AND_HOTDOGS,
                    "Carne, queso y tocineta",
                    new BigDecimal("25000.00"),
                    true
            );

            given(productRepositoryPort.getProductByUuid(productUuid)).willReturn(productInDb);

            Product result = productInteractorService.execute(productUuid);

            assertThat(result).isNotNull();
            assertThat(result.getUuid()).isEqualTo(productUuid);
            assertThat(result.getName()).isEqualTo("HAMBURGUESA ESPECIAL");
        }

        @Test
        @DisplayName("debe lanzar ProductNotFoundException cuando no existe")
        void shouldThrowProductNotFoundException_whenNotExists() {
            given(productRepositoryPort.getProductByUuid(productUuid)).willReturn(null);

            assertThatThrownBy(() -> productInteractorService.execute(productUuid))
                    .isInstanceOf(ProductNotFoundException.class)
                    .hasMessageContaining(productUuid.toString());
        }
    }

    @Nested
    @DisplayName("updateProduct")
    class UpdateProduct {

        @Test
        @DisplayName("debe delegar actualización al repositorio con todos los parámetros")
        void shouldDelegateUpdateToRepository() {
            willDoNothing().given(productRepositoryPort)
                    .updateProduct(productUuid, "Pizza", Category.KIDS_MEALS, "Masa artesanal", new BigDecimal("32000.00"), true);

            productInteractorService.updateProduct(
                    productUuid,
                    "Pizza",
                    Category.KIDS_MEALS,
                    "Masa artesanal",
                    new BigDecimal("32000.00"),
                    true
            );

            then(productRepositoryPort).should(times(1))
                    .updateProduct(productUuid, "Pizza", Category.KIDS_MEALS, "Masa artesanal", new BigDecimal("32000.00"), true);
        }
    }

    @Nested
    @DisplayName("deleteProduct")
    class DeleteProduct {

        @Test
        @DisplayName("debe eliminar producto cuando existe")
        void shouldDeleteProduct_whenExists() {
            given(productRepositoryPort.existsByUUID(productUuid)).willReturn(true);
            willDoNothing().given(productRepositoryPort).deleteByUuid(productUuid);

            boolean result = productInteractorService.deleteProduct(productUuid);

            assertThat(result).isTrue();
            then(productRepositoryPort).should(times(1)).deleteByUuid(productUuid);
        }

        @Test
        @DisplayName("debe lanzar ProductNotFoundException cuando no existe")
        void shouldThrowProductNotFoundException_whenNotExists() {
            given(productRepositoryPort.existsByUUID(productUuid)).willReturn(false);

            assertThatThrownBy(() -> productInteractorService.deleteProduct(productUuid))
                    .isInstanceOf(ProductNotFoundException.class)
                    .hasMessageContaining("Producto no existe");

            then(productRepositoryPort).should(never()).deleteByUuid(any(UUID.class));
        }
    }

    @Nested
    @DisplayName("IvalidationUUid")
    class ValidationUuid {

        @Test
        @DisplayName("debe retornar true cuando el UUID es válido")
        void shouldReturnTrue_whenUuidIsValid() {
            boolean result = productInteractorService.IvalidationUUid(productUuid);
            assertThat(result).isTrue();
        }
    }
}


