package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.SeriesService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.webapp.dtos.NetworkDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.helpers.AbstractUnmarshallerImpl;
import java.util.List;
import java.util.stream.Collectors;

import static javax.ws.rs.core.Response.ok;

@Path("networks")
@Component
public class NetworksController {

    @Autowired
    private SeriesService seriesService;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllNetworks() {
        List<NetworkDTO> networks = seriesService.getAllNetworks().stream()
                .map(n -> new NetworkDTO(n)).collect(Collectors.toList());
        return ok(new GenericEntity<List<NetworkDTO>>(networks){}).build();
    }

}
