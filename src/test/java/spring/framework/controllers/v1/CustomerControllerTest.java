package spring.framework.controllers.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import spring.framework.api.v1.model.CustomerDTO;
import spring.framework.controllers.RestResponseEntityExceptionHandler;
import spring.framework.exceptions.ResourceNotFoundException;
import spring.framework.services.CustomerService;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static spring.framework.controllers.v1.AbstractRestControllerTest.asJsonString;

class CustomerControllerTest {

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    void getAllCustomers() throws Exception {
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstname("Sergey");
        customer1.setLastname("Leontiev");
        customer1.setCustomerUrl(CustomerController.BASE_URL + "/1");

        CustomerDTO customer2 = new CustomerDTO();
        customer2.setFirstname("Lev");
        customer2.setLastname("Tolstoy");
        customer1.setCustomerUrl(CustomerController.BASE_URL + "/2");

        when(customerService.getAllCustomers()).thenReturn(Arrays.asList(customer1, customer2));

        mockMvc.perform(get(CustomerController.BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void testGetCustomerById() throws Exception {

        //given
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstname("Michale");
        customer1.setLastname("Weston");
        customer1.setCustomerUrl(CustomerController.BASE_URL + "/1");

        when(customerService.getCustomerById(anyLong())).thenReturn(customer1);

        //when
        mockMvc.perform(get(CustomerController.BASE_URL + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("Michale")));
    }

    @Test
    public void createNewCustomer() throws Exception {
        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname("Fred");
        customer.setLastname("Flintstone");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstname(customer.getFirstname());
        returnDTO.setLastname(customer.getLastname());
        returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");

        when(customerService.createNewCustomer(customer)).thenReturn(returnDTO);

        //when/then
        mockMvc.perform(post(CustomerController.BASE_URL + "/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", equalTo("Fred")))
                .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname("Fred");
        customer.setLastname("Flintstone");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstname(customer.getFirstname());
        returnDTO.setLastname(customer.getLastname());
        returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");

        when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        //when/then
        mockMvc.perform(put(CustomerController.BASE_URL + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("Fred")))
                .andExpect(jsonPath("$.lastname", equalTo("Flintstone")))
                .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
    }

    @Test
    public void testPatchCustomer() throws Exception {

        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname("Fred");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstname(customer.getFirstname());
        returnDTO.setLastname("Flintstone");
        returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");

        when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(patch(CustomerController.BASE_URL + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("Fred")))
                .andExpect(jsonPath("$.lastname", equalTo("Flintstone")))
                .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
    }

    @Test
    public void testDeleteCustomer() throws Exception {

        mockMvc.perform(delete(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService).deleteCustomerById(anyLong());
    }

    @Test
    public void testNotFoundException() throws Exception {

        when(customerService.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(CustomerController.BASE_URL + "/222")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}