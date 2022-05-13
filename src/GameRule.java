import java.awt.event.*;

import javax.swing.*;

import java.awt.*;

import javax.swing.Timer;

public class GameRule extends JPanel implements KeyListener, ActionListener
{

	private boolean play = false;
	private int score = 0;

	//jumlah balok
	private int totalBricks = 48;
	
	private Timer timer;
	private int delay=8;

	//koordinat awal pemain dan bola
	private int playerX = 310;
	
	private int ballposX = 120;
	private int ballposY = 350;

	//arah bola, minus berarti kalo x ke kiri, kalo y ke kanan
	private int ballXdir = -1;
	private int ballYdir = -2;
	
	private MapGenerator map;
	
	public GameRule()
	{		
		map = new MapGenerator(4, 12); // inisiasi jumlah kolom dan baris balok
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
        timer=new Timer(delay,this);
		timer.start();
	}
	
	public void paint(Graphics g)
	{    		
		// background
		Color bgcolor = new Color(205, 243, 251);
		g.setColor(bgcolor);
		g.fillRect(1, 1, 692, 592); // koordinat 1,1 dari kiri atas
		
		// drawing map
		map.draw((Graphics2D) g);
		
		// borders atau tepi untuk bounce ball
		g.setColor(Color.black);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);

		
		// the scores 		
		g.setColor(Color.black);
		g.setFont(new Font("serif",Font.BOLD, 25));
		g.drawString("Score : "+score, 500,30);
		
		// the paddle atau penampol
		g.setColor(Color.darkGray);
		g.fillRect(playerX, 500, 100, 10);

		// border ball
		g.setColor(Color.black);
		g.fillOval(ballposX, ballposY, 22, 22);

		// the ball
		g.setColor(Color.red);
		g.fillOval(ballposX, ballposY, 20, 20);
	
		// when you won the game
		if(totalBricks <= 0)
		{
			 play = false;
             ballXdir = 0;
     		 ballYdir = 0;
             g.setColor(Color.RED);
             g.setFont(new Font("serif",Font.BOLD, 30));
             g.drawString("You Won", 260,300);
             
             g.setColor(Color.RED);
             g.setFont(new Font("serif",Font.BOLD, 20));           
             g.drawString("Press (Enter) to Restart", 230,350);  
		}
		
		// when you lose the game
		if(ballposY > 570)
        {
			 play = false;
             ballXdir = 0;
     		 ballYdir = 0;
             g.setColor(Color.RED);
             g.setFont(new Font("serif",Font.BOLD, 30));
             g.drawString("Game Over, Scores: "+score, 190,300);
             
             g.setColor(Color.RED);
             g.setFont(new Font("serif",Font.BOLD, 20));           
             g.drawString("Press (Enter) to Restart", 230,350);        
        }
		
		g.dispose();
	}	

	public void keyPressed(KeyEvent e) 
	{
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) //kalo pencet tombol panah kanan
		{        
			if(playerX >= 600)
			{
				playerX = 600; //posisi awal di koordinat x 600
			}
			else
			{
				moveRight();
			}
        }
		
		if (e.getKeyCode() == KeyEvent.VK_LEFT) //kalo pencet tombol panah kiri
		{          
			if(playerX < 10)
			{
				playerX = 10; //posisi awal di koordinat x 10
			}
			else
			{
				moveLeft();
			}
        }		
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{          
			if(!play)
			{
				// level lebih mudah krn habis gagal
				play = true;
				ballposX = 120;
				ballposY = 350;
				ballXdir = -1;
				ballYdir = -2;
				playerX = 310;
				score = 0;
				totalBricks = 21;
				map = new MapGenerator(3, 7);
				
				repaint();
			}
        }		
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	
	public void moveRight()
	{
		play = true;
		playerX+=20; //posisi awal tambah ke kanan 20 point
	}
	
	public void moveLeft()
	{
		play = true;
		playerX-=20;  //posisi awal tambah ke kiri 20 point
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		timer.start();
		if(play)
		{			
			if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 30, 8)))
			{
				ballYdir = -ballYdir;
				ballXdir = -2;
			}
			else if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX + 70, 500, 30, 8)))
			{
				ballYdir = -ballYdir;
				ballXdir = ballXdir + 1;
			}
			else if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX + 30, 500, 40, 8)))
			{
				ballYdir = -ballYdir;
			}
			
			// check map collision with the ball		
			A: for(int i = 0; i<map.map.length; i++)
			{
				for(int j =0; j<map.map[0].length; j++)
				{				
					if(map.map[i][j] > 0)
					{
						//scores++;
						int brickX = j * map.brickWidth + 80;
						int brickY = i * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);					
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect))
						{					
							map.setBrickValue(0, i, j);
							score+=5;	
							totalBricks--;
							
							// when ball hit right or left of brick
							if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width)	
							{
								ballXdir = -ballXdir;
							}
							// when ball hits top or bottom of brick
							else
							{
								ballYdir = -ballYdir;				
							}
							
							break A;
						}
					}
				}
			}
			
			ballposX += ballXdir;
			ballposY += ballYdir;
			
			if(ballposX < 0)
			{
				ballXdir = -ballXdir;
			}
			if(ballposY < 0)
			{
				ballYdir = -ballYdir;
			}
			if(ballposX > 670)
			{
				ballXdir = -ballXdir;
			}		
			
			repaint();		
		}
	}
}
