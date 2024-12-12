package jogoPong.display;

//Importação de bibliotecas necessárias
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.net.ssl.KeyManager;
import javax.swing.JFrame;


public class Display {
	private JFrame jframe; // Janela principal do jogo
	private Canvas canvas; // Área de desenho onde o jogo será renderizado
	
	// Construtor: Inicializa a janela e o canvas
	public Display(String title, int width, int height){
		canvas = new Canvas(); // Cria o Canvas
		canvas.setPreferredSize(new Dimension(width, height)); // Define o tamanho preferido
		canvas.setMaximumSize(new Dimension(width, height));  // Define o tamanho máximo
		canvas.setMinimumSize(new Dimension(width, height));   // Define o tamanho mínimo
		
		jframe = new JFrame(title); // Cria a janela com o título fornecido
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Fecha a aplicação ao clicar no "X"
		jframe.setResizable(false);  // Evita redimensionamento da janela
		
		canvas.setFocusable(false);  // O foco do teclado será no JFrame, não no Canvas
		jframe.add(canvas);  // Adiciona o Canvas na janela
		jframe.pack();  // Ajusta o tamanho da janela para o Canvas
		
		
		jframe.setLocationRelativeTo(null);  // Centraliza a janela na tela
		jframe.setVisible(true);  // Torna a janela visível
		
	}
	
	// Retorna a estratégia de buffer do Canvas
	public BufferStrategy getBufferStrategy() {
		return canvas.getBufferStrategy();
	}
	
	 // Cria a estratégia de buffer do Canvas
	public void createBufferStrategy() {
		canvas.createBufferStrategy(3);
	}
	
	 // Adiciona um KeyListener à janela
	public void setKeyListener(KeyListener kl) {
		jframe.addKeyListener(kl);
	}

	//vazio por enquanto.
	public void setKeyListener(KeyManager km) {
		// TODO Auto-generated method stub
		
	}
	
}

