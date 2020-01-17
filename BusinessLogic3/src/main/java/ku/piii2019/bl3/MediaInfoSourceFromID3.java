/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3;

import com.mpatric.mp3agic.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author not entirely James
 */
public class MediaInfoSourceFromID3 implements MediaInfoSource {

    private static MediaInfoSourceFromID3 instance = null;

    private MediaInfoSourceFromID3() {
    }

    public static MediaInfoSourceFromID3 getInstance() {
        if (instance == null) {
            instance = new MediaInfoSourceFromID3();
        }
        return instance;
    }

    public void addMediaInfo(MediaItem m) throws Exception {
        Mp3File mp3 = new Mp3File(m.getAbsolutePath());
        ID3v1 tag = mp3.getId3v1Tag();
        int length = (int) mp3.getLengthInSeconds();
        if (tag == null) {
            tag = mp3.getId3v2Tag();
        }
        if (tag == null) {
            throw new Exception();
        }
        try {
            m.setTitle(tag.getTitle());
            m.setAlbum(tag.getAlbum());
            m.setArtist(tag.getArtist());
            m.setGenre(tag.getGenreDescription()==null ? "undefined" : tag.getGenreDescription());
            m.setLengthInSeconds(length);
        } catch (Exception ex) {
            Logger.getLogger(MediaInfoSourceFromID3.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }

    }

    public static void updateBasicMetaTags(MediaItem m) {
        try {
            Path filePath = Paths.get(m.getAbsolutePath()).toAbsolutePath().normalize();
            Path directory = filePath.getParent();
            Path fileName = filePath.getFileName();
            
            int prefix = Math.abs(m.hashCode());
            
            Path tempFilePath = Paths.get(directory.toString(), prefix+"_"+fileName.toString());
            Files.copy(filePath, tempFilePath);
            if(!Files.deleteIfExists(filePath)){
                throw new Exception("Deleting the original file was not possible!");
            }
            
            
            Mp3File mp3 = new Mp3File(tempFilePath);
            ID3v1 tag = mp3.getId3v1Tag();

            if (tag == null) {
                tag = mp3.getId3v2Tag();
            }
            if (tag == null) {
                throw new Exception();
            }

            tag.setAlbum(m.getAlbum());
            tag.setArtist(m.getArtist());
            tag.setTitle(m.getTitle());
            if (mp3.hasId3v1Tag()) {
                mp3.removeId3v1Tag();
                mp3.setId3v1Tag(tag);
                System.out.println("v1");

              
            }
            if (mp3.hasId3v2Tag()) {
                mp3.removeId3v2Tag();
                mp3.setId3v2Tag((ID3v2) tag);
                System.out.println("v2");

            }
            
          
            mp3.save(filePath.toString());
            
             if(!Files.deleteIfExists(tempFilePath)){
                throw new Exception("Deleting the temporary file was not possible!");
            }
//         
        } catch (Exception ex) {
            CustomLogging.logIt(ex);
        } 
    }
        public static void updateMetaTag(MediaItem editItem, String editProperty, String newValue){
        try{
        Field editedField = editItem.getClass().getDeclaredField(editProperty);
        editedField.set(editItem, newValue);
        } catch(Exception e){
            CustomLogging.logIt("Trying to edit MediaItem's non existent field.", Level.SEVERE);
        }
        MediaInfoSourceFromID3.updateBasicMetaTags(editItem);
    }

}
