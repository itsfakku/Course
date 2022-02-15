package com.btec.cms.repository;

import com.btec.cms.entity.RoleEntity;
import com.btec.cms.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    /**
     * Find role by role name
     *
     * @param role role name
     * @return {@link Optional<RoleEntity>}
     */
    Optional<RoleEntity> findByRole(String role);
}
