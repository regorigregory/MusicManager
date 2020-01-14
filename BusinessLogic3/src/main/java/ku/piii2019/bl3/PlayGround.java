/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3;
import java.util.Set;
import org.apache.commons.text.similarity.LevenshteinDistance;

/**
 *
 * @author Regory Gregory
 */
public class PlayGround {
    public static void main(String... args){
        String folder = "C:\\gdrive\\NetBeansProjects\\music-manager-assignment\\test_folders\\original_filenames\\collection-B\\After Many Days";
        Set<MediaItem> test = MediaFileService.getInstance().getAllID3MediaItems(folder, null);
        MediaItem m = test.iterator().next();
        m.setArtist("pussy");
        MediaInfoSourceFromID3.updateBasicMetaTags(m);
    }
}
