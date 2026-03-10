package com.example.delivery.domain.port.in.iProduct;

import java.util.UUID;

public interface IDeleteProductInteractor {
    boolean deleteProduct(UUID uuid);
    boolean IvalidationUUid(UUID uuid);
}
