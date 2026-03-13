package com.example.delivery.application.service;

import com.example.delivery.domain.exception.client.ClientConflictException;
import com.example.delivery.domain.exception.client.ClientNotFoundException;
import com.example.delivery.domain.exception.client.InvalidClientDocumentException;
import com.example.delivery.domain.model.Client;
import com.example.delivery.domain.port.out.ClientRepositoryPort;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ClientInteractorService - Tests unitarios")
class ClientInteractorServiceTest {

    @Mock
    private ClientRepositoryPort clientRepositoryPort;

    @InjectMocks
    private ClientInteractorService clientInteractorService;

    // Datos reutilizables
    private Client clientRequest;
    private Client clientInDb;

    @BeforeEach
    void setUp() {
        clientRequest = new Client(
                "CC-12345678",
                "Juan Pérez",
                "juan@example.com",
                "3001234567",
                "Calle 1 # 2-3"
        );
        clientInDb = new Client(
                "CC-12345678",
                "Juan Pérez",
                "juan@example.com",
                "3001234567",
                "Calle 1 # 2-3"
        );
    }

    // ─────────────────────────────────────────────────
    // createClient
    // ─────────────────────────────────────────────────
   // @Disabled
    @Nested
    @DisplayName("createClient")
    class CreateClient {

        @Test
        @DisplayName("debe retornar el cliente creado cuando el documento no existe aún en BD")
        void shouldReturnCreatedClient_whenDocumentIsNew() {
            // Arrange
            given(clientRepositoryPort.existsByDocument("CC-12345678")).willReturn(false);
            given(clientRepositoryPort.createClient(clientRequest)).willReturn(clientRequest);

            // Act
            Client result = clientInteractorService.createClient(clientRequest);

            // Assert
            assertThat(result).isEqualTo(clientRequest);
            then(clientRepositoryPort).should(times(1)).createClient(clientRequest);
        }

        @Test
        @DisplayName("debe lanzar ClientConflictException cuando el documento ya existe en BD")
        void shouldThrowClientConflictException_whenDocumentAlreadyExists() {
            // Arrange
            given(clientRepositoryPort.existsByDocument("CC-12345678")).willReturn(true);

            // Act & Assert
            assertThatThrownBy(() -> clientInteractorService.createClient(clientRequest))
                    .isInstanceOf(ClientConflictException.class)
                    .hasMessageContaining("CC-12345678");

            then(clientRepositoryPort).should(never()).createClient(any());
        }

        @Test
        @DisplayName("debe lanzar InvalidClientDocumentException cuando el documento no cumple regex")
        void shouldThrowInvalidClientDocumentException_whenDocumentFormatIsInvalid() {
            Client invalidClient = new Client(
                    "12345678",
                    "Juan Pérez",
                    "juan@example.com",
                    "3001234567",
                    "Calle 1 # 2-3"
            );

            assertThatThrownBy(() -> clientInteractorService.createClient(invalidClient))
                    .isInstanceOf(InvalidClientDocumentException.class)
                    .hasMessageContaining("CC|CE|P");

            then(clientRepositoryPort).should(never()).existsByDocument(anyString());
            then(clientRepositoryPort).should(never()).createClient(any());
        }
    }

    // ─────────────────────────────────────────────────
    // execute  (getClientById)
    // ─────────────────────────────────────────────────
    @Nested
    @DisplayName("execute (getClientById)")
    class Execute {

        @Test
        @DisplayName("debe retornar el cliente cuando el documento existe")
        void shouldReturnClient_whenDocumentExists() {
            // Arrange
            given(clientRepositoryPort.getClientById("CC-12345678")).willReturn(clientInDb);

            // Act
            Client result = clientInteractorService.execute("CC-12345678");

            // Assert
            assertThat(result).isNotNull();
            assertThat(result.getDocument()).isEqualTo("CC-12345678");
            assertThat(result.getNameAndSurname()).isEqualTo("Juan Pérez");
        }

        @Test
        @DisplayName("debe lanzar ClientNotFoundException cuando el documento no existe")
        void shouldThrowClientNotFoundException_whenDocumentDoesNotExist() {
            // Arrange
            given(clientRepositoryPort.getClientById("CC-99999999")).willReturn(null);

            // Act & Assert
            assertThatThrownBy(() -> clientInteractorService.execute("CC-99999999"))
                    .isInstanceOf(ClientNotFoundException.class)
                    .hasMessageContaining("CC-99999999");
        }
    }

