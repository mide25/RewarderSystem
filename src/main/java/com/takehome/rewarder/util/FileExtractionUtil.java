package com.takehome.rewarder.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileExtractionUtil {

    public static final String CSV_SEPARATOR_DELIMITER = ",";

    public static List<String[]> extractCsvAsListArray(InputStream inputStream) throws Exception {
        List<String[]> output = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        try{
            while ((line = bufferedReader.readLine()) != null) {
                String[] lineAsArray = line.split(CSV_SEPARATOR_DELIMITER);
                output.add(lineAsArray);
            }
            if(output == null || output.size() == 0){
                throw new Exception("Empty File!");
            }
            return output;
        }
        catch(IOException ex){
            throw new Exception("An error occurred reading ");
        }
        finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    throw new Exception("Could not close buffered reader: "+e.getMessage());
                }
            }
        }
    }
}
