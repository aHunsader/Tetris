package Tetris;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {
	public static void main(String[] args){
		JFrame frame = new JFrame("Tetris");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(Constants.WIDTH + Constants.SIDEBAR, Constants.HEIGHT);
		frame.setLocationRelativeTo(null);
		final Tetris game = new Tetris();
		frame.add(game);
		JOptionPane.showMessageDialog(null, "Start Game:");
		frame.setVisible(true);
		frame.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				Shape lastShape = game.shapes.get(game.shapes.size()-1);

				if (e.getKeyCode() == KeyEvent.VK_UP) {
					if(lastShape.testLeft(game.allBlocks) && lastShape.testRight(game.allBlocks) 
							&& lastShape.testDown(game.allBlocks)) {
						lastShape.rotate();
					}
				}
				else if(e.getKeyCode() == KeyEvent.VK_DOWN){
					game.tryDown(true);
				}
				else if(e.getKeyCode() == KeyEvent.VK_LEFT){
					boolean check = false; 
					for(Block b :lastShape.getList()){
						if(b.getX() <= 0){
							check = true;
						}
					}
					if(!check){
						if(lastShape.testLeft(game.allBlocks)){
							lastShape.translateShapeL();
						}
					}		
				}
				else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
					boolean check = false; 
					for(Block b :lastShape.getList()){
						if(b.getX() + Constants.SIZE >= Constants.WIDTH){
							check = true;
						}
					}
					if(!check){
						if(lastShape.testRight(game.allBlocks)){
							lastShape.translateShapeR();
						}
					}	
				}
				else if(e.getKeyCode() == KeyEvent.VK_ENTER){
					int displacement = 0;
					while(lastShape.testDown(game.allBlocks)){
						game.tryDown(false);
						displacement++;
					}
					game.score += Constants.HARD*displacement;
				}
				else if(e.getKeyCode() == KeyEvent.VK_SHIFT) {
					game.switchHold();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
		
		while(true) {
			try {
				Thread.sleep(Constants.LEVEL_TIME);
			} catch (Exception e) {
				
			}
			game.tryDown(false);
		}
	}
}