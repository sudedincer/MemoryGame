import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        JFrame frame= new JFrame();

        Memory memoryGame = new Memory();

        frame.setSize(410,430);//In these lines, we set the dimensions, position, and other properties of the frame.
        frame.setTitle("Memory Game");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(memoryGame);
        frame.setLocation(500,100);
        frame.setVisible(true);

    }
}