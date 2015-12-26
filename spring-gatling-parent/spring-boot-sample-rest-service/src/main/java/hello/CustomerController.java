package hello;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/customer", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
public class CustomerController {

    private Map<String, Customer> customers = new HashMap<String, Customer>();

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getCustomer(@PathVariable("id") String id) {
        Customer customer = customers.get(id);
        ResponseEntity responseEntity;
        if (customer == null) {
            responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            responseEntity = new ResponseEntity(customer, HttpStatus.OK);
        }
        return responseEntity;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity createCustomer(@RequestBody Customer customer) {
        String id = UUID.randomUUID().toString();
        customer.setId(id);
        customers.put(id, customer);
        return new ResponseEntity(customer, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateCustomer(@RequestBody Customer customer) {
        ResponseEntity responseEntity;
        if(customers.get(customer.getId()) != null) {
            customers.put(customer.getId(), customer);
            responseEntity = new ResponseEntity(HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteCustomer(@PathVariable("id") String id) {
        customers.remove(id);
    }
}
