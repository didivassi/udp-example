package academy.mindswap;

import academy.mindswap.udp.Commands;
import academy.mindswap.udp.UDPClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        UDPClient client = new UDPClient("localhost",8080);
        System.out.println(client.receiveQuote(Commands.HIT_ME_COMMAND));
        System.out.println(client.receiveQuote("QQ coisa"));

        while (true){
            try {
                String command = new BufferedReader(new InputStreamReader(System.in)).readLine();

                if(command.equalsIgnoreCase(Commands.EXIT_COMMAND)){
                    break;
                }
                System.out.println(client.receiveQuote(command));
            }catch (IOException e){
                System.out.println(e);
            }
        }




    }
}
