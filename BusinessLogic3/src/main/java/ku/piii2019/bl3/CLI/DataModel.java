/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3.CLI;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import ku.piii2019.bl3.MediaItem;

/**
 *
 * @author regor
 */
public class DataModel {

    private static List<MediaItem> collectionA;
    private static List<MediaItem> collectionB;
    private static List<MediaItem> collectionC;
    private static List<List<MediaItem>> collections;
    private static DataModel instance=null;
    
    private DataModel() {

        collectionA = null;
        collectionB = null;
        collectionC = null;
        collections = new LinkedList<>();

        collections.add(collectionA);
        collections.add(collectionB);
        collections.add(collectionC);
    }
    public static DataModel getInstance(){
        if(instance==null){
            instance = new DataModel();
        }
        return instance;
    
    }

    public static void setCollection(List<MediaItem> targetCollection, LinkedList<MediaItem> newCollection) {
        targetCollection = newCollection;
    }
    
    public static void setCollection(int targetIndex, LinkedList<MediaItem> newCollection) {
        targetIndex =  processIndex(targetIndex);

        collections.set(targetIndex, newCollection);
    }

    public static void clearCollection(List<MediaItem> targetCollection) {
        targetCollection.clear();

    }

    public static void clearCollection(int index) {
        index = processIndex(index);
        collections.get(index).clear();
        
    }
    
      public static void clearAllCollections(List<MediaItem> targetCollection) {
        collections = new LinkedList<>();

    }

    public static List<MediaItem> getCollection(int index) {
        index = processIndex(index);
        return collections.get(index);

    }

    private static int processIndex(int index) {
        if (index < 1) {
            
            index = 1;
        } else if (index > 3) {
            index=3;
        }
        
        return index-1;

    }

}


