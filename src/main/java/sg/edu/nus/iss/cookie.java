package sg.edu.nus.iss;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class cookie {
    
    List<String> cookies = null;
    
    public void readCookieFile(String fileName) throws IOException
    {
        //instantiate the cookies collection object
        cookies = new ArrayList<>();
        //use File object to pass the filename
        File newFile = new File(fileName);
        //Use FileReader and BufferReader for reading from cookie file
        //Instantiate a FileReader follow by a BufferedReader
        FileReader readFile = new FileReader(newFile);
        BufferedReader br = new BufferedReader(readFile);
        String newline = "";
        //While loop to loop through the file
        //Read each line and add into the cookies collection object
        while((newline=br.readLine())!=null)
        {
            System.out.println(newline);
            cookies.add(newline);
        }
        //close the BufferedReader and FileReader in reverse order
        br.close();
        readFile.close();
    }
    public String getRandomCookie()
    {
        //Use Maths library for random function
        //check if cookies are loaded
        Random random = new Random();

        //If cookies loaded, generate and return random cookie
        if(cookies == null)
        {
            return "There is no cookies";
        } else
        { 
            String randomCookie = cookies.get(random.nextInt(cookies.size()));
            return randomCookie;
        }
    
    }

}
