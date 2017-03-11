package ImageHidingCode;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.*;

public class Sound 
{
    private AudioFormat format;
    private byte[] samples;
    static int totalbytes = 0;
    public Sound(String filename)
    {
        try
        {
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(filename));
            format = stream.getFormat();
            samples = getSamples(stream);
        }
        catch (UnsupportedAudioFileException e){e.printStackTrace();}
        catch (IOException e){e.printStackTrace();}
    }

    public byte[] getSamples()
    {
        return samples;
    }

    public byte[] getSamples(AudioInputStream stream)
    {
        int length = (int)(stream.getFrameLength() * format.getFrameSize());
        byte[] samples = new byte[length];
        DataInputStream in = new DataInputStream(stream);
        try
        {
            in.readFully(samples);
        }
        catch (IOException e){e.printStackTrace();}
        return samples;
    }
    
    static String getBits_data(byte b)
	{
	    String result = "";
	    for(int i = 0; i < 8; i++)
	        result += (b & (1 << i)) == 0 ? "0" : "1";
	    return result;
	}

    public void play(InputStream source)
    {
        int bufferSize = format.getFrameSize() * Math.round(format.getSampleRate());// / 10);
        byte[] buffer = new byte[bufferSize];
        String content ="";
        SourceDataLine line;
        try
        {
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            line = (SourceDataLine)AudioSystem.getLine(info);
            line.open(format, bufferSize);
        }
        catch (LineUnavailableException e)
        {
            e.printStackTrace();
            return;
        }
        
        line.start();

        try
        {
            int numBytesRead = 0;
            while (numBytesRead != -1)
            {
                numBytesRead = source.read(buffer, 0, buffer.length);
                // Uncomment this section for generating codes of your audio file
                /*for(byte b : buffer)
                {
                	content += getBits_data(b);
                }*/
                //totalbytes+=numBytesRead;
               // numBytesRead = -1;
               if (numBytesRead != -1)
                    line.write(buffer, 0, numBytesRead);
               // System.out.println(content);
            }
        }
        catch (IOException e){e.printStackTrace();}

        line.drain();
        line.close();
    }
    public static void main(String[] args) 
    {
           Sound player = new Sound("/Users/manasgaur/Downloads/president_speech.wav");
           InputStream stream = new ByteArrayInputStream(player.getSamples()); 
           player.play(stream);
           System.out.println("end"+ totalbytes);
    }
}


