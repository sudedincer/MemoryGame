import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class Memory extends JPanel implements MouseListener {

    public int[][] matrix = new int[4][4];
    public Color[] color ={Color.blue,Color.cyan,Color.green,Color.pink,Color.yellow,Color.magenta,Color.gray,Color.red};
    public long startTime;
    public boolean[][] clicked = new boolean[4][4];
    public int x1=-1,x2=-1;
    public int y1=-1,y2=-1;
    public boolean[][] matched=new boolean[4][4];
    private int clickCount=0;
    private int gameover=0;
    private Timer initialTimer;
    private boolean showColors = true;


    public Memory() {
        //We created an array with random numbers.
        int[] control = new int[16];
        for(int i=0;i<16;i++) {
            control[i] = 0;
        }
        Random random=new Random();
        for (int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                while(true){
                    int randomNumber = random.nextInt(16);
                    if (control[randomNumber] == 0) {
                        matrix[i][j] = randomNumber % 8;
                        control[randomNumber] = 1;
                        break;
                    }
                }
            }
        }
        addMouseListener(this);//We added functionality to detect mouse touches on the panel.
        startTime = System.currentTimeMillis();//We took the starting time to calculate how long the game will last.


        initialTimer = new Timer(5000, new ActionListener() {
            @Override                                      //We created a function for the game to display all colors for 5 seconds when it is first opened.
            public void actionPerformed(ActionEvent e) {
                showColors = false;
                repaint();
                initialTimer.stop();
            }
        });
        initialTimer.start();
    }

    // We drew the lines and circles.

    public void paint(Graphics g ) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        //We drew the colors on the screen when the game first started.
        if (showColors) {
            for (int m = 0; m < 4; m++) {
                for (int n = 0; n < 4; n++) {
                    g2d.setPaint(color[matrix[m][n]]);
                    g2d.fillOval(10 + m * 100, 10 + n * 100, 80, 80);
                }
            }
        } else {
            for (int m = 0; m < 4; m++) {
                for (int n = 0; n < 4; n++) {
                    g2d.setPaint(Color.black);
                    g2d.drawRect(m * 100, n * 100, 100, 100);

                    //We painted the colors based on mouse clicks.
                    if (matched[m][n] || clicked[m][n]) {
                        g2d.setPaint(color[matrix[m][n]]);
                        g2d.fillOval(10 + m * 100, 10 + n * 100, 80, 80);
                    } else {
                        g2d.setPaint(Color.white);
                        g2d.fillOval(10 + m * 100, 10 + n * 100, 80, 80);
                    }

                }
            }
        }
    }

    public void gameOver(int gameover){  //We created a function to check for the end of the game.
        if(gameover==8){
            long endTime = System.currentTimeMillis(); // We recorded the time when the game ended.
            long elapsedTime = (endTime - startTime) / 1000; // We calculated the elapsed time and converted it to seconds.
            JOptionPane.showMessageDialog(null, "GAME OVER!\n You found all pairs in " + elapsedTime + " seconds!");
            System.exit(0);
        }
    }
    public void systemExit(){//We created a function to end the game when we make three mistakes.
        JOptionPane.showMessageDialog(null,"GAME OVER!\n You made three mistakes! ");
        System.exit(0);
    }

    //We captured mouse clicks and checked the clicks.

    public void mouseClicked(MouseEvent e) {
        int x = e.getX() / 100;
        int y = e.getY() / 100;
        //We started the game after the display time for colors elapsed.
        if (!initialTimer.isRunning()) {
            repaint();
        }

        if (!matched[x][y]) {
            if (x1 == -1 && y1 == -1) {
                x1 = x;
                y1 = y;
                clicked[x][y] = true;
            } else if (x2 == -1 && y2 == -1 && (x1 != x || y1 != y)) {
                x2 = x;
                y2 = y;
                clicked[x][y] = true;

                if (matrix[x1][y1] == matrix[x2][y2]) {
                    matched[x1][y1] = true;
                    matched[x2][y2] = true;
                    x1 = -1;
                    y1 = -1;
                    x2 = -1;
                    y2 = -1;
                    gameover++;
                    gameOver(gameover);
                } else {
                    // If there was no match, we preserved the colors.
                    Timer timer = new Timer(1000, new ActionListener() {//We wrote a function that displays our wrong choice for 1 second when we make an incorrect selection.
                        @Override
                        public void actionPerformed(ActionEvent arg0) {
                            clicked[x1][y1] = false;
                            clicked[x2][y2] = false;
                            x1 = -1;
                            y1 = -1;
                            x2 = -1;
                            y2 = -1;

                            repaint();
                            clickCount++;
                            if (clickCount == 3) {
                                systemExit();
                            }
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                }
            }
            repaint();

        }
    }
    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
