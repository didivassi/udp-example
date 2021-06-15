package academy.mindswap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        UDPClient client = new UDPClient("localhost",8080);
        System.out.println(client.receiveQuote("hit me"));
        System.out.println(client.receiveQuote("not a command"));

        while (true){
            try {
                String command = new BufferedReader(new InputStreamReader(System.in)).readLine();

                if(command.equalsIgnoreCase("exit")){
                    break;
                }
                System.out.println(client.receiveQuote(command));
            }catch (IOException e){
                System.out.println(e);
            }
        }




    }
}
