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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lemuel Castro
 */
public class ClientRequestListener implements Runnable{
    
    private static final int PORT = 4000;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ClientRequestChangesListener clientRequestChangesListener;
    
    private List<MessageReceiverThread> messageReceiverThreads;
    
    public interface ClientRequestChangesListener{
        void hasConnected(String ip);
        void hasMessage(String message);
    }
    
    public ClientRequestListener(ClientRequestChangesListener clientRequestChangesListener){
        this.clientRequestChangesListener = clientRequestChangesListener;
        messageReceiverThreads = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(4000);
            System.out.println("initialized");
        } catch (IOException ex) {
            Logger.getLogger(ClientRequestListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        while(true){
            System.out.println("starting");
            try {
                //Blocks loop until a connection is established
                clientSocket = serverSocket.accept();
                clientRequestChangesListener.hasConnected(clientSocket.getRemoteSocketAddress().toString());
                MessageReceiverThread messageReceiverThread = new MessageReceiverThread(clientSocket);
                messageReceiverThreads.add(messageReceiverThread);
                new Thread().start();
            } catch (IOException ex) {
                Logger.getLogger(ClientRequestListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void sendMessageToClient(String message, int i){
        messageReceiverThreads.get(i).sendMessageToClient(message);
    }
}
