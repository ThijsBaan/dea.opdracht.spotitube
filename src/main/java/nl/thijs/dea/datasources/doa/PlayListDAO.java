package nl.thijs.dea.datasources.doa;

import nl.thijs.dea.datasources.DatabaseConnection;
import nl.thijs.dea.models.PlaylistModel;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayListDAO {
    private Connection connection;
    private int totalPlaylistLength;

    @Inject
    public void setConnection(DatabaseConnection databaseConnection) {
        this.connection = databaseConnection.getConnection();
    }

    public int getTotalPlaylistLength() {
        return totalPlaylistLength;
    }

    public boolean isTheClientTokenValid(String token) {
        try {
            PreparedStatement tokenSt = connection.prepareStatement("SELECT Username, Token FROM Token WHERE " +
                    "Token " +
                    "= ? ");
            tokenSt.setString(1, token);

            ResultSet results = tokenSt.executeQuery();

            return results.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<PlaylistModel> getAllPlaylists(String token) {
        try {

            PreparedStatement playlistSt = connection.prepareStatement("SELECT ID, " +
                    "                    Name, " +
                    "                    CASE WHEN (SELECT [Username] FROM Token WHERE Token = ?) = [Owner]  " +
                    "                    THEN 1 ELSE 0 END AS [Owner], " +
                    "                    (SELECT SUM(Duration) FROM Tracks t where t.ID in (SELECT Track FROM tracksInPlaylist WHERE Playlist = p.ID)) as [Duration]  " +
                    "                    FROM Playlist p " +
                    "                    WHERE [Owner] IN (SELECT [Username] FROM Token WHERE Token = ?)  " +
                    "                    OR [ID] IN (SELECT Playlist FROM followingPlaylist WHERE Follower IN (SELECT [Username] FROM Token  " +
                    "                    WHERE Token = ?))");
            playlistSt.setString(1, token);
            playlistSt.setString(2, token);
            playlistSt.setString(3, token);

            ResultSet results = playlistSt.executeQuery();

            int[] emptyArray = new int[0];

            var playlistList = new ArrayList<PlaylistModel>();

            while (results.next()) {
                playlistList.add(new PlaylistModel((Integer) results.getObject("ID"), results.getString(
                        "Name"),
                        results.getBoolean("Owner"), emptyArray));
                totalPlaylistLength += results.getInt("duration");
            }

            return playlistList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
