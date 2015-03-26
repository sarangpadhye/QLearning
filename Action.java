
import java.util.ArrayList;
import java.util.HashMap;

public class Action {

	public int action;
	public Double up_prob;
	public Double down_prob;
	public Double left_prob;
	public Double right_prob;

	//Action class for every state
	//0 : UP
	//1: DOWN
	//2: LEFT
	//3: RIGHT
	public Action(int direction, double u, double d, double l, double r) {
		this.action = direction;
		this.up_prob = u;
		this.down_prob = d;
		this.left_prob = l;
		this.right_prob = r;

	}

}
