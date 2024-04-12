/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.noah.orchestrator;

import io.quarkus.runtime.Startup;
import io.smallrye.mutiny.Uni;
import it.noah.common.ABCEventDetails;
import it.noah.common.ABEventDetails;
import it.noah.common.SingleABCEventDetails;
import it.noah.orchestrator.clients.ClientA;
import it.noah.orchestrator.clients.ClientB;
import it.noah.orchestrator.clients.ClientC;
import it.noah.sagacqrs.dao.dto.Participant;
import it.noah.sagacqrs.orchestrator.Orchestrator;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.Serializable;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

/**
 *
 * @author NATCRI
 */
@Path("/abc")
@Startup
@ApplicationScoped
public class ResourceABC implements Serializable {

    private static final long serialVersionUID = -6563479097025118280L;

    private Participant[] abParticipants;

    @Inject
    Logger log;

    @Inject
    Orchestrator orchestrator;

    @Inject
    @RestClient
    ClientA clientA;

    @Inject
    @RestClient
    ClientB clientB;

    @Inject
    @RestClient
    ClientC clientC;

    @PostConstruct
    void init() {
        orchestrator.init(log,
                new Participant(clientA),
                new Participant(clientB),
                new Participant(clientC));
        abParticipants = new Participant[]{new Participant(clientA, null, 30000L), new Participant(clientB, null, 45000L)};
    }

    @GET
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> create() {
        return orchestrator.saga("CREATE_ABC", new ABCEventDetails());
    }

    @GET
    @Path("/delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> delete(@PathParam(value = "id") Long id, @QueryParam(value = "optlock") Long optlock) {
        return orchestrator.saga("DELETE_ABC", new ABCEventDetails(id, optlock));
    }

    @GET
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> update(@PathParam(value = "id") Long id, @QueryParam(value = "optlock") Long optlock,
            @QueryParam(value = "prefix") String titlePrefix) {
        return orchestrator.saga("UPDATE_ABC", new ABCEventDetails(id, optlock, titlePrefix));
    }

    @GET
    @Path("/readall")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> readAll() {
        return orchestrator.cqrs("READ_ALL_AB", new ABEventDetails(), abParticipants);
    }

    @GET
    @Path("/readone/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> readAll(@PathParam(value = "id") Long id) {
        return orchestrator.cqrs("READ_ONE_ABC", new SingleABCEventDetails(id));
    }

}
