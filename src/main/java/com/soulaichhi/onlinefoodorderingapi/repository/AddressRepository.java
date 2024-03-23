package com.soulaichhi.onlinefoodorderingapi.repository;

import com.soulaichhi.onlinefoodorderingapi.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
