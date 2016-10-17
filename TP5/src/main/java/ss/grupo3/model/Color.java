package ss.grupo3.model;

public class Color {

	private int R;
	private int G;
	private int B;
	
	public Color(int R, int G, int B) {
		this.R = R;
		this.G = G;
		this.B = B;
	}
	
	public void change(int newR, int newG, int newB) {
		this.R = newR;
		this.G = newG;
		this.B = newB;
	}
	
	public String toString() {
		return R + " " + G + " " + B;
	}
}
