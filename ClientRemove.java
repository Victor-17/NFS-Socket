import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientRemove {
     public static void main(String[] args) throws IOException {
        System.out.println("=== Cliente NFS Remove ===");
        Socket socket = new Socket("localhost", 6000);

        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        
        System.out.println("Para remover um arquivo digite: remove + path do arquivo");
        // laço para o cliente
        while (true) {
            // escrevendo mensagem para o servidor
            Scanner teclado = new Scanner(System.in);
            String texto = teclado.nextLine();
            out.writeUTF(texto); 

            // lendo a mensagem do servidor
            String mensagem = in.readUTF();
            System.out.println(mensagem);
        }
    }
}
