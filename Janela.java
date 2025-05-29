import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class Janela {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Java Wizard");
        JPanel painel = new GamePanel();

        // Define o painel para ocupar a tela toda e remove as bordas e opção de resize (Fullscreen)
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setUndecorated(true);
        painel.setPreferredSize(screenSize);
        frame.add(painel);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        //ESC para sair do jogo
        painel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "sair");
        painel.getActionMap().put("sair", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