    // ─────────────────────────────────────────────────
    // updateClient
    // ─────────────────────────────────────────────────
    @Nested
    @DisplayName("updateClient")
    class UpdateClient {

        @Test
        @DisplayName("debe retornar el cliente actualizado cuando hay al menos un campo diferente")
        void shouldReturnUpdatedClient_whenChangesAreDetected() {
            // Arrange — solo cambia el nombre
            Client requestWithChanges = new Client(
                    "CC-12345678",
                    "Juan Carlos Pérez",   // <- cambio
                    "juan@example.com",
                    "3001234567",
                    "Calle 1 # 2-3"
            );
            Client savedResult = new Client(
                    "CC-12345678",
                    "Juan Carlos Pérez",
                    "juan@example.com",
                    "3001234567",
                    "Calle 1 # 2-3"
            );

            given(clientRepositoryPort.getClientById("CC-12345678")).willReturn(clientInDb);
            given(clientRepositoryPort.existsByDocument("CC-12345678")).willReturn(true);
            given(clientRepositoryPort.updateClient("CC-12345678", requestWithChanges)).willReturn(savedResult);

            // Act
            Client result = clientInteractorService.updateClient("CC-12345678", requestWithChanges);

            // Assert
            assertThat(result.getNameAndSurname()).isEqualTo("Juan Carlos Pérez");
            then(clientRepositoryPort).should(times(1)).updateClient("CC-12345678", requestWithChanges);
        }

        @Test
        @DisplayName("debe lanzar ClientNotFoundException cuando el documento no existe en BD")
        void shouldThrowClientNotFoundException_whenDocumentDoesNotExist() {
            // Arrange
            given(clientRepositoryPort.getClientById("CC-99999999")).willReturn(null);
            given(clientRepositoryPort.existsByDocument("CC-99999999")).willReturn(false);

            // Act & Assert
            assertThatThrownBy(() -> clientInteractorService.updateClient("CC-99999999", clientRequest))
                    .isInstanceOf(ClientNotFoundException.class)
                    .hasMessageContaining("CC-99999999");

            then(clientRepositoryPort).should(never()).updateClient(anyString(), any());
        }

        @Test
        @DisplayName("debe lanzar ClientConflictException cuando no se detectan cambios en ningún campo")
        void shouldThrowClientConflictException_whenNoChangesDetected() {
            // Arrange — clientRequest tiene exactamente los mismos datos que clientInDb
            given(clientRepositoryPort.getClientById("CC-12345678")).willReturn(clientInDb);
            given(clientRepositoryPort.existsByDocument("CC-12345678")).willReturn(true);

            // Act & Assert
            assertThatThrownBy(() -> clientInteractorService.updateClient("CC-12345678", clientRequest))
                    .isInstanceOf(ClientConflictException.class)
                    .hasMessageContaining("No se detectaron cambios");

            then(clientRepositoryPort).should(never()).updateClient(anyString(), any());
        }
    }

    // ─────────────────────────────────────────────────
    // deleteClient
    // ─────────────────────────────────────────────────
    @Nested
    @DisplayName("deleteClient")
    class DeleteClient {

        @Test
        @DisplayName("debe llamar a deleteClient en el repositorio cuando el cliente existe")
        void shouldCallRepoDelete_whenClientExists() {
            // Arrange
            given(clientRepositoryPort.existsByDocument("CC-12345678")).willReturn(true);
            willDoNothing().given(clientRepositoryPort).deleteClient("CC-12345678");

            // Act
            clientInteractorService.deleteClient("CC-12345678");

            // Assert
            then(clientRepositoryPort).should(times(1)).deleteClient("CC-12345678");
        }

        @Test
        @DisplayName("debe lanzar ClientNotFoundException cuando el cliente no existe")
        void shouldThrowClientNotFoundException_whenClientDoesNotExist() {
            // Arrange
            given(clientRepositoryPort.existsByDocument("CC-99999999")).willReturn(false);

            // Act & Assert
            assertThatThrownBy(() -> clientInteractorService.deleteClient("CC-99999999"))
                    .isInstanceOf(ClientNotFoundException.class)
                    .hasMessageContaining("CC-99999999");
            then(clientRepositoryPort).should(never()).deleteClient(anyString());
        }
    }
}

