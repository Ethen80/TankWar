package Audio;

import java.io.*;
import javax.sound.sampled.*;

public class AudioPlayer {
    private String fileName = null;
    private AudioInputStream cin = null;
    private AudioFormat format = null;
    private DataLine.Info info = null;
    private SourceDataLine line = null;

    private boolean stop = false;

    public AudioPlayer(String fileName){
        this.fileName = fileName;
    }

    @SuppressWarnings("deprecation")
    public void player(){
        try {
            cin = AudioSystem.getAudioInputStream(new File(fileName).toURL());
            format = cin.getFormat();
            info = new DataLine.Info(SourceDataLine.class, format);
            line = (SourceDataLine)AudioSystem.getLine(info);

            line.open(format);
            line.start();

            int len = 0;
            byte[] buffer = new byte[512];

            while(!stop){
                len = cin.read(buffer, 0 , buffer.length);
                if(len <= 0){
                    break;
                }
                line.write(buffer, 0 , len);
            }
            line.drain();
            line.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        if(line != null){
            line.stop();
            stop = true;
        }
    }
    /**
     * 声音播放用线程
     */
    public class AudioThread extends Thread{
        @Override
        public void run() {
            // TODO Auto-generated method stub
            player();
            //stop();
            super.run();
        }
    }
}
