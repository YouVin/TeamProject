package com.example.demo.User;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.context.annotation.Bean;


public interface UserRepository extends JpaRepository<User, Long> {
	
    Optional<User> findUserByEmailAndProvider(String email, String provider); 
}
	
//	private final EntityManager em;
//	
//	public Optional<User> findOneByEmail(String email){
//		return em.createQuery("select u from User u where u.email = :email", User.class)
//				.setParameter("email", email)
//				.getResultList()
//				.stream().findAny();
//	}
//	
//	public User save(User user) {
//		if(user.getId() == null) {
//			user.setProfile_yn("N");
//			em.persist(user);
//		} else {
//			em.merge(user);
//		}
//		return user;
//	}
