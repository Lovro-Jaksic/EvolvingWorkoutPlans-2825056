package cardioEndurance;

import java.util.HashMap;
import java.util.Random;

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
     * In total there will be 144 genes to fit all the required criteria for the beginner plan (12 workouts *
     * 4 exercises per workout * 3 genes per exercise (exerciseID, active time, rest time))
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
                // generate random number between 1 and 20 for index 0, 3, 6, 9, etc.
                randomNumber = random.nextInt(20) + 1;
            } else if (gene % 3 == 1) {
                // generate random number between 20 and 60 for index 1, 4, 7, 10, etc.
                randomNumber = random.nextInt(41) + 20;
            } else {
                // generate random number between 10 and 30 for index 2, 5, 8, 11, etc.
                randomNumber = random.nextInt(21) + 10;
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
    public void solutionToString(String[] exercises, int[] userConfig) {
        // A hashmap to store the exercise IDs and the names of the exercises as values
        HashMap<Integer, String> exerciseMap = new HashMap<>();

        // Keeps track of the workouts for printing
        int counter = 1;

        // Populate the hashmap with exercise IDs and exercise names
        for (int i = 0; i < exercises.length; i++) {
            exerciseMap.put(i + 1, exercises[i]);
        }

        System.out.println("<-------------------------------------------------------" +
                " Optimized Cardio Endurance Schedule " +
                " ------------------------------------------------------->");

        // A for loop to print out individual exercises and their corresponding active and rest times
        for (int i = 0; i < this.chromosome.length; i += 3) {
            System.out.print(exerciseMap.get(this.chromosome[i]) + " -->" + " Active time: " +
                    this.chromosome[i + 1] + "sec Rest time: " + this.chromosome[i + 2]
                    + " sec");
            System.out.println();

            if ((i + 3) % (userConfig[1] * 3) == 0) {
                System.out.println("<---------------------------------------- Workout " +
                        counter + " ---------------------------------------->");
                counter++;
            }
        }
    }
}
