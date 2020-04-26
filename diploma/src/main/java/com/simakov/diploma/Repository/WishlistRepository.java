package com.simakov.diploma.Repository;

import java.util.List;

import com.simakov.diploma.Model.Wishlist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByUserEmail(String email);

    void deleteByWishlistIdAndUserEmail(int wishlistid, String email)
    ;
    void deleteAllByUserEmail(String email);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "ALTER TABLE wishlist DROP wishlist_id", nativeQuery = true)
    void dropId();

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "ALTER TABLE wishlist AUTO_INCREMENT = 1", nativeQuery = true)
    void autoIncrement();

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "ALTER TABLE wishlist ADD wishlist_id int UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST;", nativeQuery = true)
    void addId();
}
