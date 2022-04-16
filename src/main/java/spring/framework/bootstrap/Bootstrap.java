package spring.framework.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import spring.framework.domain.Category;
import spring.framework.domain.Customer;
import spring.framework.domain.Vendor;
import spring.framework.repositories.CategoryRepository;
import spring.framework.repositories.CustomerRepository;
import spring.framework.repositories.VendorRepository;

@Component
public class Bootstrap implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadCustomers();
        loadVendors();
    }

    public void loadCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        System.out.println("Category data Loaded = " + categoryRepository.count());
    }

    public void loadCustomers() {
        Customer joe = new Customer();
        joe.setFirstname("Joe");
        joe.setLastname("Newman");

        Customer michael = new Customer();
        michael.setFirstname("Michael");
        michael.setLastname("Lachappele");

        Customer david = new Customer();
        david.setFirstname("David");
        david.setLastname("Winter");

        Customer anne = new Customer();
        anne.setFirstname("Anne");
        anne.setLastname("Hine");

        Customer alice = new Customer();
        alice.setFirstname("Alice");
        alice.setLastname("Eastman");

        Customer freddy = new Customer();
        freddy.setFirstname("Freddy");
        freddy.setLastname("Meyers");

        customerRepository.save(joe);
        customerRepository.save(michael);
        customerRepository.save(david);
        customerRepository.save(anne);
        customerRepository.save(alice);
        customerRepository.save(freddy);

        System.out.println("Customer data Loaded = " + customerRepository.count());
    }

    public void loadVendors() {
        Vendor vendor1 = new Vendor();
        vendor1.setName("Vendor 1");
        vendorRepository.save(vendor1);

        Vendor vendor2 = new Vendor();
        vendor2.setName("Vendor 2");
        vendorRepository.save(vendor2);

        System.out.println("Vendor data Loaded = " + vendorRepository.count());
    }
}
