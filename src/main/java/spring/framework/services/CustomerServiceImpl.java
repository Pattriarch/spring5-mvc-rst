package spring.framework.services;

import org.springframework.stereotype.Service;
import spring.framework.api.v1.mapper.CustomerMapper;
import spring.framework.api.v1.model.CustomerDTO;
import spring.framework.controllers.v1.CustomerController;
import spring.framework.domain.Customer;
import spring.framework.exceptions.ResourceNotFoundException;
import spring.framework.repositories.CustomerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    customerDTO.setCustomerUrl(getCustomerUrl(customer.getId()));
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    customerDTO.setCustomerUrl(getCustomerUrl(customer.getId()));
                    return customerDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);

        Customer savedCustomer = customerRepository.save(customer);

        CustomerDTO returnDTO = customerMapper.customerToCustomerDTO(savedCustomer);

        returnDTO.setCustomerUrl(getCustomerUrl(savedCustomer.getId()));

        return returnDTO;
    }

    private CustomerDTO savedAndReturnDTO(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);

        CustomerDTO returnDTO = customerMapper.customerToCustomerDTO(savedCustomer);

        returnDTO.setCustomerUrl(getCustomerUrl(savedCustomer.getId()));

        return returnDTO;
    }

    @Override
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        customer.setId(id);

        return savedAndReturnDTO(customer);
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id).map(customer -> {

            if (customerDTO.getFirstname() != null) {
                customer.setFirstname(customerDTO.getFirstname());
            }

            if (customerDTO.getLastname() != null) {
                customer.setLastname(customerDTO.getLastname());
            }

            CustomerDTO returnDTO = customerMapper.customerToCustomerDTO(customerRepository.save(customer));

            returnDTO.setCustomerUrl("/api/v1/customer/" + id);

            return returnDTO;
        }).orElseThrow(ResourceNotFoundException::new);
    }

    private String getCustomerUrl(Long id) {
        return CustomerController.BASE_URL + "/" + id;
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }
}
