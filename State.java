public class State {

	private String type;
	private String Name;
	private int reward;
	// private Double Value;
	private Action max_action;
	public int row_pos, col_pos;
	private double qUval;
	private double qDval;
	private double qLval;
	private double qRval;
	public State(int i, int j, int k, Action max_action2, String string, String name2, double u, double d, double r, double l) {
		// TODO Auto-generated constructor stub
		this.row_pos = i;
		this.col_pos = j;
		this.setMax_action(max_action2);
		this.setReward(k);
		this.setName(name2);
		this.setType(string);
		this.setqUval(u);
		this.setqDval(d);
		this.setqLval(l);
		this.setqRval(r);
	}

	public int getReward() {
		return reward;
	}

	public void setReward(int reward) {
		this.reward = reward;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Action getMax_action() {
		return max_action;
	}

	public void setMax_action(Action max_action) {
		this.max_action = max_action;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public double getqUval() {
		return qUval;
	}

	public void setqUval(double qUval) {
		this.qUval = qUval;
	}

	public double getqDval() {
		return qDval;
	}

	public void setqDval(double qDval) {
		this.qDval = qDval;
	}

	public double getqLval() {
		return qLval;
	}

	public void setqLval(double qLval) {
		this.qLval = qLval;
	}

	public double getqRval() {
		return qRval;
	}

	public void setqRval(double qRval) {
		this.qRval = qRval;
	}

}
