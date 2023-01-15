package drumpad;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;


public class frame extends JFrame {
        private final JPanel panel = new JPanel();
        private final JButton[] bottone;
        private  final Random rand = new Random();
        private final JButton trackButton = new JButton("🔖");
        private final JButton repeatbutton=new JButton("▶️");

        private boolean isTracked=false;

        private final JButton clearbutton=new JButton("🗑️");
        private final JPanel statepanel=new JPanel();

        private final JSplitPane paneldivider= new JSplitPane(JSplitPane.VERTICAL_SPLIT,panel,statepanel);

        private final JButton stoplay =new JButton("⏹️");
        private Clip currentClip;
        private final JTextArea replaysound=new JTextArea();














    public frame() throws LineUnavailableException, UnsupportedAudioFileException, IOException {

        //frame style
        super("drumpad app");
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setSize(500, 400);
        add(paneldivider);



        paneldivider.setOneTouchExpandable(true);
       //component
        File folder = new File("./assets");
        File[] soundFiles = folder.listFiles();
        Clip[] clips=new Clip[soundFiles.length];
        List<Integer> clickedSounds = new ArrayList<>();
        statepanel.setLayout(new BorderLayout());


        statepanel.add(trackButton,BorderLayout.PAGE_START);

        statepanel.add(repeatbutton,BorderLayout.LINE_START);

        statepanel.add(clearbutton,BorderLayout.CENTER);

        statepanel.add(stoplay,BorderLayout.LINE_END);


        statepanel.add(replaysound,BorderLayout.PAGE_END);

        replaysound.setPreferredSize(new Dimension(200,200));
        replaysound.setRows(12);









        replaysound.setEditable(false);


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
                if (currentClip != null) {
                    currentClip.stop();
                }
                currentClip = clips[j];
                clips[j].setFramePosition(0);
                clips[j].start();

                clickedSounds.add(j);

            });
        }// end sound assigment

        trackButton.addActionListener(e -> {
            for(int i=0; i<clickedSounds.size(); i++){
                int soundIndex = clickedSounds.get(i);

                System.out.print(soundFiles[soundIndex].getName()+",");
                replaysound.append((soundFiles[soundIndex].getName()+"\n"));
            }





                isTracked=true;

        });// end track buttom

        repeatbutton.addActionListener(e -> {
            if (isTracked) {
                for (int i = 0; i < clickedSounds.size(); i++) {
                    int buttonid=clickedSounds.get(i);

                    try {
                        clips[buttonid].stop();
                        clips[buttonid].setFramePosition(0);
                        clips[buttonid].start();
                        Thread.sleep(300); //change time if u want play sound separately or in same time
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });// end action listener repeat
        clearbutton.addActionListener(e -> {

            clickedSounds.clear();
            replaysound.setText("");


        });// end actio listener clear button
        // end run



        stoplay.addActionListener(e -> {
            if (currentClip != null) {
                currentClip.stop();
            }



        });// end event listener



    }// end frame




}// end extend jframe
