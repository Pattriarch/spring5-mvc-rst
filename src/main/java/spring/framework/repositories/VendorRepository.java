package spring.framework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.framework.domain.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
