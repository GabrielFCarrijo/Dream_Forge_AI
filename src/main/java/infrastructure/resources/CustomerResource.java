package infrastructure.resources;

import application.ApplicationService;
import application.dto.Customer;
import application.dto.ProfilePhoto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.util.List;
import java.util.NoSuchElementException;

@Path("customers")
public class CustomerResource {
    private final ApplicationService service;

    public CustomerResource(ApplicationService service) {
        this.service = service;
    }

    @GET
    public List<Customer> searchCustomers() {
        return service.searchCustomers();
    }

    @GET
    @Path("/{id}")
    public Customer getCustomer(@PathParam("id") String id) {
        try {
            return service.getCustomer(id);
        } catch (NoSuchElementException exception) {
            throw new NotFoundException();
        }
    }

    @POST
    @Path("/{id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @ResponseStatus(RestResponse.StatusCode.ACCEPTED)
    public void persistProfilePhoto(@PathParam("id") String id,
                                    @RestForm("photo") FileUpload fileUpload,
                                    @RestForm("positivePrompt") String positivePrompt,
                                    @RestForm("negativePrompt") String negativePrompt) {
        ProfilePhoto profilePhoto = ProfilePhoto.create(fileUpload);

        service.persistProfilePhoto(id, profilePhoto, positivePrompt, negativePrompt);
    }

    @OPTIONS
    @Path("/customers")
    public Response handleOptions() {
        System.out.println("OPTIONS Request received!");
        return Response.ok()
                .header("Access-Control-Allow-Origin", "http://localhost:3000")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Authorization")
                .build();
    }

}
