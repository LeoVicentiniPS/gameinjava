package uteis;

import static uteis.Config.*;

import java.awt.*;

public class Interface {
    public static final void drawMenu(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("Java Wizard", WINDOW_WIDTH / 2 - 120, WINDOW_HEIGHT / 2 - 50);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("Pressione ENTER para começar", WINDOW_WIDTH / 2 - 160, WINDOW_HEIGHT / 2);
    }

    public static final void drawPause(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 32));
        g.drawString("Jogo Pausado", WINDOW_WIDTH / 2 - 100, WINDOW_HEIGHT / 2);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Pressione P para voltar", WINDOW_WIDTH / 2 - 110, WINDOW_HEIGHT / 2 + 40);
    }

    public static final void drawGameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("Game Over", WINDOW_WIDTH / 2 - 100, WINDOW_HEIGHT / 2 - 50);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("Pressione ENTER para reiniciar", WINDOW_WIDTH / 2 - 160, WINDOW_HEIGHT / 2);
    }
}
