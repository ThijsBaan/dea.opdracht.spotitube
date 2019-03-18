package nl.thijs.dea.datasources.doa;

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

    @Inject
    public void setConnection(DatabaseConnection databaseConnection) {
        this.connection = databaseConnection.getConnection();
    }

    public boolean checkIfPlaylistHasTracks(int forPlayList) {
        try {
            PreparedStatement tracksAvailable = connection.prepareStatement("SELECT t.ID " +
                    "FROM Tracks t WHERE t.ID IN (SELECT Track FROM tracksInPlaylist tp WHERE tp.Playlist = ?)");
            tracksAvailable.setInt(1, forPlayList);

            return tracksAvailable.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<TrackModel> getAllTracksPerPlaylist(int forPlayList) {
        try {PreparedStatement track = connection.prepareStatement("SELECT t.ID, Title, Performer, Duration, Album, " +
                "Playcount, PublicationDate, Description, OfflineAvailable " +
                "FROM Tracks t WHERE t.ID IN (SELECT Track FROM tracksInPlaylist tp WHERE tp.Playlist = ?)");
            track.setInt(1, forPlayList);

            ResultSet r = track.executeQuery();
            var trackList = new ArrayList<TrackModel>();

            while(r.next()){
                trackList.add(new TrackModel(r.getInt("ID"), r.getString("Title"), r.getString("Performer"),
                        r.getInt("Duration"), r.getString("Album"), r.getInt("Playcount"), r.getString(
                                "PublicationDate"), r.getString("Description"),  r.getBoolean("OfflineAvailable")));
            }

            return trackList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
