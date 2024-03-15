package com.example.webprojectgames.repositories;

import com.example.webprojectgames.model.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role getRoleByRoleName(String name);
}
