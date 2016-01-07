/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketreceiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lemuel Castro
 */
public class MessageReceiverThread implements Runnable{
    private final Socket socket;
    
    public MessageReceiverThread(Socket socket){
        this.socket = socket;
    }
    
    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message;
            
            OutputStream outputStream = socket.getOutputStream();
            new PrintStream(outputStream).println("HOLA!");
            
            while((message = in.readLine())!=null){
                System.out.println(message);
            }
        } catch (IOException ex) {
            Logger.getLogger(MessageReceiverThread.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
     public void sendMessageToClient(String message){
        OutputStream out;
        try {
            out = socket.getOutputStream();
            PrintStream printStream = new PrintStream(out);
            printStream.println(message);
        } catch (IOException ex) {
            Logger.getLogger(ClientRequestListener.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
}
