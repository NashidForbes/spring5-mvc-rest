package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.controllers.v1.CustomerController;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.services.interfaces.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerMapper customerMapper,
                               CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() throws ResourceNotFoundException {
        return customerRepository.findAll()
                .stream()
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    customerDTO.setCustomerUrl("/api/v1/customers/" + customer.getId());
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerByName(String name) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::customerToCustomerDTO)
                .map(customerDTO -> {
                    //set API URL
                    customerDTO.setCustomerUrl(CustomerController.BASE_URL + "/" + id);
                    return customerDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        CustomerDTO returnDto = saveCustomerDTO(customer);
        return returnDto;
    }

    private CustomerDTO saveCustomerDTO(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        savedCustomer.setCustomerUrl("/api/v1/customer/" + savedCustomer.getId());
        CustomerDTO returnDto = customerMapper.customerToCustomerDTO(savedCustomer);
        return returnDto;
    }

    @Override
    public CustomerDTO saveCustomerByIdDTO(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        CustomerDTO savedCustomerDTO = saveCustomerDTO(customer);
        savedCustomerDTO.setId(id);
        return savedCustomerDTO;
    }

    // using http PATCH method
    @Override
    public CustomerDTO patchCustomerByIdDTO(Long id, CustomerDTO customerDTO) throws ResourceNotFoundException {
        return customerRepository.findById(id).map(customer -> {

            if(customerDTO.getFirstname() != null){
                customer.setFirstname(customerDTO.getFirstname());
            }

            if(customerDTO.getLastname() != null){
                customer.setLastname(customerDTO.getLastname());
            }

            return saveCustomerDTO(customerMapper.customerDTOToCustomer(customerDTO));
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteCustomerByIdDTO(Long id) {
        log.info("Delete customer id " + id);
        customerRepository.deleteById(id);
    }


}
