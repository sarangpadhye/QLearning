import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.Map.Entry;

public class setup {

	private State[][] grid;
	private int m_row, m_col;
	private HashMap<State, ArrayList<Integer>> StateAction = new HashMap<State, ArrayList<Integer>>();

	private ArrayList<Action> AllActions = null;
	private Vector<State> States = new Vector<State>();
	private HashMap<State, ArrayList<State>> possibleStateOnAction = new HashMap<State, ArrayList<State>>();

	static final int ACTION_UP = 0;
	static final int ACTION_DOWN = 1;
	static final int ACTION_LEFT = 2;
	static final int ACTION_RIGHT = 3;
	Random r = new Random();

	public setup(int row, int col) {

		Action max_action = null;
		HashMap<Integer, Double> temp = new HashMap<Integer, Double>();
		// Save the Grid indexes
		this.m_row = row;
		this.m_col = col;

		// Create Grid
		grid = new State[m_row][m_col];

		// Set the Action
		// Actions = new ArrayList<Integer>();
		// Actions.add(ACTION_UP);
		// Actions.add(ACTION_DOWN);
		// Actions.add(ACTION_LEFT);
		// Actions.add(ACTION_RIGHT);

		int k = 0;
		for (int i = 0; i < m_row; i++) {
			for (int j = 0; j < m_col; j++) {

				String name = "S" + k;
				grid[i][j] = new State(i, j, 0, max_action, "Undecided", name,
						0.0, 0.0, 0.0, 0.0);

				k = k + 1;
			}
		}

		// Initialize the Actions

		AllActions = new ArrayList<Action>();

		AllActions.add(new Action(0, 0.8, 0.2, 0.0, 0.0));
		AllActions.add(new Action(1, 0.0, 1.0, 0.0, 0.0));
		AllActions.add(new Action(2, 0.0, 0.0, 1.0, 0.0));
		AllActions.add(new Action(3, 0.0, 0.2, 0.0, 0.8));

		System.out.println();

	}

	public void printall() {

		System.out.println(m_row);
		System.out.println(m_col);
		for (int i = 0; i < m_row; i++) {
			for (int j = 0; j < m_col; j++) {
				// System.out.println("State(" + i + "," + j + ")");
				// System.out.println(grid[i][j].getReward());
				// System.out.println(grid[i][j].getValue());
				// System.out.println(grid[i][j].getType());
				// System.out.println(grid[i][j].getMax_action());
				System.out.print("\t" + grid[i][j].getName());
			}
			System.out.println();
		}
	}

	public void printRewards() {
		for (int i = 0; i < m_row; i++) {
			for (int j = 0; j < m_col; j++) {

				System.out.print(grid[i][j].getReward() + "\t");

			}
			System.out.println();
		}
	}

	public void printState() {
		for (int i = 0; i < m_row; i++) {
			for (int j = 0; j < m_col; j++) {

				System.out.print(grid[i][j].getType() + "\t");

			}
			System.out.println();
		}
	}

	public void printMaxAction() {
		for (int i = 0; i < m_row; i++) {
			for (int j = 0; j < m_col; j++) {

				int action;
				String take_action = null;
				Action A = grid[i][j].getMax_action();
				if (A == null) {
					action = -1;
				} else
					action = A.action;

				if (action == 0)
					take_action = "UP";
				else if (action == 1)
					take_action = "DOWN";
				else if (action == 2)
					take_action = "LEFT";
				else if (action == 3)
					take_action = "RIGHT";
				else if (action == -1)
					take_action = "NA";
				if (i == 0 && j == 3)
					take_action = "STAY";

				System.out.print(take_action + "\t");

			}
			System.out.println();
		}
	}

	public void decideStates() {
		// TODO Auto-generated method stub
		for (int i = 0; i < m_row; i++) {
			for (int j = 0; j < m_col; j++) {
				// Set the Start State
				if (i == 4 && j == 0)
					grid[i][j].setType("S");
				// Set the Goal State
				else if (i == 0 && j == 3)
					grid[i][j].setType("G");

				// Set the Unreachable states
				else if (i == 3 && j == 1)

					grid[i][j].setType("UR");

				else if (i == 3 && j == 3)

					grid[i][j].setType("UR");

				else if (i == 1 && j == 1)
					grid[i][j].setType("P");

				else
					// Set rest Terminal states
					grid[i][j].setType("T");
			}

		}
	}

