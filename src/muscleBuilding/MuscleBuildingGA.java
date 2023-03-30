package muscleBuilding;

import java.util.HashMap;
import java.util.Map;

/**
 * Main class used for running the algorithm
 */

public class MuscleBuildingGA {

    // Array to store all the available beginner exercises (currently 25)
    static String[] beginnerExercises = {
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

    // Array to store all the available intermediate exercises (currently 25)
    static String[] intermediateExercises = {
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

    // Array to store all the available advanced exercises (currently 25)
    static String[] advancedExercises = {
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

    // Sets and reps goals for each week, used for progressive overload, defined in ranges for all 3 plans
    // 1st element represents sets, 2nd and 3rd elements represent target rep ranges
    static int[][] beginnerPlan = {
            // Week 1
            {2, 6, 8},
            // Week 2
            {3, 6, 8},
            // Week 3
            {3, 8, 10},
            // Week 4
            {3, 8, 10}
    };

    static int[][] intermediatePlan = {
            // Week 1
            {3, 6, 8},
            // Week 2
            {3, 8, 10},
            // Week 3
            {4, 8, 10},
            // Week 4
            {4, 10, 12}
    };

    static int[][] advancedPlan = {
            // Week 1
            {4, 8, 10},
            // Week 2
            {4, 8, 10},
            // Week 3
            {4, 10, 12},
            // Week 4
            {4, 10, 12}
    };

    /**
     * First 4 elements represent workouts per week - 5th element represents exercises per workout
     * 6th element represents total workouts in the 4 weeks - 7th element the total number of genes in the chromosome
     */
    static int[] beginnerConfig = {3, 3, 4, 4, 4, 14, 168};
    static int[] intermediateConfig = {4, 4, 5, 5, 5, 18, 270};
    static int[] advancedConfig = {5, 5, 6, 6, 6, 22, 396};

    // The number of generations used for terminating the algorithm
    public static int maxGenerations = 250;

    // Define the hyperparameter grid
    public static Map<String, double[]> hyperParameters = new HashMap<>();

    // Initialize the best result and its corresponding hyperparameters
    public static double bestFitness = Double.NEGATIVE_INFINITY;
    public static Map<String, Double> bestHyperparameters = new HashMap<>();
    public static int meanIndividualScore;

    public static void main(String[] args) {
        // Populate the hyperparameter grid
        hyperParameters.put("populationSize", new double[]{50, 100, 150, 200, 250});
        hyperParameters.put("mutationRate", new double[]{0.001, 0.005, 0.01, 0.02, 0.05});
        hyperParameters.put("crossoverRate", new double[]{0.5, 0.7, 0.8, 0.9, 0.95});
        hyperParameters.put("elitismCount", new double[]{1, 2, 4, 5, 8});
        hyperParameters.put("selectionSize", new double[]{1, 3, 5, 8, 10});

        long totalDurationMillis = 0;
        long startTime = System.currentTimeMillis();
        int runCounter = 1;

        // Loop through all possible combinations of hyperparameters
        for (double populationSize : hyperParameters.get("populationSize")) {
            for (double mutationRate : hyperParameters.get("mutationRate")) {
                for (double crossoverRate : hyperParameters.get("crossoverRate")) {
                    for (double elitismCount : hyperParameters.get("elitismCount")) {
                        for (double selectionSize : hyperParameters.get("selectionSize")) {
                            // Call the method for running the genetic algorithm
                            for (int i = 0; i < 30; i++) {
                                Map<String, Double> result = runGeneticAlgorithm((int) populationSize, mutationRate, crossoverRate,
                                        (int) elitismCount, (int) selectionSize);

                                System.out.println("Run number: " + runCounter +
                                        " Population size: " + populationSize +
                                        ", Mutation rate: " + mutationRate +
                                        ", Crossover rate: " + crossoverRate +
                                        ", Elitism count: " + elitismCount +
                                        ", Selection size: " + selectionSize +
                                        ", Fitness: " + result.get("fitness"));

                                runCounter++;

                                double currentFitness = result.get("fitness");
                                if (currentFitness > bestFitness) {
                                    bestFitness = currentFitness;
                                    bestHyperparameters.put("populationSize", result.get("populationSize"));
                                    bestHyperparameters.put("mutationRate", result.get("mutationRate"));
                                    bestHyperparameters.put("crossoverRate", result.get("crossoverRate"));
                                    bestHyperparameters.put("elitismCount", result.get("elitismCount"));
                                    bestHyperparameters.put("selectionSize", result.get("selectionSize"));
                                }
                            }
                        }
                    }
                }
            }
        }
        // Record the end time
        long endTime = System.currentTimeMillis();
        // Calculate the duration
        long durationMillis = endTime - startTime;
        double durationSeconds = durationMillis / 1000.0;
        double durationMinutes = durationSeconds / 60.0;
        double durationHours = durationMinutes / 60.0;

        // Print the durations
        System.out.println("Duration of the run: " + durationMillis + " milliseconds");
        System.out.println("Duration of the run: " + durationSeconds + " seconds");
        System.out.println("Duration of the run: " + durationMinutes + " minutes");
        System.out.println("Duration of the run: " + durationHours + " hours");

        // Print the best parameters
        System.out.println("Best Hyperparameters:");
        for (Map.Entry<String, Double> entry : bestHyperparameters.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // Print the best result and its corresponding hyperparameters
        System.out.println("Best fitness: " + bestFitness);
        System.out.println("Best hyperparameters: " + bestHyperparameters.toString());

//        long totalDurationMillis = 0;
//
//        for (int i = 0; i < 30; i++) {
//            // Record the start time
//            long startTime = System.currentTimeMillis();
//
//            System.out.println("Run number: " + (i + 1));
//
//            runGeneticAlgorithm((int) hyperParameters.get("populationSize")[0], hyperParameters.get("mutationRate")[0],
//                    hyperParameters.get("crossoverRate")[4], (int) hyperParameters.get("elitismCount")[1],
//                    (int) hyperParameters.get("selectionSize")[2]);
//
//            // Record the end time
//            long endTime = System.currentTimeMillis();
//
//            // Calculate the duration
//            long durationMillis = endTime - startTime;
//
//            // Calculate the duration and add it to the total duration
//            totalDurationMillis += durationMillis;
//
//            // Convert to seconds, minutes, and hours
//            double durationSeconds = durationMillis / 1000.0;
//            double durationMinutes = durationSeconds / 60.0;
//            double durationHours = durationMinutes / 60.0;
//
//            // Print the duration
//            System.out.println("Duration of the run: " + durationMillis + " milliseconds");
//            System.out.println("Duration of the run: " + durationSeconds + " seconds");
//            System.out.println("Duration of the run: " + durationMinutes + " minutes");
//            System.out.println("Duration of the run: " + durationHours + " hours");
//        }
//        System.out.println("Total duration of the 30 runs in seconds: " + (totalDurationMillis / 1000.0));
//        System.out.println("Total duration of the 30 runs in minutes: " + ((totalDurationMillis / 1000.0) / 60.0));
    }

    public static Map<String,Double> runGeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount, int selectionSize) {
        // Create a genetic algorithm object
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(populationSize, mutationRate, crossoverRate, elitismCount, selectionSize);

        // Initialize the population with a specified length of the individual's chromosomes
        Population population = geneticAlgorithm.initPopulation(advancedConfig[6]);

        // Evaluate the population
        geneticAlgorithm.evaluatePopulation(population, advancedPlan, advancedConfig);

        // Used for keeping track of the generations
        int generation = 1;

        /**
         * The evolution loop
         *
         * Terminates after it reaches the maximum number of generations
         */
        while (!geneticAlgorithm.isTerminationConditionMet(generation, maxGenerations)) {
            // Apply crossover
            population = geneticAlgorithm.uniformCrossover(population);

            // Apply mutation
            population = geneticAlgorithm.mutatePopulation(population);

            // Evaluate the population
            geneticAlgorithm.evaluatePopulation(population, advancedPlan, advancedConfig);

            // Increment the generation count
            generation++;
        }

        // Print the final solution
//        System.out.println("Stopped after " + maxGenerations + " generations");
//        System.out.println("Generation: " + generation + " --> Best workout: " + population.getFittest(0).toString());

        // Testing the new printing of the solution
//        Individual solution = population.getFittest(0);
//        solution.solutionToString(advancedExercises, advancedConfig);
        // Print the fitness of the individual
//        System.out.println(solution.getFitness());

        Map<String, Double> result = new HashMap<>();
        result.put("fitness", population.getFittest(0).getFitness());
        result.put("populationSize", (double) populationSize);
        result.put("mutationRate", mutationRate);
        result.put("crossoverRate", crossoverRate);
        result.put("elitismCount", (double) elitismCount);
        result.put("selectionSize", (double) selectionSize);

        return result;
    }
}
