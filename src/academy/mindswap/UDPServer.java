package academy.mindswap;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

public class UDPServer {



    public static void main(String[] args) {
        DatagramSocket socket=null;
        RandomAccessFile quotes = null ;
        try{
            //create a socket
            socket= new DatagramSocket(8080);
            quotes=new RandomAccessFile("resources/quotes.txt","r");


            //create an array of bytes to receive
            while (true){
                byte[] receiveBuffer=new byte[1024];
                //create the datagram packet
                DatagramPacket receivedPacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                //populate the receiveBuffer
                socket.receive(receivedPacket); //blocking method
                //System.out.println(receivedPacket.getPort());
                //convert the byte array to string from received message
                String command=new String(Utils.trim(receiveBuffer)).toLowerCase();

                byte[] messageToSend;

                switch (command){
                    case "hit me":
                        long random=new Random().longs(0, quotes.length()).findFirst().getAsLong();
                        quotes.seek(random);
                        quotes.readLine();//get to the end of the first line
                        messageToSend = quotes.readLine().getBytes();
                        break;
                    default:
                        messageToSend="Command not recognized".getBytes();
                        break;

                }

                DatagramPacket sendPacket = new DatagramPacket(messageToSend, messageToSend.length, receivedPacket.getAddress(), receivedPacket.getPort());
                socket.send(sendPacket); //blocking method

                //test on terminal
                //nc -u localhost 8080
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(socket!=null)
                socket.close();
        }

    }
}
