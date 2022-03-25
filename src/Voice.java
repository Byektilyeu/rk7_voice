
public class Voice {

    public String voiceP() {
        voiceRead voiceread = new voiceRead();
        String voice = voiceread.getGreaterThanZero();
        System.out.println("voice---------------------------------------------------------------------------: " + voice);
        playVoice music = new playVoice("C:\\UCS\\Voice\\voices\\" + voice + ".wav");
        voiceRead qmsnumber = new voiceRead();
        qmsnumber.updateVoiceState();
        return voice;
    }



}
