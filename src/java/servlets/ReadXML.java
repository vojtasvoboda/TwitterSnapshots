package servlets;

import java.beans.*;
import java.io.*;

/**
 * ReadXML deserializes an instance of from XML.
 */
public class ReadXML {
   
    public Object toObject(String xml) {

      try {
         // Create file input stream.
         FileInputStream fstream = new FileInputStream(xml);

         try {
            // Create XML decoder.
            XMLDecoder istream = new XMLDecoder(fstream);

            try {
               // Read object.
               Object obj = istream.readObject();
               return obj;
            } finally {
               // Close object stream.
               istream.close();
            }
         } finally {
            // Close file stream.
            fstream.close();
         }
      } catch(Exception ex) {
         System.out.println(ex);
      }
      return false;
   }
}
