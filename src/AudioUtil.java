

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 音效工具类
 *
 *  @author 子墨
 */

@SuppressWarnings("deprecation")
public class AudioUtil{
    /**
     * 坦克诞生音效
     */
    public static final String ADD="AudioSource/add.wav";
    /**
     * 爆炸音效
     */
    public static final String BLAST="AudioSource/blast.wav";
    /**
     * 发射子弹音效
     */
    public static final String FIRE="AudioSource/fire.wav";
    /**
     * 游戏结束音效
     */
    public static final String GAMEOVER="AudioSource/gameover.wav";
    /**
     * 子弹撞击音效
     */
    public static final String HIT="AudioSource/hit.wav";
    /**
     * 游戏开始音效
     */
    public static final String START="AudioSource/start.wav";
    /**
     * 获取到所有背景音效的集合的方法
     */
    public static List<AudioClip> getAudios(){
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