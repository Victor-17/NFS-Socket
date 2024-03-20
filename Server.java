import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringTokenizer;

public class Server{
    public static void main(String[] args) throws IOException {
        System.out.println("=== Servidor NFS iniciado === ");

        ServerSocket serverSocket = new ServerSocket(6000);

        while (true) {
            Socket socket = serverSocket.accept();  
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            // lendo a mensagem do cliente
            String mensagem = in.readUTF();
            System.out.println("Cliente: " + socket.getInetAddress() + ":" + socket.getPort());
            System.out.println("Enviou a mensagem: " + mensagem);
            String resposta = new String();
            String respostaCreate = new String();
            if(mensagem.startsWith("readdir")){
                StringTokenizer strToken = new StringTokenizer(mensagem, " ");
                strToken.nextToken();
                File pasta = new File(strToken.nextToken());
                File[] arquivos = pasta.listFiles();
                for(File arquivo: arquivos){
                    resposta += " " + arquivo.getName();
                }
                out.writeUTF("Arquivos: " + resposta);
            }else if (mensagem.startsWith("create")) {
                String fileName = "Teste.txt";
                StringTokenizer stringTokenizer = new StringTokenizer(mensagem, " ");
                stringTokenizer.nextToken();
                File pasta = new File(stringTokenizer.nextToken());
                Path p = Paths.get(pasta + "/" + fileName);
                Path novo = Files.createFile(p);
                out.writeUTF("Arquivo Criado: " + novo);
            }else if(mensagem.startsWith("rename")){
                StringTokenizer stringTokenizer = new StringTokenizer(mensagem, " ");
                stringTokenizer.nextToken();
                File pasta = new File(stringTokenizer.nextToken());
                // Caminho do arquivo original
                Path arquivoOriginal = Paths.get(pasta + "");
                String recorte = arquivoOriginal.toString();
                //Novo nome do arquivo
                Path novoNome = Paths.get(recorte.replace("Teste.txt", "TesteRenomeado.txt") + "");
                Path alteracao = Files.move(arquivoOriginal, novoNome);
                out.writeUTF("Arquivo Renomeado: " + alteracao);
            }else if(mensagem.startsWith("remove")){
                StringTokenizer stringTokenizer = new StringTokenizer(mensagem, " ");
                stringTokenizer.nextToken();
                File pasta = new File(stringTokenizer.nextToken());
                Path p = Paths.get(pasta + "");
                Files.delete(p);
                out.writeUTF("Arquivo: " + p + " foi removido!");
            }
        }
    }
}
