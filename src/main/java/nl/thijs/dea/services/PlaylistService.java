package nl.thijs.dea.services;

import nl.thijs.dea.datasources.dao.PlayListDAO;
import nl.thijs.dea.datasources.dao.TokenDAO;
import nl.thijs.dea.services.dto.PlaylistRequestDto;
import nl.thijs.dea.services.dto.PlaylistResponseDto;

import javax.inject.Inject;

public class PlaylistService {
    private PlayListDAO playlistDAO;
    private TokenDAO tokenDAO;

    @Inject
    public void setDAO(PlayListDAO playlistDAO, TokenDAO tokenDAO) {
        this.playlistDAO = playlistDAO;
        this.tokenDAO = tokenDAO;
    }

    public PlaylistResponseDto loadPlaylists(String token) {
        if (tokenDAO.verifyClientToken(token)) {
            PlaylistResponseDto response = new PlaylistResponseDto();

            response.setPlaylists(playlistDAO.loadPlaylists(token));
            response.setLength(playlistDAO.getTotalPlaylistLength());

            return response;
        }
        return null;
    }

    public void addPlaylist(String token, PlaylistRequestDto request) {
        playlistDAO.addPlaylist(token, request.getName());
    }

    public void deletePlaylist(String token, int id){
        playlistDAO.deletePlaylist(token, id);
    }

    public void editPlaylist(String token, int id,
                                 PlaylistRequestDto request){
        playlistDAO.editPlaylist(token, id, request.getName());
    }
}
