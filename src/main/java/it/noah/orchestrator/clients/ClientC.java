package it.noah.orchestrator.clients;

import it.noah.sagacqrs.participant.interfaces.IParticipantClient;
import jakarta.inject.Named;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 *
 * @author NATCRI
 */
@Named("C")
@Path("/c")
@RegisterRestClient(configKey = "c")
public interface ClientC extends IParticipantClient {
}
