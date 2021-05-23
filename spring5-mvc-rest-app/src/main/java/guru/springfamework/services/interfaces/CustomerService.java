package guru.springfamework.services.interfaces;

import guru.springfamework.model.CustomerDTO;

import java.util.List;

public interface CustomerService {

    List<? extends CustomerDTO> getAllCustomers();

    CustomerDTO getCustomerByName(String name);

    CustomerDTO getCustomerById(Long id);

    CustomerDTO createNewCustomer(CustomerDTO customerDTO);

    CustomerDTO saveCustomerById(Long id, CustomerDTO customerDTO);

    CustomerDTO patchCustomerById(Long id, CustomerDTO customerDTO);

    void deleteCustomerById(Long id);
}
