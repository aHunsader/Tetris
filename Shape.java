package Tetris;

import java.awt.Color;
import java.util.ArrayList;

public class Shape {
	private ArrayList<Block> list;
	private int type;
	private Color col;
	
	public Shape(int type){
		list = new ArrayList<>();
		this.type = type;
		int l = Constants.SIZE;
		int c = (int)(.5*Constants.WIDTH) - 2*Constants.SIZE;
		switch(type){
			case 1: //Square
				col = Color.YELLOW;
				list.add(new Block(c+l,0,col));
				list.add(new Block(2*l+c,0,col));
				list.add(new Block(c+l,l,col));
				list.add(new Block(c+2*l,l,col));
				break;
			case 2: //T thing
				col = new Color(160, 32, 240);
				list.add(new Block(c,0,col));
				list.add(new Block(c+2*l,0,col));
				list.add(new Block(c+l,0,col));
				list.add(new Block(c+l,l,col));
				break;
			case 3: //S thing
				col = Color.GREEN;
				list.add(new Block(c,0,col));
				list.add(new Block(c+l,0,col));
				list.add(new Block(c+l,l,col));
				list.add(new Block(c+2*l,l,col));
				break;
			case 4: //Z thing
				col = Color.RED;
				list.add(new Block(c,l,col));
				list.add(new Block(c+l,0,col));
				list.add(new Block(c+l,l,col));
				list.add(new Block(c+2*l,0,col));
				break;
			case 5: //L thing
				col = new Color(255,140,0);
				list.add(new Block(c,0,col));
				list.add(new Block(c,l,col));
				list.add(new Block(c+l,l,col));
				list.add(new Block(c+2*l,l,col));
				break;
			case 6: //J thing
				col = new Color(30,144,255);
				list.add(new Block(c,l,col));
				list.add(new Block(c,0,col));
				list.add(new Block(c+l,0,col));
				list.add(new Block(c+2*l,0,col));
				break;
			case 7: //Line
				col = Color.CYAN;
				list.add(new Block(c,0,col));
				list.add(new Block(c+l,0,col));
				list.add(new Block(c+2*l,0,col));
				list.add(new Block(c+3*l,0,col));
				break;
		}	
	}
		public void translateShapeR(){
			for(Block b : list){
				b.translateRight();
			}
		}
		public void translateShapeL(){
			for(Block b : list){
				b.translateLeft();
			}
		}
		public void translateShapeD(){
			for(Block b : list){
				b.translateDown();
			}
		}
		
		public void rotate() {
			if(type != 1){
				int x = list.get(2).getX();
				int y = list.get(2).getY();
				for(Block b: list) {
					int x_1 = b.getX() - x;
					int y_1 = b.getY() - y;
					b.setXY(x - y_1, y + x_1);
				}
			}
		}
	public ArrayList<Block> getList(){
		return list;
	}
	public Color getColor(){
		return col;
	}
	public boolean testLeft(ArrayList<Block> blist){
		for(Block b : blist){
			for(Block c : list){
				if((b.getX() + Constants.SIZE == c.getX()) && (b.getY() == c.getY())){
				return false;
				}
			}
		}
		return true;
	}
	public boolean testRight(ArrayList<Block> blist){
		for(Block b : blist){
			for(Block c : list){
				if(b.getX() == c.getX() + Constants.SIZE && b.getY() == c.getY()){
					return false;
				}
			}
		}
		return true;
	}
	public boolean testDown(ArrayList<Block> blist){
		for(Block b : blist){
			for(Block c : list){
				if(b.getX() == c.getX() && b.getY() == c.getY() + Constants.SIZE){
					return false;
				}
			}
		}
		return true;
	}
	public int getType() {
		return type;
	}
}