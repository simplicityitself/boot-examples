package demo.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByLastName(String lastName);

    @Query("select c from demo.data.Customer c where c.lastName like ?1")
    List<Customer> findByLastNameQuery(String lastName);
}
