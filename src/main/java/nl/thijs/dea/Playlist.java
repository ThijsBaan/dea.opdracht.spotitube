package nl.thijs.dea;

import nl.thijs.dea.dto.PlaylistRequestDto;
import nl.thijs.dea.dummy.DummyPlaylists;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/")
public class Playlist {
    private List<DummyPlaylists> lists = new ArrayList<>();

    public Playlist() {
        lists.add(new DummyPlaylists(1,"Death metal", true, ""));
        lists.add(new DummyPlaylists(2,"Pop", false, ""));
    }

    @GET
    @Path("playlists")
    public Response geefResponse(String token){

        if (true) { // maak hier controle of Token de juiste is!
            PlaylistRequestDto response = new PlaylistRequestDto();

            response.setPlaylists(lists);
            response.setLength(37);

            return Response.ok().entity(response).build();
        } else { // token klopt niet
            return Response.status(403).build();
        }
        //return Response.status(400).build();
    }
}
