package jogoPong.states;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Gerencia os estados do jogo (ex.: Menu, Level1, Help etc.).
 * Atua como um controlador central para alternar e manter o fluxo do jogo.
 */

public class StateManager implements KeyListener {
    public static final int NUMBER_STATES = 4; 
    public static State[] states = new State[NUMBER_STATES];
    public static int currentState = 0; 

    // Constantes que representam os estados
    public static final int FPS = 0;
    public static final int MENU = 1;
    public static final int LEVEL1 = 2;
    public static final int HELP = 3; 

    public static void setState(int state) {
        if (state >= 0 && state < NUMBER_STATES) {
            currentState = state;
            states[currentState].init();
        } else {
            System.err.println("Estado inválido: " + state);
        }
    }

    public static State getState() {
        return states[currentState];
    }

    //Construtor: Inicializa os estados disponíveis
    public StateManager() {
        states[FPS] = new FPSState(); 
        states[MENU] = new MenuState(); 
        states[LEVEL1] = new LevelState(); 
        states[HELP] = new HelpState(); 
    }

    public void update() {
        states[currentState].update();
    }

    public void render(Graphics g) {
        states[currentState].render(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {
     
    }

    @Override
    public void keyPressed(KeyEvent e) {
        states[currentState].KeyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        states[currentState].KeyReleased(e.getKeyCode());
    }
}
