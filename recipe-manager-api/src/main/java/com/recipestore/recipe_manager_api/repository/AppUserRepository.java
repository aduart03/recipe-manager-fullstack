package com.recipestore.recipe_manager_api.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.recipestore.recipe_manager_api.model.AppUser;



public interface AppUserRepository extends JpaRepository<AppUser,Long>{

    Optional<AppUser> findByUsername(String username);

    boolean existsByUsername(String username);

}
