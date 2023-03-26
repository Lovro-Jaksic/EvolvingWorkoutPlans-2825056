package MuscleBuilding_1st_Version;

import java.util.HashMap;
import java.util.Random;

/**
 The Individual class represents an individual solution in the population of the genetic algorithm for the 4-week
 workout plan.
 Each Individual has a chromosome that contains genes representing exercises, sets and reps for
 each workout.
 The class provides methods to create and manipulate individuals, as well as to evaluate their fitness
 and output their solution in a readable format.
 */

public class Individual {
    private int [] chromosome;
    private double fitness = -1;

    /**
     * A constructor for the individual with a specific chromosome
     * @param chromosome - The individual's chromosome
     */
    public Individual(int [] chromosome) {
        this.chromosome = chromosome;
    }

    /**
     * A constructor that will initialize a random set of exercises for the 4-week programme
     *
     * In total there will be 252 genes to fit all the required criteria for the beginner plan (14 workouts *
     * 6 exercises per workout * 3 genes per exercise (exerciseID, sets, reps))
     *
     * Ranges for randomisation depend on the gene position
     *
     *
     * @param chromosomeLength
     */
    public Individual(int chromosomeLength) {
        this.chromosome = new int[chromosomeLength];
        for (int gene = 0; gene <  chromosomeLength; gene++) {
            Random random = new Random();
            int randomNumber;
            if (gene % 3 == 0) {
                // generate random number between 1 and 25 for index 0, 3, 6, 9, etc.
                randomNumber = random.nextInt(25) + 1;
            } else if (gene % 3 == 1) {
                // generate random number between 2 and 4 for index 1, 4, 7, 10, etc.
                randomNumber = random.nextInt(3) + 2;
            } else {
                // generate random number between 6 and 12 for index 2, 5, 8, 11, etc.
                randomNumber = random.nextInt(7) + 6;
            }
            this.setGene(gene, randomNumber);
        }
    }

    public int [] getChromosome() {
        return this.chromosome;
    }

    public int getChromosomeLength() {
        return this.chromosome.length;
    }

    public void setGene(int position, int gene) {
        this.chromosome[position] = gene;
    }

    public int getGene(int position) {
        return this.chromosome[position];
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getFitness() {
        return this.fitness;
    }

    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int gene = 0; gene < this.chromosome.length; gene++) {
            output.append(this.chromosome[gene]);
        }
        return output.toString();
    }

    /**
     * A method to output the solution in a more readable format with
     * words, and not an int chromosome
     */
    public void solutionToString(String[] exercises, int [] userConfig) {
        // A hashmap to store the exercise IDs and the names of the exercises as values
        HashMap<Integer, String> exerciseMap = new HashMap<>();

        // Keeps track of the workouts for printing
        int counter = 1;

        // Populate the hashmap with exercise IDs and exercise names
        for (int i = 0; i < exercises.length; i++) {
            exerciseMap.put(i + 1, exercises[i]);
        }

        System.out.println("<-------------------------------------------------------" +
                " Optimized Workout Schedule " +
                " ------------------------------------------------------->");

        // A for loop to print out individual exercises and their corresponding sets and reps
        for (int i = 0; i < this.chromosome.length; i += 3) {
            System.out.print(exerciseMap.get(this.chromosome[i]) + " -->" + " Sets: " +
                    this.chromosome[i + 1] + " Reps: " + this.chromosome[i + 2]);
            System.out.println();

            if ((i + 3) % (userConfig[4] * 3) == 0) {
                System.out.println("<---------------------------------------- Workout " +
                        counter + " ---------------------------------------->");
                counter++;
            }
        }
    }
}
