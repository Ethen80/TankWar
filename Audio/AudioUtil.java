package Audio;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;


    @SuppressWarnings("deprecation")
    public class AudioUtil {
        /***
         *重生音效
         */
        public static final String ADD = "F:\\Desktop\\EcliWorkSpace\\TankWar2020\\AudioSource\\add.wav";
        /***
         * 爆炸音效
         */
        public static final String BLAST = "F:\\Desktop\\EcliWorkSpace\\TankWar2020\\AudioSource\\blast.wav";
        /***
         * 发射音效
         */
        public static final String FIRE = "F:\\Desktop\\EcliWorkSpace\\TankWar2020\\AudioSource\\fire.wav";
        /***
         * 游戏输音效
         */
        public static final String GAMEOVER = "F:\\Desktop\\EcliWorkSpace\\TankWar2020\\AudioSource\\gameover.wav";
        /***
         * 子弹撞击
         */
        public static final String HIT = "F:\\Desktop\\EcliWorkSpace\\TankWar2020\\AudioSource\\hit.wav";
        /***
         * 游戏赢音效
         */
        public static final String START = "F:\\Desktop\\EcliWorkSpace\\TankWar2020\\AudioSource\\start.wav";


        public static List<AudioClip> getAudios() {
            List<AudioClip> audios = new ArrayList<>();
            try {
                audios.add(Applet.newAudioClip(new File(AudioUtil.START).toURL()));
                audios.add(Applet.newAudioClip(new File(AudioUtil.ADD).toURL()));
                audios.add(Applet.newAudioClip(new File(AudioUtil.BLAST).toURL()));
                audios.add(Applet.newAudioClip(new File(AudioUtil.FIRE).toURL()));
                audios.add(Applet.newAudioClip(new File(AudioUtil.HIT).toURL()));
                audios.add(Applet.newAudioClip(new File(AudioUtil.GAMEOVER).toURL()));
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //audios.add(Applet.newAudioClip(AudioUtil.class.getResource(AudioUtil.BGM)));
            return audios;
        }
    }

