package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by jt on 9/24/17.
 */
@Component
public class Bootstrap implements CommandLineRunner{
    private final VendorRepository vendorRepository;
    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;

    public Bootstrap(VendorRepository vendorRepository,
                     CategoryRepository categoryRepository,
                     CustomerRepository customerRepository) {
        this.vendorRepository = vendorRepository;
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        initCategoryRepository();
        initCustomerRepository();
        initVendorRepository();
    }
    
    public void initCategoryRepository(){
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


        System.out.println("Data Loaded = " + categoryRepository.count() );

    }
    
    public void initCustomerRepository(){
        Customer customer1 = new Customer();
        customer1.setFirstname("Jim");
        customer1.setLastname("Atterson");
        customer1.setCustomerUrl("");

        Customer customer2 = new Customer();
        customer2.setFirstname("Michael");
        customer2.setLastname("Batson");

        customerRepository.save(customer1);
        customerRepository.save(customer2);


        System.out.println("Customer Data Loaded = " + customerRepository.count() );
        
    }
    
    
    public void initVendorRepository(){
        Vendor vendor1 = new Vendor();
        vendor1.setName("Vendor 1");
        vendor1.setVendorUrl("http://SomeVendorURL1");

        Vendor vendor2 = new Vendor();
        vendor2.setName("Vendor 2");
        vendor2.setVendorUrl("http://SomeVendorURL2");

        vendorRepository.save(vendor1);
        vendorRepository.save(vendor2);

        System.out.println("Vendor Data Loaded = " + vendorRepository.count() );
    }
}
