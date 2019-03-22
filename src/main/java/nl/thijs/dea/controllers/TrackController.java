package nl.thijs.dea.controllers;

import nl.thijs.dea.controllers.dto.TrackResponseDto;
import nl.thijs.dea.datasources.dao.TrackDAO;
import nl.thijs.dea.models.TrackModel;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/")
public class TrackController {
    private TrackDAO trackDAO;

    @Inject
    public void setDAO(TrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }

    @GET
    @Path("/playlists/{forplaylist}/tracks")
    public Response loadTracksPerPlaylist(@PathParam("forplaylist") int forPlayList) {
        var response = new TrackResponseDto();

        response.setTracks(trackDAO.getAllTracksWhoAreInPlaylist(forPlayList));
        return Response.ok().entity(response).build();
    }

    @DELETE
    @Path("/playlists/{forplaylist}/tracks/{id}")
    @Consumes("application/json")
    public Response deleteTrackFromPlaylist(@QueryParam("token") String token,
                                            @PathParam("forplaylist") int forPlayList,
                                          @PathParam("id") int trackID) {
        trackDAO.deleteTrackFromPlaylist(token, forPlayList, trackID);
        return loadTracksPerPlaylist(forPlayList);
    }

    @POST
    @Path("/playlists/{forplaylist}/tracks/")
    @Consumes("application/json")
    @Produces("application/json")
    //@Produces("application/json")
    public Response addTrackToPlaylist(@PathParam("forplaylist") int forPlayList,
                                        TrackModel request){
        trackDAO.addTrackToPlaylist(forPlayList, request.getId(), request.getOfflineAvailable());
        return loadTracksPerPlaylist(forPlayList);
    }

    @GET
    @Path("/tracks")
    @Consumes("application/json")
    public Response loadTracksForAdd(@QueryParam("Token") String token, @QueryParam("forPlaylist") int forPlayList){
        var response = new TrackResponseDto();

        response.setTracks(trackDAO.getAllTracksWhoArentInPlaylist(forPlayList));

        return Response.ok().entity(response).build();
    }


}
