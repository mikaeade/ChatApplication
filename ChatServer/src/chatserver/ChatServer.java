/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import message.ChatMessage;

/**
 *
 * @author Ohjelmistokehitys
 */
public class ChatServer {

    /**
     * @param args the command line arguments
     */
    static ArrayList<ServerClientBackEnd> clients = new ArrayList();
    static ArrayList<String> nameList = new ArrayList();

    public static void main(String[] args) {
        try {
            // Start the server to listen port 3010
            ServerSocket server = new ServerSocket(3010);

            // Start to listen and wait connections
            while (true) {
                //wait here the client to take the contact to us
                //when connected we create a socket to the client in the server side.
                //now it can communicate back to the specific socket
                Socket temp = server.accept();
                ServerClientBackEnd backend = new ServerClientBackEnd(temp);
                add(backend);
                Thread t = new Thread(backend);
                t.setDaemon(true); // background thread, ja VJM huolehdi sen tuhoamisesta
                t.start(); // call the run()
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void broadcastMessage(ChatMessage cm) {

        for (ServerClientBackEnd temp : clients) {
            temp.SendMessage(cm);
        }
    }

    public static void AddUserNameToList(ChatMessage cm) {

        System.out.println("Save userName to a ArrayList userNameList");

        boolean Contain = false;
        for (String temp : nameList) {
            if (temp.contains(cm.getUserName())) {
                System.out.println("listalla jo nimi");
                Contain = true;
            }
        }

        if (!(Contain)) {
            System.out.println("Save userName to a ArrayList userNameList");
            nameList.add(cm.getUserName());
            for (ServerClientBackEnd temp : clients) {
                temp.updateClientList(cm.getUserName());
            }
        }

    }

    public static void RemoveUserNameFromList(ChatMessage cm) {

        System.out.println("Remove userName From ArrayList userNameList");
        nameList.remove(cm.getUserName());
        for (ServerClientBackEnd temp : clients) {
                temp.updateClientList(cm.getUserName());
            }
    }

    public static void add(ServerClientBackEnd client) {

        System.out.println("Add Client socket to the server ArrayList");
        clients.add(client);
    }

    public static void remove(ServerClientBackEnd client) {
        System.out.println("remove Client socket from the server ArrayList");
        clients.remove(client);
    }

}
