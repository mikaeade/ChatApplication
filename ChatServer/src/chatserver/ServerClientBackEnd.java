/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Date;
import message.ChatMessage;

/**
 *
 * @author Ohjelmistokehitys
 */
public class ServerClientBackEnd implements Runnable {

    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    
    ServerClientBackEnd(Socket sock) {
        //konstruktori saa socketin, jonka se chatserver loi
        socket = sock;
    }

    @Override
    public void run() {
        ChatMessage cm = null;
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            //Start to wait data
            while (true) {
                //odottaa tässä uutta dataa
                cm = (ChatMessage) input.readObject();
                ChatServer.broadcastMessage(cm);
                
                
                //tallenna UserName chatviestistä
                // jos on uusi käyttäjä
                //System.out.println("AdduUserNameToList");
                System.out.println("Serveri vastaanottanut  chatmessagen");
                ChatServer.AddUserNameToList(cm);
            }
        }
        catch (IOException e){
            System.out.println("Socket exception");
            
            try {
                 System.out.println("Socket exception2");
                socket.close();
                output.close();
                input.close();
                // poista listalta
                System.out.println("Client Socket not responding");
                // sulje socket ja putket 
                // poista listalta
            } catch (IOException e1x) {
                
                System.out.println("Client IOException... not responding");
                e1x.printStackTrace();
            }
         // Username listalta
        ChatServer.RemoveUserNameFromList(cm);    
        ChatServer.remove(this);
        }         
        catch (ClassNotFoundException ex) {
            System.out.println("Client IOException tai classnotFound..");
            ex.printStackTrace();
        }
    } 
    /*
    Kirjoita UIlistaan osallistujat
    */
    public void updateClientList(String userName)
    {
        
        System.out.println("Nimi ennen lähetystä: " + userName);
        
        try {
            System.out.println(userName.getClass());
            output.writeObject(userName);
            output.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }    
    
    public void SendMessage(ChatMessage data){
        try {
            output.writeObject(data);
            output.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void pause(int seconds){
    Date start = new Date();
    Date end = new Date();
    while(end.getTime() - start.getTime() < seconds * 1000){
        end = new Date();
    }
}
}
