package guru.springfamework.services.interfaces;

import guru.springfamework.api.v1.model.CustomerDTO;

import java.util.List;

public interface CustomerService {

    List<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomerByName(String name);

    CustomerDTO getCustomerById(Long id);

    CustomerDTO createNewCustomer(CustomerDTO customerDTO);

    CustomerDTO saveCustomerByIdDTO(Long id, CustomerDTO customerDTO);

    CustomerDTO patchCustomerByIdDTO(Long id, CustomerDTO customerDTO);

    void deleteCustomerByIdDTO(Long id);
}
