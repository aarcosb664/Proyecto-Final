package aarcosb.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import aarcosb.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUserName(String userName);

    @Query(value = "SELECT COUNT(l.id) FROM listings l WHERE l.user_id = :userId", nativeQuery = true)
    int countTotalListings(Long userId);
}
