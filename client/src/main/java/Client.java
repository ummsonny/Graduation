import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

public class Client {
    public static void main(String[] args) throws Exception {
//        int count = 10;
//        Mp4Test mp4Test = new Mp4Test();
//        File source = new File("C:/inputvideo/input1.mp4");
//
//        long startTime = System.currentTimeMillis();
//        mp4Test.getThumbnail(source);
//        long endTime = System.currentTimeMillis();
//        System.out.println((endTime-startTime)+"(ms) and start transport");


        File[] files = new File("C:/Users/dlgks/OneDrive/바탕 화면/졸작/samplevideo").listFiles();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            byte[] fileContent = Files.readAllBytes(file.toPath());
            System.out.println(fileContent.length);
            String fileName = file.getName();
            System.out.println(fileName);
            try {
                Socket socket = new Socket("localhost", 9999);

                //소켓으로 파일이름 내보내기 위한 stream
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                //바이트로 변환후 파일 내용 보내기위한 stream
                BufferedOutputStream bos = new BufferedOutputStream(dos);

                dos.writeUTF(Integer.toString(fileContent.length));

                BufferedInputStream bis = new BufferedInputStream(
                        new FileInputStream(file)
                ); // 파일 내용 읽어서 bis에 바이트단위로 저장



                byte[] temp = new byte[fileContent.length];
                int length = 0;

                //파일 내용 읽어서 소켓 전송
                //read메서드는 파일의 끝인 -1을 반환할때까지 내용 읽음
                //temp크기만큼의 파일내용 읽어서 temp에 저장하고 읽은 바이트 수 반환
                while ((length = bis.read(temp)) > 0) {
                    bos.write(temp, 0, length);
                }
                bos.flush(); //현재 버퍼에 저장되어 있는 내용 클라이언트로 전송하고 버퍼 비움
                System.out.println("파일 전송완료" + i);

                //스트림과 소켓 닫기
                bis.close();
                bos.close();
                socket.close();
                Thread.sleep(500);
            } catch (Exception e) {
                System.out.println("파일 전송 실패 :"+e.getMessage());
            }

        }

    }
}
