/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3;

import com.mpatric.mp3agic.Mp3File;
import java.nio.file.Path;
import java.nio.file.Paths;
import ku.piii2019.bl3.MediaItem;
import ku.piii2019.bl3.CustomLogging;

/**
 *
 * @author regor
 */
public class M3U {
    public static final String SEPARATOR = "\r\n";
    public static String getHeader() {
        return "#EXTM3U"+SEPARATOR;
    }
    public static String getArtistHeader(String artist) {
        return "#EXTART:"+artist+SEPARATOR;
    }
    
    public static String getGenreHeader(String genre) {
        return "#EXGENRE:"+genre+SEPARATOR;
    }

    public static String getMediaItemInf(MediaItem extractFromThis, boolean relativePath) {
        StringBuilder s = new StringBuilder();
        try {
            Mp3File mp3 = new Mp3File(extractFromThis.getAbsolutePath());
            int length = extractFromThis.getLengthInSeconds();
            
            if(extractFromThis.getLengthInSeconds()==null){
                    length = (int) mp3.getLengthInSeconds();
                    extractFromThis.setLengthInSeconds(length);
            }
        
            String artist = extractFromThis.getArtist();
            String title = extractFromThis.getTitle();
            String album = extractFromThis.getAlbum();
            s.append("#EXTINF:"+length+", "+artist+" - "+title+"(from the album "+album+")"+SEPARATOR );
            if(relativePath){
                String fileName = extractFromThis.getFilename();
                s.append(fileName+SEPARATOR);
            } else {
               s.append(extractFromThis.getAbsolutePath()+SEPARATOR);

            }
           

        } catch (Exception ex) {
            CustomLogging.logIt(ex);
        }

        return s.toString();
    }
    
 
    

}
