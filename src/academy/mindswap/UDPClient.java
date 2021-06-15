package academy.mindswap;

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
            socket.send(sendPacket); //blocking method
            byte[] receiveBuffer=new byte[1024];
            DatagramPacket receivedPacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            //populate the receiveBuffer
            socket.receive(receivedPacket); //blocking method
            returnMessage=new String(Utils.trim(receiveBuffer));

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(socket!=null)
                socket.close();
        }

        return returnMessage;
    }


}
