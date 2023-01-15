package drumpad;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;


public class frame extends JFrame {
    private final JPanel panel = new JPanel();
    private final JButton[] bottone;
    private  final Random rand = new Random();
    private final JButton trackButton = new JButton("Track");
    private final JButton repeatbutton=new JButton("riproduci");
    private boolean isTracked=false;





    public frame() throws LineUnavailableException, UnsupportedAudioFileException, IOException {

        //frame style
        super("drumpad app");
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setSize(500, 200);
        add(panel);
        //component


        File folder = new File("./assets");
        File[] soundFiles = folder.listFiles();
        Clip[] clips=new Clip[soundFiles.length];
        ArrayList<Integer> clickedButtons = new ArrayList<Integer>();

        panel.add(trackButton);
        panel.add(repeatbutton);

        bottone=new JButton[soundFiles.length];

        panel.setLayout(new GridLayout(4, 4));
        for(int i = 0; i < soundFiles.length; i++) {
            bottone[i]=new JButton("pad"+(i+1));

            panel.add(bottone[i]);
            bottone[i].setBackground(new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256)));
            bottone[i].setToolTipText(String.valueOf(soundFiles[i]));
            bottone[i].setForeground(Color.white);





        }// end for button

    for (int i = 0; i < soundFiles.length; i++) {
        final int j = i;

          clips[i] = AudioSystem.getClip();


          clips[i].open(AudioSystem.getAudioInputStream(soundFiles[i]));



        bottone[i].addActionListener(e -> {
            clips[j].stop();
            clips[j].setFramePosition(0);
            clips[j].start();
            clickedButtons.add(j);

        });


    }// end assignament sound
        trackButton.addActionListener(e -> {

                System.out.print(clickedButtons.size()+"," );
                isTracked=true;

        });// end track buttom

        repeatbutton.addActionListener(e -> {
            if (isTracked) {
                for (int i = 0; i < clickedButtons.size(); i++) {
                    int buttonid=clickedButtons.get(i);


                    try {
                        clips[buttonid] = AudioSystem.getClip();
                    } catch (LineUnavailableException ex) {
                        throw new RuntimeException(ex);
                    }


                    try {
                        clips[buttonid].open(AudioSystem.getAudioInputStream(soundFiles[buttonid]));
                    } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                        throw new RuntimeException(ex);
                    }
                    clips[buttonid].setFramePosition(0);
                    clips[buttonid].start();


                   while (clips[buttonid].isRunning()){
                       try {
                           Thread.sleep(10);
                       } catch (InterruptedException ex) {
                           throw new RuntimeException(ex);
                       }


                   }// end while


                } //end iterator


            }// end if tracked


        });// end action listener




    }// end frame

}// end extend jframe
