package sg.edu.nus.iss;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws NumberFormatException, IOException
    {
        //2 arguments, 1 for file name and 1 for the port that the server will start on
        String fileName = args[0];
        String port = args[1];
        //Initalise fileName
        File cookieFile = new File(fileName);
        //Check if cookie file exists
        if(!cookieFile.exists())
        {
            System.out.println("Cookie file not found!");
            System.exit(0);
        }
        //initailise cookie object
        cookie cookie = new cookie();
        //call readCookieFile method that will read file
        cookie.readCookieFile(fileName);
        //Set myCookie to getRaandomCookie method's output and print it
        String myCookie = cookie.getRandomCookie();
        System.out.println(myCookie);
        String myCookie2 = cookie.getRandomCookie();
        System.out.println(myCookie2);
        //day 4 slide 8
        //create socket server object
        ServerSocket server = new ServerSocket(Integer.parseInt(port));
        //creating socket and waiting for client connection
        Socket socket = server.accept();
        String msgReceived = "";
        //day 4 slide 9 - allow server to read and write over the communication channel
        try(InputStream is = socket.getInputStream())
        {
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);
            //store the data set over from client, e.g. get-cookie
            //String line = dis.readUTF();
            try(OutputStream os = socket.getOutputStream())
            {
                BufferedOutputStream bos = new BufferedOutputStream(os);
                DataOutputStream dos = new DataOutputStream(bos);
                //write logic to receive and send
                while(!msgReceived.equals("close"))
                {
                    //recieve message day 4 slide 9
                    msgReceived = dis.readUTF();
                    if(msgReceived.equals("get-cookie"))
                    {
                        //instantiate cookie.java and get a random cookie
                        //Send the random cookie out using DataOutputStream (dos.writeUTF(XXXXX))
                        String randomCookie = cookie.getRandomCookie();
                        dos.writeUTF(randomCookie);
                        dos.flush();
                    } else
                    {  
                        //If client enters anything else, go back to getting input from client
                        dos.writeUTF("");
                        dos.flush();
                    }
                } 
                bos.flush();
                dos.close();
                bos.close();
                os.close();
            } catch (EOFException ex)
            {
                ex.printStackTrace();
            } finally
            {
            dis.close();
            bis.close();
            is.close();
            }
        } catch(EOFException ex)
        {
            socket.close();
            server.close();
        }
    }
}
