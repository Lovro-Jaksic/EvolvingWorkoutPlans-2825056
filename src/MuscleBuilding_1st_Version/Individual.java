package MuscleBuilding_1st_Version;

import java.util.HashMap;
import java.util.Random;

public class Individual {
    private int [] chromosome;
    private double fitness = -1;

    // Array to store all the available exercises (currently 25)
    String [] beginnerExercises = {
            // Chest
            "Push-ups",
            "Dumbbell Bench Press",
            "Dumbbell Flys",
            "Cable Crossovers",
            "Incline Dumbbell Bench Press",
            // Back
            "Lat Pull-downs",
            "Seated Cable Rows",
            "Assisted Pullups",
            "Dumbbell Shrugs",
            "Chest Supported Dumbbell Rows",
            // Legs
            "Walking Lunges",
            "Body-weight Squats",
            "Leg Press",
            "Glute Bridge",
            "Standing Calf Raises",
            // Arms & Shoulders
            "Bicep Curls with Dumbbells",
            "Dumbbell Lateral Raises",
            "Dumbbell Overhead Press",
            "Dumbbell Front Raises",
            "Resistance Band Face Pulls",
            // Core
            "Crunches",
            "Russian Twists",
            "Mountain Climbers",
            "Lying Leg Raises",
            "Dead Bug"
    };

    // Array to store all the available exercises (currently 25)
    String [] intermediateExercises = {
            // Chest
            "Dumbbell Bench Press with Resistance Bands",
            "Dips",
            "Incline Dumbbell Flys",
            "Resistance Band Pushups",
            "Cable Flys",
            // Back
            "Pullups",
            "Chinups",
            "Barbell Deadlifts",
            "Bent Over Dumbbell Rows",
            "Hyperextensions",
            // Legs
            "Hamstring Curls",
            "Dumbell Squats",
            "Weighted Step Ups",
            "Dumbbell Lunges",
            "Box Jumps",
            // Arms & Shoulders
            "Tricep Dips",
            "Barbell Bicep Curls",
            "Dumbbell Upright Rows",
            "Barbell Standing Press",
            "Dumbbell Hammer Curls",
            // Core
            "Russian Twists with a dumbbell",
            "Decline Situps",
            "Cable Woodchoppers",
            "Farmer's Carry with kettlebells",
            "Medicine Ball Slams"
    };

    // Array to store all the available exercises (currently 25)
    String [] advancedExercises = {
            // Chest
            "Plyometric Pushup",
            "Weighted Dips",
            "Decline Dumbbell Bench Press",
            "Band Resisted Dumbbell Flys",
            "Dumbbell Bench Press with chains and bands",
            // Back
            "T-Bar Rows",
            "Dumbbell Deadlifts",
            "Renegade Row",
            "Inverted Row",
            "Weighted Pullups",
            // Legs
            "Bulgarian Split Squats with Dumbbells",
            "Barbell Glute Bridges",
            "Barbell Front Squats",
            "Barbell Back Squats",
            "Goblet Squats with a Dumbbell",
            // Arms & Shoulders
            "Close Grip Benchpress",
            "Arnold Dumbbell Press",
            "Kettlebell Single-Arm Press",
            "Dumbbell Skull Crushers",
            "Dumbbell Single-Arm Preacher Curls",
            // Core
            "Hanging Leg Raises",
            "Ab-Wheel Rollout",
            "Medicine Ball V-Ups",
            "Swiss Ball Jacknife",
            "Swiss Ball Pike"
    };

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
                // generate random number between 1 and 5 for index 1, 4, 7, 10, etc.
                randomNumber = random.nextInt(5) + 1;
            } else {
                // generate random number between 8 and 14 for index 2, 5, 8, 11, etc.
                randomNumber = random.nextInt(9) + 6;
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
    public void solutionToString() {
        // A hashmap to store the exercise IDs and the names of the exercises as values
        HashMap<Integer, String> exerciseMap = new HashMap<>();

        // Keeps track of the workouts for printing
        int counter = 1;

        // Populate the hashmap with exercise IDs and exercise names
        for (int i = 0; i < beginnerExercises.length; i++) {
            exerciseMap.put(i + 1, beginnerExercises[i]);
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
