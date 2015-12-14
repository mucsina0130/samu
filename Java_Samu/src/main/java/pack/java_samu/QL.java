package pack.java_samu;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;


public class QL {
	public class Perceptron {
		public Perceptron(int nof, int... va_args) {
			n_layers = nof;
			units = new double[n_layers][];
			n_units = new int[n_layers];

			for (int i = 0; i < n_layers; ++i) {
				n_units[i] = va_args[i];
				units[i] = new double[n_units[i]];
			}
			weights = new double[n_layers - 1][][];
			Random random = new Random();

			for (int i = 1; i < n_layers; ++i) {
				weights[i - 1] = new double[n_units[i]][];
				for (int j = 0; j < n_units[i]; ++j) {
					weights[i - 1][j] = new double[n_units[i - 1]];

					for (int k = 0; k < n_units[i - 1]; ++k) {
						weights[i - 1][j][k] = randomgenerator(random);
					}
				}
			}
		}

		public double randomgenerator(Random random) {
			return -1 + (random.nextDouble() * 2);
		}

		public double sync(double x) {
			return 1.0 / (1.0 + Math.exp(-x));
		}

		public double op(double image[]) {
			units[0] = image;

			for (int i = 1; i < n_layers; ++i) {
				for (int j = 0; j < n_units[i]; ++j) {
					units[i][j] = 0.0;

					for (int k = 0; k < n_units[i - 1]; ++k) {
						units[i][j] += weights[i - 1][j][k] * units[i - 1][k];
					}

					units[i][j] = sync(units[i][j]);

				}
			}

			return sync(units[n_layers - 1][0]);

		}

		public void learn(double image[], double q, double prev_q) {
			double y[] = { q };

			learn(image, y);
		}

		public void learn(double image[], double y[]) {
			op(image);

			units[0] = image;

			double backs[][] = new double[n_layers - 1][];

			for (int i = 0; i < n_layers - 1; ++i) {
				backs[i] = new double[n_units[i + 1]];
			}

			int i = n_layers - 1;

			for (int j = 0; j < n_units[i]; ++j) {
				backs[i - 1][j] = sync(units[i][j]) * (1.0 - sync(units[i][j])) * (y[j] - units[i][j]);

				for (int k = 0; k < n_units[i - 1]; ++k) {
					weights[i - 1][j][k] += (0.2 * backs[i - 1][j] * units[i - 1][k]);
				}

			}

			for (int i1 = n_layers - 2; i1 > 0; --i1) {
				for (int j = 0; j < n_units[i1]; ++j) {

					double sum = 0.0;

					for (int l = 0; l < n_units[i1 + 1]; ++l) {
						sum += 0.19 * weights[i1][l][j] * backs[i1][l];
					}

					backs[i1 - 1][j] = sync(units[i1][j]) * (1.0 - sync(units[i1][j])) * sum;

					for (int k = 0; k < n_units[i1 - 1]; ++k) {
						weights[i1 - 1][j][k] += (0.19 * backs[i1 - 1][j] * units[i1 - 1][k]);
					}
				}
			}

		}

		public int n_layers;
		public int n_units[];
		public double units[][];
		public double weights[][][];
	}

	public QL(SPOTriplet triplet) {
		this();
	}

	public QL() {
		prev_image = new double[65536];

		prcps = new HashMap<SPOTriplet, Perceptron>();

		frqs = new HashMap<SPOTriplet, Map<String, Integer>>();
	}

	public double f(double u, int n) {

		if (n < N_e)
			return 1.0;
		else
			return u;
	}

	double max_ap_Q_sp_ap(double image[]) {
		double q_spap;
		double min_q_spap = -java.lang.Double.MAX_VALUE;

		for (Iterator<Entry<SPOTriplet, Perceptron>> it = prcps.entrySet().iterator(); it.hasNext();) {

			Entry<SPOTriplet, Perceptron> thisEntry = (Entry<SPOTriplet, Perceptron>) it.next();
			q_spap = ((Perceptron) thisEntry.getValue()).op(image);
			;
			if (q_spap > min_q_spap)
				min_q_spap = q_spap;
		}

		return min_q_spap;
	}

	SPOTriplet argmax_ap_f(String prg, double image[]) {
		double min_f = -java.lang.Double.MAX_VALUE;
		SPOTriplet ap = null;

		for (Iterator<Entry<SPOTriplet, Perceptron>> it = prcps.entrySet().iterator(); it.hasNext();) {
			Entry<SPOTriplet, Perceptron> thisEntry = (Entry<SPOTriplet, Perceptron>) it.next();
			double q_spap = ((Perceptron) thisEntry.getValue()).op(image);
			double explor = f(q_spap, frqs.get(thisEntry.getKey()).get(prg));

			if (explor >= min_f) {
				min_f = explor;
				ap = (SPOTriplet) thisEntry.getKey();
			}
		}

		return ap;
	}

	SPOTriplet op(SPOTriplet triplet, String prg, double image[]) {

		double jutalom = 3.0 * triplet.compare(prev_action) - 1.5;

		if (prcps.get(triplet) == null) {
			prcps.put(triplet, new Perceptron(3, 256 * 256, 80, 1));
		}

		SPOTriplet action = triplet;

		if (prev_jutalom > -java.lang.Double.MAX_VALUE && frqs.get(prev_action) != null) {
			frqs.get(prev_action).put(prev_state, frqs.get(prev_action).get(prev_state) + 1);

			double max_ap_q_sp_ap = max_ap_Q_sp_ap(image);

			for (int z = 0; z < 10; ++z) {
				double nn_q_s_a = (prcps.get(prev_action)).op(prev_image);

				double q_q_s_a = nn_q_s_a
						+ alpha(frqs.get(prev_action).get(prev_state)) * (jutalom + gamma * max_ap_q_sp_ap - nn_q_s_a);

				prcps.get(prev_action).learn(prev_image, q_q_s_a, nn_q_s_a);

				System.out.println("### " + (q_q_s_a - nn_q_s_a) + " " + q_q_s_a + " " + nn_q_s_a);
			}

			action = argmax_ap_f(prg, image);
		}

		prev_state = prg; // s <- s'
		prev_jutalom = jutalom; // r <- r'
		prev_action = action; // a <- a'

		prev_image = Arrays.copyOf(image, image.length);

		return action;
	}

	public double jutalom() {
		return prev_jutalom;
	}

	public double alpha(int n) {
		return 1.0 / (((double) n) + 1.0);
	}

	private QL(QL oth) {

	}

	private double gamma = .2;

	private Map<SPOTriplet, Perceptron> prcps;

	private Map<SPOTriplet, Map<String, Integer>> frqs;

	private SPOTriplet prev_action;
	private String prev_state;

	private double prev_jutalom = -java.lang.Double.MAX_VALUE;

	private double prev_image[];
	private static final int N_e = 10;

}
