package nl.thijs.dea.controllers;

import nl.thijs.dea.services.TrackService;
import nl.thijs.dea.services.models.TrackModel;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/")
public class TrackController {
    private TrackService trackService;

    @Inject
    public void setService(TrackService trackService) {
        this.trackService = trackService;
    }

    @GET
    @Path("/playlists/{forplaylist}/tracks")
    public Response loadTracksPerPlaylist(@PathParam("forplaylist") int forPlayList) {
        var response = trackService.loadTracksPerPlaylist(forPlayList);
        return Response.ok().entity(response).build();
    }

    @DELETE
    @Path("/playlists/{forplaylist}/tracks/{id}")
    @Consumes("application/json")
    public Response deleteTrackFromPlaylist(@QueryParam("token") String token,
                                            @PathParam("forplaylist") int forPlayList,
                                          @PathParam("id") int trackID) {
        trackService.deleteTrackFromPlaylist(token, forPlayList,trackID);
        return loadTracksPerPlaylist(forPlayList);
    }

    @POST
    @Path("/playlists/{forplaylist}/tracks/")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addTrackToPlaylist(@PathParam("forplaylist") int forPlayList,
                                       TrackModel request){
        trackService.addTrackToPlaylist(forPlayList, request);
        return loadTracksPerPlaylist(forPlayList);
    }

    @GET
    @Path("/tracks")
    @Consumes("application/json")
    public Response loadTracksForAdd(@QueryParam("forPlaylist") int forPlayList){
        var response = trackService.loadTracksForAdd(forPlayList);
        return Response.ok().entity(response).build();
    }
}
