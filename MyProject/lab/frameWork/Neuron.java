package frameWork;

public class Neuron {
	public static void main(final String[] args) {
		final Neuron neuron = new Neuron();
		neuron.learning(0, 0, 0);
		neuron.learning(1, 0, 0);
		neuron.learning(0, 1, 0);
		neuron.learning(1, 1, 1);
		for (int i = 0; i < 10; i++) {
			System.out.println("@" + i);
			System.out.println(neuron.input(0, 0));
			System.out.println(neuron.input(1, 0));
			System.out.println(neuron.input(0, 1));
			System.out.println(neuron.input(1, 1));
		}
	}// Learning
	
	private final double theta;
	private double[] ws;
	private double[][] ls;
	private final double learningRate;
	
	public Neuron() {
		this.theta = 1.1;
		this.ws = new double[0];
		this.ls = new double[0][0];
		this.learningRate = 0.5;
	}
	
	private void learning(final double... ls) {
		
		final double[][] oldLs = this.ls;
		this.ls = new double[oldLs.length + 1][];
		for (int i = 0; i < oldLs.length; i++) {
			this.ls[i] = oldLs[i];
		}
		this.ls[oldLs.length] = ls;
	}
	
	private double input(final double... is) {
		double net = 0;
		if (is.length > ws.length) {
			final double[] oldWs = ws;
			ws = new double[is.length];
			for (int i = 0; i < oldWs.length; i++) {
				ws[i] = oldWs[i];
			}
		}
		final int limit = Math.min(is.length, ws.length);
		for (int i = 0; i < limit; i++) {
			net += (ws[i] * is[i]);
		}
		final double o = out(net);
		final int offsetLength = is.length + 1;
		for (int i = 0; i < ls.length; i++) {
			if (ls[i].length == offsetLength) {
				boolean flg = true;
				for (int j = 0; j < is.length; j++) {
					if (ls[i][j] != is[j]) {
						flg = false;
						break;
					}
				}
				
				if (flg) {
					final double f = learningRate * (ls[i][ls[i].length - 1] - o);
					for (int j = 0; j < is.length; j++) {
						ws[j] = ws[j] + (f * is[j]);
					}
				}
			}
		}
		return o;
	}
	
	private double out(final double net) {
		if ((net - theta) > 0) {
			return 1;
		}
		return 0;
		
	}
}
