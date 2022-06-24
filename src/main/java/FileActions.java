
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Uzzi
 */
public class FileActions implements Serializable {

    private ArrayList<Object> objects = new ArrayList<Object>();

//setters
    public void addObject(Object o) {
        objects.add(o);
    }

//getters
    public void saveGame() {
        try {  // Catch errors in I/O if necessary.
            // Open a file to write to, named SavedObj.sav.
            FileOutputStream saveFile = new FileOutputStream("NTT.sav");

            // Create an ObjectOutputStream to put objects into save file.
            ObjectOutputStream save = new ObjectOutputStream(saveFile);

            // Now we do the save.
            for (int i = 0; i < objects.size(); i++) {
                save.writeObject(objects.get(i));
            }
            // Close the file.
            save.close(); // This also closes saveFile.
        } catch (Exception exc) {
            exc.printStackTrace(); // If there was an error, print the info.
        }
    }

    public Board loadGame() {
        Board stuff = new Board();
        try {
            // Open file to read from, named SavedObj.sav.
            FileInputStream saveFile = new FileInputStream("NTT.sav");

            // Create an ObjectInputStream to get objects from save file.
            ObjectInputStream save = new ObjectInputStream(saveFile);

            stuff = (Board) save.readObject();

            // Close the file.
            save.close(); // This also closes saveFile.

        } catch (Exception exc) {
        }
        System.out.println(stuff);

        return stuff;
    }
}
