import org.apache.commons.lang3.ArrayUtils;
import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Arrays;

public class ClientV1 {
    public static void main(String[] args) throws Exception {
/**
        int count = 10;
        Mp4Test mp4Test = new Mp4Test();
        File source = new File("C:/inputvideo/input1.mp4");

        long startTime = System.currentTimeMillis();
        mp4Test.getThumbnail(source);
        long endTime = System.currentTimeMillis();
        System.out.println((endTime-startTime)+"(ms) and start transport");
**/

        File[] files = new File("C:/Users/hanjun/OneDrive/바탕 화면/졸작/samplevideo").listFiles();
        String size="";
        byte[][] filesContent = new byte[10][];
        byte[] concate = new byte[0];
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            filesContent[i] = Files.readAllBytes(file.toPath());
            size = size + "," + filesContent[i].length;
            System.out.println(Arrays.toString(filesContent[i]));
            String fileName = file.getName();
        }
        for (int i=0;i< filesContent.length;i++){
            // concate는 모든 파일들 바이트변환후 합친것
            concate = ArrayUtils.addAll(concate,filesContent[i]);
        }
        size = size.substring(1);


        System.out.println(concate.length);
        System.out.println(size);
            try {
                Socket socket = new Socket("localhost", 9999);

                //소켓으로 각 파일들의 length길이 정보 들어있는 문자열 내보내기 위한 stream
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                //바이트로 변환후 파일 내용 보내기위한 stream
                BufferedOutputStream bos = new BufferedOutputStream(dos);

                dos.writeUTF(size);


                // concate는 각각의 사진의 바이트를 합친 배열이다.
                bos.write(concate, 0, concate.length);
                bos.flush(); //현재 버퍼에 저장되어 있는 내용 클라이언트로 전송하고 버퍼 비움
                System.out.println("파일 전송완료");

                //스트림과 소켓 닫기
                bos.close();
                socket.close();
            } catch (Exception e) {
                System.out.println("파일 전송 실패 :"+e.getMessage());
            }

    }
}
