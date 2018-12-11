package hw4;

import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Disk Scheduling simulator. Cylinders of 5000 with 50 requests. Reads initial
 * head position from the user. calculates and reports the total amount of head
 * movement required for FCFS, CLOOK and SSTF algorithms.
 *
 */
public class Application {
	static final int CYLINDERS = 5000;
	static final int REQUESTS = 50;

	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		Random rand = new Random();
		int headPos = 0;
		int totalMovements;
		ArrayList<Integer> list = new ArrayList<Integer>(REQUESTS);

		// random requests
		for (int i = 0; i < REQUESTS; i++) {
			list.add(rand.nextInt(CYLINDERS));
		}

		// Read in initial head position from user
		System.out.println("Enter initial head position");
		headPos = keyboard.nextInt();

		// start from the rear of the cylinder if given an error head position
		if (headPos > CYLINDERS - 1)
			headPos = CYLINDERS - 1;
		if (headPos < 0)
			headPos = 0;

		// First Come First Serve
		totalMovements = fcfs(headPos, list);
		System.out.println("Total seek time with FCFS is " + totalMovements);
		System.out.println();
		System.out.println();

		// C-LOOK
		totalMovements = c_LOOK(headPos, list);
		System.out.println("Total seek time with C-LOOk is " + totalMovements);
		System.out.println();
		System.out.println();

		// Shortest Seek Time First
		totalMovements = sstf(headPos, list);
		System.out.println("Total seek time with SSTF is " + totalMovements);

		// close the scanner object
		keyboard.close();
	}

	/**
	 * fcfs services the first requests on the list
	 * 
	 * @param head initial head position
	 * @param list the array of requests to be processed
	 * @return int the seek time of the algorithm
	 */
	public static int fcfs(int head, ArrayList<Integer> list) {
		int total = 0;
		int movement = 0;

		System.out.println("First Come First Serve:");

		for (int i = 0; i < REQUESTS; i++) {
			movement = Math.abs(head - list.get(i));
			total += movement;
			System.out.println("Move head from " + head + " to " + list.get(i) + " with seek " + movement);
			head = list.get(i);
		}
		return total;
	}

	/**
	 * sort the array, find distance between head position and maximum and find
	 * distance between maximum and minimum, and adds them together to get total
	 * distance traveled by the head
	 * 
	 * @param head initial head position
	 * @param list the array of requests to be processed
	 * @return int the seek time of the algorithm
	 */
	public static int c_LOOK(int head, ArrayList<Integer> list) {
		int total = 0;

		System.out.println("C-LOOK:");
		// sort the requests
		Collections.sort(list);

		int difference = 0;
		// get movements till the max reuquest
		for (int i = head + 1; i < list.get(REQUESTS - 1); i++) {
			if (list.contains(i)) {
				difference = Math.abs(head - i);
				total += difference;
				head = i;
			}

		}
		// get movements from the first request
		for (int i = 0; i < head; i++) {
			if (list.contains(i)) {
				difference = Math.abs(i - head);
				total += difference;
				head = i;
			}
		}

		return total;
	}

	/**
	 * Shortest-Seek-Time-First (SSTF) takes the current head position, and adds the
	 * position closest to the current head. This new position now becomes the head,
	 * then this system repeats.
	 * 
	 * @param head initial head position
	 * @param list array of requests to be processed
	 * @return int seek time of the algorithm
	 */
	public static int sstf(int head, ArrayList<Integer> list) {
		int total = 0;

		System.out.println("Shortest Seek Time First:");

		for (int i = 0; i < REQUESTS; i++) {
			int index = 0;
			int val = Math.abs(head - list.get(index));
			for (int j = 1; j < list.size(); j++) {
				if (Math.abs(head - list.get(j)) < val) {
					val = head - list.get(j);
					index = j;
				}
			}
			System.out.println("Move head from " + head + " to " + list.get(index) + " with seek "
					+ Math.abs(list.get(index) - head));
			total += Math.abs(list.get(index) - head);
			head = list.remove(index);
		}
		return total;
	}
}