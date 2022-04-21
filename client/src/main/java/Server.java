import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.net.Socket;
import java.net.ServerSocket;

public class Server {

    private void serverStart(){
        //Socket으로 읽어서 file로 쓴다.
        File saveDir = new File("C:/Users/dlgks/OneDrive/바탕 화면/졸작/receivefolder/");

        if(!saveDir.exists()){ //저장할 폴더가 없으면 만들어준다.
            saveDir.mkdirs();

        }

        try {
            for(int i=0;i<10;i++) {
                ServerSocket server = new ServerSocket(9999);
                System.out.println("서버가 준비중입니다...");

                Socket socket = server.accept(); // 클라이언트의 요청을 기다린다.

                System.out.println("파일 저장 시작...");

                //소켓용 입력 스트림 객체 생성
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                BufferedInputStream bis = new BufferedInputStream(dis);

                //클라이언트가 접속되었을 때 첫번째로 보내온 파일이름을 받는다.
                String fileName = dis.readUTF();

                File saveFile = new File(saveDir, i+".png");

                //파일 출력용 스트림 객체 생성
                BufferedOutputStream bos = new BufferedOutputStream(
                        new FileOutputStream(saveFile)
                );


                byte[] temp = new byte[1024];
                int length = 0;

                while ((length = bis.read(temp)) > 0) {
                    bos.write(temp, 0, length);

                }
                bos.flush();

                System.out.println("파일 저장 완료...");


                //스트림과 소켓 닫기
                bos.close();
                bis.close();
                socket.close();
                server.close();
            }


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("파일 저장 실패!!" + e.getMessage());
        }

    }

    public static void main(String[] args) {
        new Server().serverStart();


    }

}