	// Reward Info
	// -1.0 for Terminal States
	// 0 for Unreacheable States
	// +10 for the Goal state
	// -50 for the Pit

	public void setZeroRewards() {
		//Set Zero Rewards Except for the Goal and Pitt State
		for (int i = 0; i < m_row; i++) {
			for (int j = 0; j < m_col; j++) {
				if (i == 0 && j == 3)
					grid[i][j].setReward(10);
				else if (i == 1 && j == 1)
					grid[i][j].setReward(-50);
				else
					grid[i][j].setReward(0);
			}

		}
	}

	public HashMap<Integer, Double> get_validProbOnly(Action A) {
		
		HashMap<Integer, Double> validProb = new HashMap<Integer, Double>();
		if (A.up_prob != 0.0) {
			validProb.put(0, A.up_prob);
		}
		if (A.down_prob != 0.0) {

			validProb.put(1, A.down_prob);
			// FinalMap.put(dir, validProb);
		}
		if (A.left_prob != 0.0) {

			validProb.put(2, A.left_prob);
			// FinalMap.put(dir, validProb);
		}
		if (A.right_prob != 0.0) {

			validProb.put(3, A.right_prob);
			// FinalMap.put(dir, validProb);
		}

		return validProb;
	}

	public Action getRandomisedAction(Action A) {
		//Get the Transitional Probabilities for a given action
		HashMap<Integer, Double> TransitMap = get_validProbOnly(A);

		java.util.Iterator<Entry<Integer, Double>> it = TransitMap.entrySet()
				.iterator();
		ArrayList<Integer> actions = new ArrayList<Integer>();
		ArrayList<Double> transitProb = new ArrayList<Double>();
		while (it.hasNext()) {
			Map.Entry pairs = it.next();
			int a = (int) pairs.getKey();
			Double prob_a = (Double) pairs.getValue();
			actions.add(a);
			transitProb.add(prob_a);

		}

		Random r = new Random();
		ArrayList<Double> transitProb1 = new ArrayList<Double>();
		double total = 0;
		for (int i = 0; i < transitProb.size(); i++) {
			total = total + transitProb.get(i);
			transitProb1.add(total);
		}

		double number = r.nextDouble();
		int SelectedAction = 0;
		//Chose a random action based on the transitional probabilities
		for (int i = 0; i < transitProb1.size(); i++) {
			if (number < transitProb1.get(i)) {
				SelectedAction = actions.get(i);
				break;
			}
		}

		Action action = returnActionObject(SelectedAction);
		return action;

	}

	public void setGivenRewards() {
		for (int i = 0; i < m_row; i++) {
			for (int j = 0; j < m_col; j++) {
				if (i == 4 && j == 0)
					grid[i][j].setReward(-1);

				// Set the Goal State
				else if (i == 0 && j == 3)
					grid[i][j].setReward(10);

				// Set the Unreachable states
				else if (i == 3 && j == 1)

					grid[i][j].setReward(0);

				else if (i == 3 && j == 3)

					grid[i][j].setReward(0);

				else if (i == 1 && j == 1)
					grid[i][j].setReward(-50);

				else
					// Set rest Terminal states
					grid[i][j].setReward(-1);

			}
		}

	}

	public void set_states() {
		State s;
		// Vector<State> States = new Vector<State>();
		for (int i = 0; i < m_row; i++) {
			for (int j = 0; j < m_col; j++) {

				s = grid[i][j];
				States.addElement(s);
			}
		}
	}

	public Vector<State> get_AllStates() {
		return States;

	}

	public HashMap<State, ArrayList<Integer>> get_StateAction() {

		return StateAction;

	}

	public ArrayList<Action> get_Actions() {
		return AllActions;
	}

