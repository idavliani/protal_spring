package com.protal.myApp.Repository;

import com.protal.myApp.Entity.Purchase;
import com.protal.myApp.Entity.Ticket;
import com.protal.myApp.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    User findByTicketsBought(Ticket ticket);

    User findByUserPurchases(Purchase purchase);

    User findByEmailAndPassword(String email, String password);

    User findByUsername(String username);

    List<User> findByUsernameOrEmail(String username, String email);

    @Transactional
    @Modifying
    @Query(value = "update User u set u.name=:name,u.lastName=:lastName, u.telephone=:telephone, " +
            "u.password=:password, u.image=:image where u.id=:id")
    void updateUserValues(@Param("image") byte[] image, @Param("name") String name,
                    @Param("lastName") String lastName, @Param("telephone") Long telephone,
                    @Param("password") String password, @Param("id") Integer id);

    @Transactional
    @Modifying
    @Query(value = "update User u set u.name=:name,u.lastName=:lastName, u.telephone=:telephone, " +
            "u.password=:password where u.id=:id")
    void updateUserValuesNoImage(@Param("name") String name, @Param("lastName") String lastName, @Param("telephone") Long telephone,
                   @Param("password") String password, @Param("id") Integer id);

}