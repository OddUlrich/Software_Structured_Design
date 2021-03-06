public class OrExp extends Bool {

	Bool left;
	Bool right;

	public OrExp(Bool left, Bool right) {
		this.left = left;
		this.right = right;
	}

	public boolean evaluate() {
		return left.evaluate() || right.evaluate();

	}

	public String show() {
		return "( " + left.show() + " || " + right.show() + " )";
	}
}