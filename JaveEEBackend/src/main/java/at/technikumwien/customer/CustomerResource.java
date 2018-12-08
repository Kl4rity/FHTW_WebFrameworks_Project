package at.technikumwien.customer;

import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

@Path("/customers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {
	private static final Logger LOGGER = Logger.getLogger(CustomerService.class.getName()); 
	
	@PersistenceContext
	private EntityManager em;
	@Inject
	private CustomerService customersService;
	@Context
	private UriInfo uriInfo;
	
	@POST
	@Transactional
	public Response create(Customer customers) {
		LOGGER.info("create >> customers=" + customers);
		
		em.persist(customers);
		URI uri = uriInfo.getAbsolutePathBuilder().path(customers.getId().toString()).build();
		return Response.created(uri).build();
	}
	
	@GET
	@Path("/{id}")
	public Customer retrieve(@PathParam("id") long id) {
		LOGGER.info("retrieve >> id=" + id);
		
		return em.find(Customer.class, id);
	}
	
	@PUT
	@Path("/{id}")
	@Transactional
	public void update(@PathParam("id") long id, Customer customerNew) {
		LOGGER.info("update >> id=" + id + ", customers=" + customerNew);

		Customer customerOld = em.find(Customer.class, id);
		if (customerOld != null) {
			customerOld.setFirstname(customerNew.getFirstname());
			customerOld.setLastname(customerNew.getLastname());
			customerOld.setActiveState(customerNew.getActiveState());
			customerOld.setBirthDate(customerNew.getBirthDate());
		}
		else {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
	}
	
	@DELETE
	@Path("/{id}")
	@Transactional
	public void delete(@PathParam("id") long id) {
		LOGGER.info("delete >> id=" + id);
		
		Customer customer = em.find(Customer.class, id);
		if (customer != null) {
			em.remove(customer);
		}
		else {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
	}	
	
	@GET
	public List<Customer> retrieveAll() {
		LOGGER.info("retrieveAll");

		return customersService.getAllCustomers();  
	}
}