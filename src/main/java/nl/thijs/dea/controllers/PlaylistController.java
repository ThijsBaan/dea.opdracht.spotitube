package nl.thijs.dea.controllers;

import nl.thijs.dea.dto.LoginResponseDto;
import nl.thijs.dea.dto.PlaylistRequestDto;
import nl.thijs.dea.dummy.DummyPlaylists;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * PlaylistController --- class for letting user view the playlists
 * @author Thijs
 */
@Path("/")
public class PlaylistController {
    private List<DummyPlaylists> musicList = new ArrayList<>();
    private String token;

    /**
     * Constructor:
     * add two DummyPlaylists for PlaylistController and add them to the ArrayList musicList.
     */
    public PlaylistController() {
        musicList.add(new DummyPlaylists(1, "Death metal", true, ""));
        musicList.add(new DummyPlaylists(2, "Pop", false, ""));
        musicList.add(new DummyPlaylists(3, "Disney", true, ""));
    }

    @GET
    @Path("playlists")
    @Produces("application/json")
    public Response geefResponse(@QueryParam("token") String token) {
        if ("".equals(token)) {
            // 400: Bad Request. Something is wrong with the request.
            // This could be due to a missing query-parameter for the token.
            return Response.status(400).build();
        } else if ("ABCDEFG".equals(token)) { //"ABCDEFG" vervangen voor opgeslagen code uit database
            PlaylistRequestDto response = new PlaylistRequestDto();

            response.setPlaylists(musicList);
            response.setLength(37);

            return Response.ok().entity(response).build();
        } else {
            // 403: Forbidden. The request was valid, but you have requested a resource for which are not authorized.
            // This will probably mean you have provided a token that is invalid.
            return Response.status(403).build();
        }
    }
}
