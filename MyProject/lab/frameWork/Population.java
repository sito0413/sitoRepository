package frameWork;

import java.util.ArrayList;
import java.util.Random;

public class Population {
	public static void main(final String[] args) {
		final Population population = new Population();
		population.add(createGean(5));
		population.add(createGean(122));
		population.add(createGean(2));
		population.add(createGean(3));
		population.add(createGean(17));
		while (population.evaluation(50000)) {
			population.genetic(2, 1);
		}
	}
	
	private static byte[] createGean(final int v) {
		final String string = Integer.toBinaryString(v);
		final int length = 4;
		final byte[] bs = new byte[length];
		for (int i = 0; i < length; i++) {
			bs[i] = 0;
		}
		for (int i = length - string.length(); i < string.length(); i++) {
			if ((i >= 0) && (i < length)) {
				bs[i] = Byte.parseByte(string.substring(i, i + 1));
			}
		}
		return bs;
	}
	
	private ArrayList<byte[]> list = new ArrayList<byte[]>();
	private final Random random = new Random();
	private int generation;
	
	public void add(final byte[] chromosome) {
		list.add(chromosome);
	}
	
	public void remove(final byte[] chromosome) {
		list.remove(chromosome);
	}
	
	@SuppressWarnings("nls")
	public boolean evaluation(final int limit) {
		
		final StringBuilder builder = new StringBuilder();
		builder.append((generation + 1)).append(" ");
		for (final byte[] bs : list) {
			builder.append('[');
			for (int i = 0; i < bs.length; i++) {
				builder.append(bs[i]);
			}
			builder.append(']');
		}
		System.out.println(builder);
		return generation++ < limit;
	}
	
	public void genetic(final int crossoverNum, final int potential) {
		selection();
		crossover(crossoverNum);
		mutation(crossoverNum, potential);
	}
	
	private void selection() {
		final ArrayList<byte[]> tmpList = list;
		byte[] lastChromosome = null;
		final int[] is = new int[tmpList.size()];
		for (int i = 0; i < tmpList.size(); i++) {
			int fitness = 0;
			for (int j = 0; j < tmpList.get(i).length; j++) {
				fitness += (int) (tmpList.get(i)[j] * Math.pow(2, j));
			}
			is[i] = fitness + 1;
		}
		list = new ArrayList<byte[]>(tmpList.size());
		while (list.size() != tmpList.size()) {
			int sumFitness = 0;
			for (int i = 0; i < tmpList.size(); i++) {
				final byte[] chromosome = tmpList.get(i);
				if ((lastChromosome == null) || !chromosome.equals(lastChromosome)) {
					sumFitness += is[i];
				}
			}
			if ((sumFitness - 1) > 0) {
				final int r = random.nextInt(sumFitness - 1);
				int s = 0;
				for (int i = 0; i < tmpList.size(); i++) {
					final byte[] chromosome = tmpList.get(i);
					if ((lastChromosome == null) || !chromosome.equals(lastChromosome)) {
						s += is[i];
						if (s > r) {
							if (lastChromosome == null) {
								lastChromosome = chromosome;
							}
							else {
								lastChromosome = null;
							}
							list.add(chromosome);
							break;
						}
					}
				}
			}
		}
	}
	
	private void crossover(final int num) {
		final int limit = list.size() - num;
		for (int i = 0; i < limit; i += num) {
			final byte[][] chromosomes = new byte[num][];
			for (int j = 0; j < num; j++) {
				chromosomes[j] = list.get(i + j);
			}
			
			final int crossoverPoint = random.nextInt(Math.min(chromosomes[0].length, chromosomes[1].length));
			final byte[] gene1 = chromosomes[0];
			final byte[] gene2 = chromosomes[1];
			for (int j = 0; j < crossoverPoint; j++) {
				final byte tmp = gene1[j];
				gene1[j] = gene2[j];
				gene2[j] = tmp;
			}
		}
	}
	
	private void mutation(final int num, final double potential) {
		final int limit = list.size() - num;
		for (int i = 0; i < limit; i++) {
			if (random.nextInt(100) <= potential) {
				int max = 0;
				for (int j = 0; j < list.get(i).length; j++) {
					max += j;
				}
				final int rdm = random.nextInt(max - 1);
				final int sum = 0;
				for (int j = 0; j < list.get(i).length; j++) {
					if (sum > rdm) {
						list.get(i)[j] ^= 1;
					}
				}
			}
		}
	}
}
