package com.stock.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stock.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String name);

}
