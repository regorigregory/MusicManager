/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author James
 */
public class SearchTestHelper {

    static Set<MediaItem> getStandardSet() {
        Set<MediaItem> output = new HashSet<>();

        output.add(billEvans1());
        output.add(billEvans2());
        output.add(oscarPeterson1());
        output.add(budPowell1());

        return output;
    }
    
        static Set<MediaItem> getIncompleteMediaInfoSet() {
        Set<MediaItem> output = new HashSet<>();

        output.add(budPowell1());
        output.add(budPowellNoTitle());
        output.add(budPowellNoArtist());
        output.add(budPowellNoAlbum());
        return output;
    }

    static String getPath(int i) {
        return "C:" + File.separator
                + "someFolder" + File.separator
                + "file" + i + ".mp3";
    }

    static MediaItem billEvans1() {
        return new MediaItem().
                setAbsolutePath(getPath(1234)).
                setAlbum("Everybody Digs Bill Evans").
                setArtist("Bill Evans").
                setTitle("Peace Piece");
    }

    static MediaItem billEvans2() {
        return new MediaItem().
                setAbsolutePath(getPath(2435)).
                setAlbum("A Portrait of Bill Evans").
                setArtist("Bill Evans").
                setTitle("Autumn Leaves");
    }

    static MediaItem oscarPeterson1() {
        return new MediaItem().
                setAbsolutePath(getPath(3456)).
                setAlbum("We Get Requests").
                setArtist("Oscar Peterson").
                setTitle("The Girl From Ipanema");

    }

    static MediaItem budPowell1() {

        return new MediaItem().
                setAbsolutePath(getPath(4567)).
                setAlbum("The Amazing Bud Powell").
                setArtist("Bud Powell").
                setTitle("A Night in Tunisia");
    }

    static MediaItem budPowellNoTitle() {

        return new MediaItem().
                setAbsolutePath(getPath(4567)).
                setAlbum("The Amazing Bud Powell").
                setArtist("Bud Powell").
                setTitle(null);
    }

    static MediaItem budPowellNoArtist() {

        return new MediaItem().
                setAbsolutePath(getPath(4567)).
                setAlbum("The Amazing Bud Powell").
                setArtist(null).
                setTitle("A Night in Tunisia");
    }

    static MediaItem budPowellNoAlbum() {

        return new MediaItem().
                setAbsolutePath(getPath(4567)).
                setAlbum(null).
                setArtist("Bud Powell").
                setTitle("A Night in Tunisia");
    }

}
