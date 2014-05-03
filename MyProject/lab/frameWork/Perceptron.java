package frameWork;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Perceptron {
	public static void main(final String args[]) throws IOException, FileNotFoundException {
		final int[][][] is = new int[][][] {
		        {
		                {
		                        -1, -1
		                }, {
			                -1
		                }
		        }, {
		                {
		                        -1, 1
		                }, {
			                1
		                }
		        }, {
		                {
		                        1, 1
		                }, {
			                1
		                }
		        }, {
		                {
		                        1, 1
		                }, {
			                1
		                }
		        }
		};
		final Perceptron perceptron = new Perceptron(2, 2, 1);
		perceptron.input(is);
		perceptron.input(0, -1);
		perceptron.input(0, -1);
		final int[] ds = perceptron.output(new int[] {
		        -1, -1
		});
		for (final double d : ds) {
			System.out.println(d);
		}
	}
	
	private static final int MAX_TRY_COUNT = 100;
	private final List<int[]> eList;
	private final List<int[]> cList;
	private final Unit[] inputUnits;
	private final Unit[] middleUnits;
	private final Unit[] outputUnits;
	
	Perceptron(final int inputUnit, final int middleUnit, final int outputUnit) {
		eList = new LinkedList<int[]>();
		cList = new LinkedList<int[]>();
		inputUnits = new Unit[inputUnit];
		for (int i = 0; i < inputUnit; i++) {
			inputUnits[i] = new Unit(1);
		}
		middleUnits = new Unit[middleUnit];
		for (int i = 0; i < middleUnit; i++) {
			middleUnits[i] = new Unit(inputUnit);
		}
		outputUnits = new Unit[outputUnit];
		for (int i = 0; i < outputUnit; i++) {
			outputUnits[i] = new Unit(middleUnit);
		}
	}
	
	public void input(final int index, final int d) {
		this.inputUnits[index].input(index, d);
	}
	
	public int[] output(final int[] ts) {
		final int[] output = new int[outputUnits.length];
		for (int i = 0; i < inputUnits.length; i++) {
			final int a = inputUnits[i].output();
			for (int j = 0; j < middleUnits.length; j++) {
				middleUnits[i].input(i, a);
			}
			for (int j = 0; j < middleUnits.length; j++) {
				final int v = middleUnits[j].output();
				for (int k = 0; k < outputUnits.length; k++) {
					outputUnits[k].input(j, v);
				}
				for (int k = 0; k < outputUnits.length; k++) {
					int t = 0;
					if (k < ts.length) {
						t = ts[k];
					}
					output[k] = outputUnits[k].output();
					while (output[k] != t) {
						for (int l = 0; l < middleUnits.length; l++) {
							outputUnits[k].learning(l, 1 * (t - output[k]) * middleUnits[l].output());
						}
						output[k] = outputUnits[k].output();
					}
				}
			}
		}
		
		return output;
	}
	
	private static class Unit {
		private final int[] input;
		private final int[] weight;
		
		public Unit(final int inputUnit) {
			input = new int[inputUnit + 1];
			weight = new int[inputUnit + 1];
			if (input.length > 0) {
				input[0] = 1;
			}
			for (int i = 0; i < weight.length; i++) {
				weight[i] = 1;
			}
		}
		
		public void learning(final int index, final int deltaWeight) {
			weight[index] += deltaWeight;
			
		}
		
		public void input(final int index, final int d) {
			this.input[index] = d;
		}
		
		public int output() {
			int output = 0;
			for (int i = 0; i < input.length; i++) {
				output += input[i] * weight[i];
			}
			return output;
		}
	}
	
	public void input(final int[][][] is) {
		int maxLength = 0;
		final int inputSize = is.length;
		for (int i = 0; i < inputSize; i++) {
			final int[] e = new int[is[i][0].length + 1];
			e[0] = 1;
			for (int j = 1; j < e.length; j++) {
				e[j] = is[i][0][j - 1];
			}
			maxLength = Math.max(maxLength, e.length);
			eList.add(e);
			final int[] c = new int[is[i][1].length];
			for (int j = 0; j < c.length; j++) {
				c[j] = is[i][1][j];
			}
			cList.add(c);
		}
		
		// 学習
		int count = 0;
		final int[] w = new int[maxLength];
		/*
		 * 実行
		 */
		int run = 0;
		int run_p = 0;
		int num_p = 0;
		final int[] W_p = new int[maxLength];
		while (true) {
			count++;
			if (count > MAX_TRY_COUNT) {
				break;
			}
			else {
				// 訓練例の選択
				int k = (int) (new Random().nextDouble() * inputSize);
				if (k >= inputSize) {
					k = inputSize - 1;
				}
				// 出力の計算
				int s = 0;
				for (int i = 0; i < maxLength; i++) {
					s += w[i] * eList.get(k)[i];
				}
				// 正しい分類
				if (((s > 0) && (cList.get(k)[0] > 0)) || ((s < 0) && (cList.get(k)[0] < 0))) {
					run++;
					if (run > run_p) {
						int num = 0;
						for (int i = 0; i < inputSize; i++) {
							int t = 0;
							for (int j = 0; j < maxLength; j++) {
								t += w[j] * eList.get(i)[j];
							}
							if (((t > 0) && (cList.get(i)[0] > 0)) || ((t < 0) && (cList.get(i)[0] < 0))) {
								num++;
							}
						}
						if (num > num_p) {
							num_p = num;
							run_p = run;
							for (int i = 0; i < maxLength; i++) {
								W_p[i] = w[i];
							}
							if (num == inputSize) {
								break;
							}
						}
					}
				}
				// 誤った分類
				else {
					run = 0;
					for (int i = 0; i < maxLength; i++) {
						w[i] += cList.get(k)[0] * eList.get(k)[i];
					}
				}
			}
		}
		// 結果の出力
		printout(count, num_p, inputSize, maxLength, W_p);
	}
	
	@SuppressWarnings("nls")
	private void printout(final int n_tri, final int num, final int inputSize, final int maxLength, final int[] W_p) {
		System.out.print("重み\n");
		for (int i1 = 0; i1 < maxLength; i1++) {
			System.out.print("  " + W_p[i1]);
		}
		System.out.println();
		
		System.out.print("分類結果\n");
		for (int i1 = 0; i1 < inputSize; i1++) {
			int s = 0;
			for (int i2 = 0; i2 < maxLength; i2++) {
				s += eList.get(i1)[i2] * W_p[i2];
			}
			if (s > 0) {
				s = 1;
			}
			else {
				s = (s < 0) ? -1 : 0;
			}
			for (int i2 = 1; i2 < maxLength; i2++) {
				System.out.print(" " + eList.get(i1)[i2]);
			}
			System.out.println(" Cor " + cList.get(i1)[0] + " Res " + s);
		}
		
		if (inputSize == num) {
			System.out.print("  ！！すべてを分類（試行回数：" + n_tri + "）\n");
		}
		else {
			System.out.print("  ！！" + num + " 個を分類\n");
		}
		
	}
}