/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclientapp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.ChatMessage;

/**
 *
 * @author Ohjelmistokehitys runnable voi laittaa koodia ajoon johonkin
 * threadiin
 */
public class ClientBackEnd implements Runnable {

    private Socket clientSocket;
    private ObjectOutputStream output; //kirjoita objekti tiedonvälitykseen
    private ObjectInputStream input;   // lue Objektiserviltä saatu

    ClientBackEnd() {
        try {
            // 3010 portti mitä kuunnellaan
            // localhost = "127.0.0.1" loopback osoite
            // loopback käy koneen fyysisessä layerissa ja tulee takas
            //

            clientSocket = new Socket("localhost", 3010);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // kun käynnistetään säie/thread, tämän luokan konstruktori ajetaan ui 
    // kontekstissa. run funktiossa asiat mitä halutaan ajaa run() metodissa thredissa.
    // kutsutaan vain kerran.
    @Override
    public void run() {
        try {
            // kirjotus ja luku
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            input = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // read and write from socket until user closes the app
        while (true) {

            try {
                ChatMessage m = (ChatMessage) input.readObject();
                // testausta varten printtaa viesti
                System.out.println(m.getChatMessage());
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void sendMessage(ChatMessage cm) {

        try {
            output.writeObject(cm);
            output.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
