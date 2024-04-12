package it.noah.orchestrator.clients;

import it.noah.sagacqrs.participant.interfaces.IParticipantClient;
import jakarta.inject.Named;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 *
 * @author NATCRI
 */
@Named("B")
@Path("/b")
@RegisterRestClient(configKey = "b")
public interface ClientB extends IParticipantClient {
}