	public State get_nextState(State S, Action A, int dir) {
		State nextState = null;

		// get row and col coordinate of the current state

		int row, col;

		row = S.row_pos;
		col = S.col_pos;

		// if up is the action
		if (dir == 0) {
			if (row == 0)
				nextState = null;
			else {
				nextState = grid[row - 1][col];
				if (nextState.getType().equals("UR"))
					nextState = null;
			}
			// if down is the action
		} else if (dir == 1) {
			if (row == 4)
				nextState = null;
			else {
				nextState = grid[row + 1][col];
				// ignore the unreachable state
				if (nextState.getType().equals("UR"))
					nextState = null;
			}
			// if left is the action
		} else if (dir == 2) {
			if (col == 0)
				nextState = null;
			else {
				nextState = grid[row][col - 1];
				// ignore the unreachable state
				if (nextState.getType().equals("UR"))
					nextState = null;
			}

			// if right is the action
		} else if (dir == 3) {
			if (col == 3)
				nextState = null;
			else {
				nextState = grid[row][col + 1];
				// ignore the ureachable state
				if (nextState.getType().equals("UR"))
					nextState = null;
			}

		}

		return nextState;
	}

	public double qValuesForS_atA(State S, int a) {
		double qval = 0.0;
		if (a == 0)
			qval = S.getqUval();
		else if (a == 1)
			qval = S.getqDval();
		else if (a == 2)
			qval = S.getqLval();
		else if (a == 3)
			qval = S.getqRval();

		return qval;
	}

	public int getRandomAction() {
		//Get Random Action for Exploration
		int raction = r.nextInt(4);
		if (raction == 0)
			return 0;
		else if (raction == 1)
			return 1;
		else if (raction == 2)
			return 2;
		else
			return 3;

	}

	public Action getEpsilonGreedyAction(State S, double epsilon) {

		//select action based on the e-greedy strategy
		double rnumber = r.nextInt(10) * 0.1;
		int returnAction;
		if (rnumber > epsilon)
			returnAction = get_maxAction(S);
		else {
			returnAction = getRandomAction();
		}

		Action action = returnActionObject(returnAction);

		return action;

	}

	private Action returnActionObject(int returnAction) {
		// TODO Auto-generated method stub
		ArrayList<Action> Actions = new ArrayList<Action>();
		Action action = null;
		Actions = get_Actions();
		for (int i = 0; i < Actions.size(); i++) {
			if (returnAction == Actions.get(i).action)
				action = Actions.get(i);
		}
		return action;

	}

	public double get_Qvalue(State S, Action A) {
		//return the Qvalues given a State and Action
		double Qval = 0.;
		// UP
		if (A.action == 0)
			Qval = S.getqUval();
		else if (A.action == 1)
			Qval = S.getqDval();
		else if (A.action == 2)
			Qval = S.getqLval();
		else if (A.action == 3)
			Qval = S.getqRval();
		return Qval;

	}

	public void set_Qvalue(double qval, State S, Action A) {
		//set the Qvalue for a given State and Action
		if (A.action == 0)
			S.setqUval(qval);
		else if (A.action == 1)
			S.setqDval(qval);
		else if (A.action == 2)
			S.setqLval(qval);
		else if (A.action == 3)
			S.setqRval(qval);
	}

	public ArrayList<Double> getAllQvalues(State S) {
		//get all the Q Values
		ArrayList<Double> AllQvalues = new ArrayList<Double>();
		AllQvalues.add(S.getqUval());
		AllQvalues.add(S.getqDval());
		AllQvalues.add(S.getqLval());
		AllQvalues.add(S.getqRval());

		return AllQvalues;
	}

	public Double get_maxQvalue(State S) {
		//return the Max Value For a particular state
		double maxValue = -1e30;

		ArrayList<Double> qvalues = getAllQvalues(S);
		for (int i = 0; i < qvalues.size(); i++) {
			if (qvalues.get(i) > maxValue)
				maxValue = qvalues.get(i);
		}
		return maxValue;

	}

	public int get_maxAction(State S) {
		//return the action having max Q value
		double maxValue = -1e30;
		ArrayList<Integer> indices = new ArrayList<Integer>();
		ArrayList<Double> MaxArray = new ArrayList<Double>();
		ArrayList<Double> qvalues = getAllQvalues(S);
		for (int i = 0; i < qvalues.size(); i++) {
			if (qvalues.get(i) > maxValue)
				maxValue = qvalues.get(i);

		}

		for (int j = 0; j < qvalues.size(); j++) {
			if (maxValue == qvalues.get(j)) {
				indices.add(j);
				MaxArray.add(maxValue);
			}
		}

		//Choose randomly if more than 1 actions have the same max Qvalue
		int randomAction = r.nextInt(indices.size());
		return randomAction;

	}

}
