package nl.thijs.dea.datasources.dao;

import nl.thijs.dea.datasources.DatabaseConnection;
import nl.thijs.dea.models.TrackModel;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrackDAO {
    private Connection connection;
    private List<TrackModel> trackList = new ArrayList<>();

    @Inject
    public void setConnection(DatabaseConnection databaseConnection) {
        this.connection = databaseConnection.getConnection();
    }

    public List<TrackModel> getAllTracksWhoArentInPlaylist(int forPlayList) {
        addAllTracksToArrayList();
        List<TrackModel> tmp = new ArrayList<>(trackList);

        return getCorrectTracksPerRequest(forPlayList, tmp, "remove");
    }

    public List<TrackModel> getAllTracksWhoAreInPlaylist(int forPlayList) {
        addAllTracksToArrayList();
        List<TrackModel> tmp = new ArrayList<>();

        return getCorrectTracksPerRequest(forPlayList, tmp, "add");
    }

    private List<TrackModel> getCorrectTracksPerRequest(int forPlayList, List<TrackModel> tempList, String action) {
        try {
            PreparedStatement track = connection.prepareStatement("SELECT t.ID, OfflineAvailable " +
                    "FROM Tracks t LEFT OUTER JOIN tracksInPlaylist tp " +
                    "on t.ID = tp.Track " +
                    "WHERE tp.Playlist = ?");
            track.setInt(1, forPlayList);

            ResultSet r = track.executeQuery();

            while (r.next()) {
                for (TrackModel t : trackList) {
                    if ((r.getInt("ID") == t.getId())) {
                        if ("add".equals(action)) {
                            t.setOfflineAvailable(r.getBoolean("OfflineAvailable"));
                            tempList.add(t);
                        } else if ("remove".equals(action)) {
                            tempList.remove(t);
                        }
                    }
                }
            }
            return tempList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void addAllTracksToArrayList() {
        if (trackList.isEmpty()) {
            try {
                PreparedStatement track = connection.prepareStatement("SELECT distinct t.ID, Title, Performer, " +
                        "Duration, Album, Playcount, PublicationDate, Description " +
                        "FROM Tracks t");

                ResultSet r = track.executeQuery();

                while (r.next()) {
                    trackList.add(new TrackModel(r.getInt("ID"), r.getString("Title"), r.getString("Performer"),
                            r.getInt("Duration"), r.getString("Album"), r.getInt("Playcount"), r.getString(
                            "PublicationDate"), r.getString("Description"), false));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteTrackFromPlaylist(String token, int forPlayList, int trackID) {
        try {
            PreparedStatement deleteTrack = connection.prepareStatement("DELETE FROM tracksInPlaylist " +
                    "WHERE Playlist IN (SELECT p.[ID] FROM playlist p " +
                    "                   WHERE p.Owner IN (SELECT [Username] FROM Token WHERE Token = ?)) " +
                    "AND Playlist = ? AND Track = ?");
            deleteTrack.setString(1, token);
            deleteTrack.setInt(2, forPlayList);
            deleteTrack.setInt(3, trackID);

            deleteTrack.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addTrackToPlaylist(int playlistID, int trackID, boolean offlineAvailable) {
        try {
            PreparedStatement addTrack = connection.prepareStatement("INSERT INTO tracksInPlaylist " +
                    "VALUES(?, ?, ?)");
            addTrack.setInt(1, playlistID);
            addTrack.setInt(2, trackID);
            addTrack.setBoolean(3, offlineAvailable);

            addTrack.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
