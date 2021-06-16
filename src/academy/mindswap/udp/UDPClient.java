package academy.mindswap.udp;

import academy.mindswap.utils.Utils;

import java.io.IOException;
import java.net.*;

public class UDPClient {

    DatagramSocket socket=null;
    String serverName;
    int port;
    public UDPClient(String serverName, int port){
        this.serverName=serverName;
        this.port=port ;
    }

    public String receiveQuote(String command){
        String returnMessage=null;
        try {
            socket= new DatagramSocket();
            InetAddress address = InetAddress.getByName(serverName);
            //create an array of bytes to send
            byte[] sendBuffer=command.getBytes();
            //create the datagram packet
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, port);
            //populate the receiveBuffer
            socket.send(sendPacket);

            byte[] receiveBuffer=new byte[1024];

            socket.setSoTimeout(10000);
            while(true) {
                DatagramPacket receivedPacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                try {
                    socket.receive(receivedPacket); //blocking method
                    returnMessage=new String(Utils.trim(receiveBuffer));
                    break;
                } catch (SocketTimeoutException e) {
                    // resend
                    socket.send(sendPacket);
                }
            }



        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(socket!=null)
                socket.close();
        }

        return returnMessage;
    }


}
