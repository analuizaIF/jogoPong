package jogoPong;

//Importações necessárias para gráficos, multithread e outros.
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import jogoPong.display.Display;
import jogoPong.input.KeyManager;
import jogoPong.states.StateManager;


public class Game implements Runnable { // Essa classe é o "coração" do jogo, que cuida de inicializar e rodar tudo.
	
	private Display display; // Janela do jogo
	private Thread thread;  //Para rodar o jogo em paralelo (multithreading)
	private boolean running = false; // Indica se o jogo está em execução ou não
	
	public static final int width = 400, height = 300; // Tamanho da tela do jogo
	
	private StateManager sm; // Gerenciador de estados do jogo (menu, gameplay, etc.)
	private KeyManager km;  // Gerenciador de entradas do teclado
	
	// Construtor do jogo: Inicializa os componentes básicos
	public Game() {
		display = new Display("Pong", width, height);  // Cria a janela com título e dimensões
		sm = new StateManager();  // Cria o gerenciador de estados
		km = new KeyManager();  // Cria o gerenciador de teclas
		display.setKeyListener(sm); // Adiciona o gerenciador de estados como escutador de teclado
		display.setKeyListener(km);  // Adiciona o gerenciador de teclas como escutador de teclado
		
		StateManager.setState(StateManager.MENU);  // Define o estado inicial como o menu
	}

	@Override
	public void run() { // Esse método é chamado quando a Thread é iniciada
		init();  // Inicializações adicionais (vazia aqui, mas pode ser usada futuramente)
		
		int FPS = 60; // Define o número de frames por segundo
		double timePerTick = 1000000000/60; // Quanto tempo dura um frame
		double delta = 0;  // Acumula o tempo para controlar os frames
		long now;
		long lastTime = System.nanoTime(); // Pega o tempo atual em nanossegundos
		
		// Loop principal do jogo
		while(running) {
			now = System.nanoTime();  // Pega o tempo atual
			delta += (now -lastTime)/ timePerTick; // Calcula quanto tempo se passou
			lastTime = now;  // Atualiza o último tempo
			
			if (delta >= 1) {  // Se passou o tempo necessário para um frame...
				update();  // Atualiza a lógica do jogo
				render(); // Desenha o jogo na tela
				delta--;  // Reduz o tempo acumulado
			}
			
		}
		stop();  // Para a execução quando `running` for falso
		
	}
	
	private void init() {
	// Método vazio por enquanto, mas poderia ser usado para inicializar coisas adicionais.	
	}
	
	// Atualiza a lógica do jogo	
	private void update() { 
		if (StateManager.getState() ==null) return; // Se não há estado ativo, não faz nada
			sm.update();  // Atualiza o estado ativo
			km.update();  // Atualiza o gerenciador de teclas
			
		
	}
	
	// Cuida da renderização dos gráficos na tela
	private void render() {
		BufferStrategy bs = display.getBufferStrategy(); // Garante que os gráficos sejam processados suavemente
		if (bs ==null) {  // Se ainda não existe um buffer...
			display.createBufferStrategy();  // Cria um buffer
			bs = display.getBufferStrategy(); // Pega o novo buffer
		}
		
		Graphics g = bs.getDrawGraphics();  // Objeto usado para desenhar na tela
		g.clearRect(0, 0, width, height);  // Limpa a tela para evitar borrões
		
		if (StateManager.getState()!=null) {
			sm.render(g);  // Desenha o estado atual
		}
		
		g.dispose(); // Libera os recursos gráficos
		bs.show();  // Mostra o que foi desenhado
					
	}

	// Método que inicia o jogo
	public synchronized void start() {
		if (thread != null) return;  // Se já existe uma Thread rodando, não faz nada
		thread = new Thread (this);  // Cria uma nova Thread
		thread.start(); // Inicia a Thread, chamando o método `run()`
		running = true; // Define que o jogo está rodando
		
	}
	
	// Método que para o jogo
	public synchronized void stop() {
		if (thread ==null) return; // Se não existe uma Thread, não faz nada
		try {
			thread.join(); // Aguarda a Thread finalizar
		} catch (InterruptedException e) {
			e.printStackTrace();  // Mostra o erro no console, se houver
		}
		
	}

}
