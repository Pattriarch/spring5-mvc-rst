package spring.framework.api.v1.mapper;

import org.junit.jupiter.api.Test;
import spring.framework.api.v1.model.CustomerDTO;
import spring.framework.domain.Customer;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {

    public static final String FIRST_NAME = "SERGEY";
    public static final String LAST_NAME = "PAVLOV";
    public static final long ID = 1L;
    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    void customerToCustomerDTO() {
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstname(FIRST_NAME);
        customer.setLastname(LAST_NAME);

        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        assertEquals(FIRST_NAME, customerDTO.getFirstname());
        assertEquals(LAST_NAME, customerDTO.getLastname());
    }
}