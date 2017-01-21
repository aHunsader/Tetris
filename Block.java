package Tetris;

import java.awt.Color;

public class Block {
	private int x;
	private int y;
	private Color col;
	
	public Block(int x, int y, Color col){
		this.x = x;
		this.y = y;
		this.col = col;
	}
	public void translateRight(){
		setXY(getX()+Constants.SIZE, getY());
		}
	public void translateLeft(){
		setXY(getX()-Constants.SIZE, getY());
	}
	public void translateDown(){
		setXY(getX(), getY() + Constants.SIZE);
	}
	public void setXY(int x, int y){
		this.x = x;
		this.y = y;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public Color getColor(){
		return col;
	}
}