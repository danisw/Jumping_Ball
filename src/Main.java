import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		JFrame obj=new JFrame();
		GameRule gameRule = new GameRule();
		
		obj.setBounds(10, 10, 700, 600);
		obj.setTitle("Jumping Ball");
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gameRule);
                obj.setVisible(true);
		
	}

}
