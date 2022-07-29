import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;



import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener{
	static final int SCREEN_WIDTH= 600;
	static final int SCREEN_HEIGHT= 600;
	static final int UNIT_SIZE= 30;
	static final int GAME_UNITS= (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	int delay= 100;
	static final int x[]=new int[GAME_UNITS];
	static final int y[]=new int[GAME_UNITS];
	int bodyParts = 6;
	int score;
	int appleX;
	int appleY;
	char direction= 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	
	
			
			
	GamePanel(){
		random= new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startgame();
		
		
		
	}
	public void startgame() {
		newApple();
		running=true;
		timer= new Timer(delay, this);
		timer.start();
		
		
		
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
		
		
	}
	
	public void draw(Graphics g) {
		if (running) {
			for(int i=0; i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
			}
			g.setColor(Color.green);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
		
			for(int i=0; i<bodyParts;i++) {
				if(i==0) {
					g.setColor(new Color(100, 10, 132));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				
				}
				else {
					g.setColor(new Color(146, 2, 137));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			g.setColor(Color.white);
			g.setFont(new Font("Ink Free", Font.BOLD, 15));
			FontMetrics metrics= getFontMetrics(g.getFont());
			g.drawString("Score "+ score, (SCREEN_WIDTH-500), (SCREEN_HEIGHT-10));
		
		}
		else {
			gameOver(g);
			finalScore(g);
		}
	}
	public void move() {
		for(int i=bodyParts; i>0; i--) {
			x[i]=x[i-1];
			y[i]=y[i-1];
			
			
		}
		switch(direction){
		case 'U':
			y[0]=y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0]=y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0]=x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0]=x[0] + UNIT_SIZE;
			break;
			
		}
		
		
	}
	
	public void newApple() {
		appleX= random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY= random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
		
		
	}
	
	public void checkApple() {
		if((x[0]== appleX) && (y[0]==appleY)) {
			if(delay>50){
				bodyParts++;
				newApple();
				delay-=2;
				timer.stop();
				timer= new Timer(delay, this);
				timer.start();
				score++;
			}
			else if(delay<=50){
				bodyParts++;
				newApple();
				score++;
				
			}
			
		}
		
	}
	
	public void checkCollisions() {
		//verifica si hay colision con el cuerpo
		for(int i=bodyParts; i>0; i--) {
			if(x[0]==x[i] && y[0]==y[i]) {
				running=false;	
				
			}
		}
		//verifica si hay colision a la izquierda
		if(x[0]<0) {
			running=false;
		}
		//verifica si hay colision a la derecha
		if(x[0]>SCREEN_WIDTH-1) {
			running=false;
		}
		//verifica si hay colision abajo
		if(y[0]<0) {
			running=false;
		}
		//verifica si hay colision arriba
		if(y[0]>SCREEN_HEIGHT) {
			running=false;
		}
		
		if(!running) {
			timer.stop();
		}
		
			
		
	}
	
	public void gameOver(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics= getFontMetrics(g.getFont());
		g.drawString("GAME OVER", (SCREEN_WIDTH-metrics.stringWidth("GAME OVER"))/2, (SCREEN_HEIGHT-metrics.stringWidth("GAME OVER"))/2);
		
		
		
	}
	
	public void finalScore(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("Ink Free", Font.BOLD, 50));
		g.drawString("Score "+ score, (SCREEN_WIDTH-380), (SCREEN_HEIGHT-100));
		
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_A:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_D:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_W:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			
			case KeyEvent.VK_S:
			if(direction != 'U') {
				direction = 'D';
				}
				break;
		}
			
		}
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			checkApple();
			checkCollisions();
			
		}
		repaint();
		
		
	}

}
