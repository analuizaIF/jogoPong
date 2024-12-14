package jogoPong.display;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.net.ssl.KeyManager;
import javax.swing.JFrame;

/*/
 * Gerencia a interface gráfica do jogo, incluindo a criação da janela principal 
 * (JFrame) e o painel de desenho (Canvas), usado para renderização.
 */

public class Display {
	private JFrame jframe; 
	private Canvas canvas; 
	
	/*/
	 * CONSTRUTOR: configuracoes de Frame e Painel
	 */
	public Display(String title, int width, int height){
		canvas = new Canvas(); 
		canvas.setPreferredSize(new Dimension(width, height)); 
		canvas.setMaximumSize(new Dimension(width, height));  
		canvas.setMinimumSize(new Dimension(width, height)); 
		
		jframe = new JFrame(title); 
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		jframe.setResizable(false); 
		
		canvas.setFocusable(false); 
		jframe.add(canvas);  
		jframe.pack(); 
		
		jframe.setLocationRelativeTo(null);  
		jframe.setVisible(true);
	}
	
	//Retorna a estratégia de buffer do Canvas
	public BufferStrategy getBufferStrategy() {
		return canvas.getBufferStrategy();
	}
	
	public void createBufferStrategy() {
		canvas.createBufferStrategy(3);
	}
	
	public void setKeyListener(KeyListener kl) {
		jframe.addKeyListener(kl);
	}	
}

