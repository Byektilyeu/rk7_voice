
public class Voice {

    public void voiceP(){
        voiceRead voiceread = new voiceRead();
        String voice = voiceread.getGreaterThanZero();
        System.out.println("voice---------------------------------------------------------------------------: " + voice);
        playVoice music = new playVoice("C:\\UCS\\QMSVoice\\rk7_voice\\voices\\" + voice + ".wav");
    }
}
