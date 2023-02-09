package MuscleBuilding_1st_Version;

import java.util.HashMap;
import java.util.Random;

/**
 * An individual represents a single candidate solution
 *
 * Exercises
 *
 * Chest: Flat Barbell Bench Press (1), Incline Dumbbell Bench Press (2), Body-weight Dips (3),
 * Push-ups (4), Cable Chest Fly's (5)
 *
 * Back: Pull-ups (6), Deadlifts (7), Bent-Over Rows (8), Dumbbell Shrugs (9), Lat Pull-down (10)
 *
 * Legs: Front Squats (11), Romanian Deadlift (12), Lunges (13), Glute Bridge (14),
 * Bulgarian split squats (15)
 *
 * Arms & Shoulders: Barbell Bicep Curl (16), Incline Bicep Curl (17), Skull Crushers (18),
 * Cable Triceps Push-down (19), Seated Dumbbell Shoulder Press (20)
 *
 * Core: Russian twists (21), Plank hold (22), Leg raises (23), Toe touches crunches (24),
 * Cable rotations (25)
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
     * A constructor that will initialize a random set of exercises for the 4-week program
     *
     * In total there will be 252 genes to fit all the required criteria (14 workouts *
     * 6 exercises per workout * 3 genes per exercise (exerciseID, sets, reps))
     *
     *
     * @param chromosomeLength
     */
    public Individual(int chromosomeLength) {
        this.chromosome = new int[chromosomeLength];
        for (int gene = 0; gene <  chromosomeLength; gene++) {
            Random random = new Random();
            int randomNumber = random.nextInt(25) + 1;
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
    public void solutionToString() {
        // A hashmap to store the exercise IDs and the names of the exercises as values
        HashMap<Integer, String> exerciseMap = new HashMap<>();

        // Keeps track of the workouts for printing
        int counter = 1;

        // Array to store all the available exercises (currently 25)
        String [] exercises = {
                "Flat Barbell Bench Press",
                "Incline Dumbbell Bench Press",
                "Body-weight Dips",
                "Push-ups",
                "Cable Chest Fly's",
                "Pull-ups",
                "Deadlifts",
                "Bent-Over Rows",
                "Dumbbell Shrugs",
                "Lat Pull-down",
                "Front Squats",
                "Romanian Deadlift",
                "Lunges",
                "Glute Bridge",
                "Bulgarian split squats",
                "Barbell Bicep Curl",
                "Incline Bicep Curl",
                "Skull Crushers",
                "Cable Triceps Push-down",
                "Seated Dumbbell Shoulder Press",
                "Russian twists",
                "Plank hold",
                "Leg raises",
                "Toe touches crunches",
                "Cable rotations"
        };

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

            if ((i + 3) % 18 == 0) {
                System.out.println("<---------------------------------------- Workout " +
                        counter + " ---------------------------------------->");
                counter++;
            }
        }
    }
}
