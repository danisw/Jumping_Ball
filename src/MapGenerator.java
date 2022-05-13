import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class MapGenerator 
{
	public int map[][];
	public int brickWidth;
	public int brickHeight;
	
	public MapGenerator (int row, int col)
	{
		map = new int[row][col];		//inisiasi baris dan kolom map game
		for(int i = 0; i<map.length; i++)
		{
			for(int j =0; j<map[0].length; j++)
			{
				map[i][j] = 1;
				//misal i = 2, j =3 yaitu 2 baris 3 kolom
				/*
				 map=
				 1,1,1,
				 1,1,1,
				 angka 1 akan diubah menjadi balok
				 */
			}			
		}
		
		brickWidth = 540/col; //lebar balok. 540 semua kolom dr ujung kiri layar isi balok sampe kanan
		brickHeight = 150/row;
	}	
	
	public void draw(Graphics2D g)
	{

		Color brickColor = new Color(214,82, 24);

		for(int i = 0; i<map.length; i++)
		{
			for(int j =0; j<map[0].length; j++)
			{
				if(map[i][j] > 0)
				{

					// this is for colouring the brick
					g.setColor(brickColor);
					g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight); // posisi koordinat x = jumlah baris +80, jumlah kolom+50
					
					// this is just to show separate brick, game can still run without it
					g.setStroke(new BasicStroke(5));
					g.setColor(Color.black);
					g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
				}
			}
		}
	}
	
	public void setBrickValue(int value, int row, int col)
	{
		map[row][col] = value;
	}
}
