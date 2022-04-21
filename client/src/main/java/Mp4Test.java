import java.io.File;

public class Mp4Test {

    public void getThumbnail(File source) throws Exception{
        double plusSize = 0.5;
        int threadSize = 4;

        VideoThread[] videoThreads = new VideoThread[threadSize];

        for (int i = 0; i < videoThreads.length; i++) {
            videoThreads[i] = new VideoThread(source, threadSize, i, plusSize);
            videoThreads[i].start();
        }

        boolean runFlag = true;
        while(runFlag) {
            Thread.sleep(100);
            runFlag = false;
            for (int i = 0; i < threadSize; i++) {
                if (videoThreads[i].isAlive())
                    runFlag = true;
            }
        }
    }
}
