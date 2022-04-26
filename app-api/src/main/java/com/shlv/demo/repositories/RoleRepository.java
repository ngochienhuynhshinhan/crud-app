package com.shlv.demo.repositories;

import com.shlv.demo.models.ERole;
import com.shlv.demo.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
