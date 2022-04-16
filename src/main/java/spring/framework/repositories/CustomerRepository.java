package spring.framework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.framework.domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
