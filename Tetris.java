package Tetris;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.*;

public class Tetris extends JComponent {
	protected boolean[][] board;
	protected ArrayList<Block> allBlocks;
	protected ArrayList<Shape> shapes;
	protected int rowsCleared;
	protected int score;
	protected Shape hold = null;
	
	public Tetris(){
		Thread animationThread = new Thread(new Runnable(){
			public void run() {
				while(true){
					repaint();
				}
			}
		});
				animationThread.start();
				allBlocks = new ArrayList<Block>();
		        shapes = new ArrayList<Shape>();
				allBlocks = new ArrayList<Block>();
		        board = new boolean[10][20];
				for(int i = 0; i< board.length; i++){
					for (int j = 0; j< board[0].length; j++){
						board[i][j] = false;
					}
				}
				Shape s = new Shape(Constants.RAND.nextInt(7)+1);
				shapes.add(s);
				rowsCleared = 0;
				Constants.LEVEL_TIME = 1000;
				score = 0;

	}
	public void paintComponent(Graphics g){
		Graphics2D gg = (Graphics2D) g;
		drawBlocks(gg);
		drawSideBar(gg);
		drawHold(gg);
		if(gameOver()){
			endGameScreen(gg);
		}
	}
	public boolean rowCheck(int row) {
		for(int i = 0; i < board.length; i++) {
			if(!board[i][row]){
				return false;
				}
			}
		return true;
	}
	
	public void delRows(){
		int count = 0;
		for(int a = 0; a < board[0].length; a++) {
			if(rowCheck(a)) {
				rowsCleared++;
				count++;
				if(rowsCleared % 10 == 0) {
					Constants.LEVEL_TIME = Math.max(Constants.LEVEL_TIME-50, 0);
				}
				for(int i = allBlocks.size()-1; i>=0; i--) {
					if(allBlocks.get(i).getY() / Constants.SIZE == a) { 
						allBlocks.remove(i);
					} else if(allBlocks.get(i).getY() / Constants.SIZE < a) {
						allBlocks.get(i).translateDown();
					}
				}
				for(int x = 0; x < board.length; x++){
					for(int y = a; y >= 1; y--){
						board[x][y] = board[x][y-1];
					}
					board[x][0] = false;
				}
			}
		}
		switch(count){ //scoring
			case 0:
				break;
			case 1:
				score += Constants.SINGLE;
				break;
			case 2:
				score += Constants.DOUBLE;
				break;
			case 3:
				score += Constants.TRIPLE;
				break;
			case 4:
				score += Constants.TET_LINE;
				break;
		}
			
	}
	
	public void tryDown(boolean bool){
		boolean check = false; //definitely replace with sam method
		for(Block b :shapes.get(shapes.size()-1).getList()){
			if(b.getY() + 2.5*Constants.SIZE >= Constants.HEIGHT){
				check = true;
			}
		}
		if(!check && shapes.get(shapes.size()-1).testDown(allBlocks)){
			shapes.get(shapes.size()-1).translateShapeD();
			if(bool){
				score++;
			}
		}	
		else{
			for(Block b : shapes.get(shapes.size()-1).getList()){
				allBlocks.add(b);
				board[b.getX()/Constants.SIZE][b.getY()/Constants.SIZE] = true;
			}
			delRows();
			if(!gameOver()) {
				shapes.add(new Shape(Constants.RAND.nextInt(7)+1));
				Constants.K_SWITCH = 0;
			}
		}
	}
	
	public void switchHold() {
		if(Constants.K_SWITCH < 1) {
			if(hold == null) {
				hold = shapes.get(shapes.size() - 1);
				shapes.set(shapes.size() - 1, new Shape(Constants.RAND.nextInt(7)+1));
			} else {
				Shape temp = hold;
				hold = shapes.get(shapes.size() - 1);
				shapes.set(shapes.size() - 1, new Shape(temp.getType()));
			}
		}
		Constants.K_SWITCH++;
	}
	
	public boolean gameOver() {
		for(int a = 0; a < board.length; a++) {
			if(board[a][0]) {
				return true;
			}
		}
		return false;
	}
	
	public void drawBlocks(Graphics2D gg){
		for(Block b : shapes.get(shapes.size()-1).getList()){
			gg.setColor(b.getColor());
			gg.fillRect(b.getX(), b.getY(), Constants.SIZE, Constants.SIZE);
			gg.setColor(Color.BLACK);
			gg.drawRect(b.getX(), b.getY(), Constants.SIZE, Constants.SIZE);
		}
		for(Block b : allBlocks){
			gg.setColor(b.getColor());
			gg.fillRect(b.getX(), b.getY(), Constants.SIZE, Constants.SIZE);
			gg.setColor(Color.BLACK);
			gg.drawRect(b.getX(), b.getY(), Constants.SIZE, Constants.SIZE);
		}
	}
	public void drawSideBar(Graphics2D gg){
		gg.setColor(new Color(160,82,45));
		gg.fillRect(Constants.WIDTH, 0, Constants.SIZE, Constants.HEIGHT);
		gg.setColor(Color.BLACK);
		gg.drawRect(Constants.WIDTH, 0, Constants.SIZE, Constants.HEIGHT);
		gg.drawString("Score:", Constants.WIDTH + Constants.SIDEBAR/3, 2*Constants.HEIGHT/5);
		gg.drawString("" + score, Constants.WIDTH + Constants.SIDEBAR/3, 2*Constants.HEIGHT/5 + 40);
		gg.drawString("Rows Cleared:", Constants.WIDTH + Constants.SIDEBAR/3, 2*Constants.HEIGHT/3);
		gg.drawString("" + rowsCleared, Constants.WIDTH + Constants.SIDEBAR/3, 2*Constants.HEIGHT/3 + 40);
		gg.drawString("Hold:", Constants.WIDTH + Constants.SIDEBAR/3, 25);
		
	}
	public void endGameScreen(Graphics2D gg){
		gg.setColor(Color.RED);
		gg.fillRect(Constants.WIDTH/2-100, Constants.HEIGHT/2-100, 200, 200);
		gg.setColor(Color.BLACK);
		gg.drawRect(Constants.WIDTH/2-100, Constants.HEIGHT/2-100, 200, 200);
		gg.drawString("GAME OVER", Constants.WIDTH/2 - 35, Constants.HEIGHT/2 - 15);
	}
	public void drawHold(Graphics2D gg){
		if(hold != null) {
			for(Block b : new Shape(hold.getType()).getList()){
				b.setXY(b.getX() + 250, b.getY() + 50);
				gg.setColor(b.getColor());
				gg.fillRect(b.getX(), b.getY(), Constants.SIZE, Constants.SIZE);
				gg.setColor(Color.BLACK);
				gg.drawRect(b.getX(), b.getY(), Constants.SIZE, Constants.SIZE);
			}
		}
	}
}