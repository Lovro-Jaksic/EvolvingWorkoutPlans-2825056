package SimpleHillClimbingAlgorithm;

import java.util.*;

public class First_Version_SHC_MuscleBuilding {
    // Array to store all the available beginner exercises (currently 25)
    static String [] beginnerExercises = {
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
    static String [] intermediateExercises = {
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
    static String [] advancedExercises = {
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
            {3, 10, 12},
            // Week 2
            {3, 12, 14},
            // Week 3
            {4, 10, 12},
            // Week 4
            {4, 12, 14}
    };

    static int[][] advancedPlan = {
            // Week 1
            {4, 10, 12},
            // Week 2
            {4, 10, 12},
            // Week 3
            {4, 12, 14},
            // Week 4
            {4, 12, 14}
    };

    /**
     * First 4 elements represent workouts per week - 5th element represents exercises per workout
     * 6th element represents total workouts in the 4 weeks - 7th element the total number of genes in the chromosome
     */
    static int [] beginnerConfig = {3, 3, 4, 4, 4, 14, 168};
    static int [] intermediateConfig = {4, 4, 5, 5, 5, 18, 270};
    static int [] advancedConfig = {5, 5, 6, 6, 6, 22, 396};

    // The number of generations used for terminating the algorithm
    public static int maxGenerations = 500;

    public static void main(String[] args) {

        // Initialize the current best solution
        int[] bestSolution = generateRandomSolution(beginnerConfig[6]);
        System.out.println("Fully random solution");
        solutionToString(bestSolution,beginnerExercises, beginnerConfig);
        double bestFitness = calcFitness(bestSolution, beginnerPlan, beginnerConfig);

        // The current iteration
        int currentIteration = 0;

        // The algorithm loop
        while(currentIteration < maxGenerations) {
            // Generate a new solution by changing one exercise
            int[] newSolution = changeOneExercise(bestSolution, beginnerPlan, beginnerConfig, beginnerExercises);
            double newFitness = calcFitness(newSolution, beginnerPlan, beginnerConfig);

            // If the new solution is better, accept it as the current best solution
            if (newFitness > bestFitness) {
                bestSolution = newSolution;
                bestFitness = newFitness;
            }

            // Increment the iteration count
            currentIteration++;
        }

        System.out.println("Stopped after " + maxGenerations + " generations");

        // Print the best solution and its fitness
        System.out.println("Best solution: " + Arrays.toString(bestSolution));
        System.out.println("Fitness: " + bestFitness);

        // Print the workouts in readable format
        solutionToString(bestSolution, beginnerExercises, beginnerConfig);

    }

    /**
     * Generates a random solution
     * @return a random solution
     */
    public static int[] generateRandomSolution(int chromosomeLength) {
        int[] solution = new int[chromosomeLength];
        for (int gene = 0; gene < chromosomeLength; gene++) {
            // Randomly select an exercise
            Random random = new Random();
            int randomNumber;
            if (gene % 3 == 0) {
                // generate random number between 1 and 25 for index 0, 3, 6, 9, etc.
                randomNumber = random.nextInt(25) + 1;
                solution[gene] = randomNumber;
            } else if (gene % 3 == 1) {
                // generate random number between 2 and 4 for index 1, 4, 7, 10, etc.
                randomNumber = random.nextInt(3) + 2;
                solution[gene] = randomNumber;
            } else {
                // generate random number between 6 and 12 for index 2, 5, 8, 11, etc.
                randomNumber = random.nextInt(7) + 6;
                solution[gene] = randomNumber;
            }
        }
        return solution;
    }

    /**
     * Changes one exercise in the given solution randomly
     * @param solution the solution to change
     * @return the new solution with one exercise changed
     */
    public static int[] changeOneExercise(int[] solution, int[][] userPlan, int[] userConfig, String[] exerciseList) {
        int[] newSolution = Arrays.copyOf(solution, solution.length);
        Random random = new Random();
        // Randomly select an exercise to change
        int exerciseIndex = random.nextInt(userConfig[4] * userConfig[5]); // number of total workouts * exercises per workout
        // Get a random position of an exercise ID within the chromosome
        int startIndex = exerciseIndex * 3;
        // Randomly select a new exercise
        int newExercise = random.nextInt(exerciseList.length);
        newSolution[startIndex] = newExercise;

        // Randomly select the number of sets within the target range
        if ((startIndex + 1) < (userConfig[0] * userConfig[4] * 3)) {
            newSolution[startIndex+1] = userPlan[0][0];
        } else if ((startIndex + 1) > ((userConfig[0] * userConfig[4] * 3) - 1) && (startIndex + 1) < (userConfig[0] + (userConfig[1]) * userConfig[4] * 3)) {
            newSolution[startIndex+1] = userPlan[1][0];
        } else if ((startIndex + 1) > ((userConfig[0] + (userConfig[1]) * userConfig[4] * 3) - 1) && (startIndex + 1) < (userConfig[0] + (userConfig[1] + userConfig[2]) * userConfig[4] * 3)) {
            newSolution[startIndex+1] = userPlan[2][0];
        } else {
            newSolution[startIndex+1] = userPlan[3][0];
        }

        // Randomly select the number of reps within the target range
        if ((startIndex + 2) < (userConfig[0] * userConfig[4] * 3)) {
            int minReps = userPlan[0][1];
            int maxReps = userPlan[0][2];
            Random randomReps = new Random();
            newSolution[startIndex + 2] = randomReps.nextInt(maxReps - minReps + 1) + minReps;
        } else if ((startIndex + 2) > ((userConfig[0] * userConfig[4] * 3) - 1) && (startIndex + 2) < (userConfig[0] + (userConfig[1]) * userConfig[4] * 3) ) {
            int minReps = userPlan[1][1];
            int maxReps = userPlan[1][2];
            Random randomReps = new Random();
            newSolution[startIndex + 2] = randomReps.nextInt(maxReps - minReps + 1) + minReps;
        } else if ((startIndex + 2) > ((userConfig[0] + (userConfig[1]) * userConfig[4] * 3) - 1) && (startIndex + 2) < (userConfig[0] + (userConfig[1] + userConfig[2]) * userConfig[4] * 3)) {
            int minReps = userPlan[2][1];
            int maxReps = userPlan[2][2];
            Random randomReps = new Random();
            newSolution[startIndex + 2] = randomReps.nextInt(maxReps - minReps + 1) + minReps;
        } else {
            int minReps = userPlan[3][1];
            int maxReps = userPlan[3][2];
            Random randomReps = new Random();
            newSolution[startIndex + 2] = randomReps.nextInt(maxReps - minReps + 1) + minReps;
        }
        return newSolution;
    }

    /**
     * Method used for calculating the fitness of the solution
     */
    public static double calcFitness(int[] solution, int[][] userPlan, int[] userConfig) {
        int fitness = 0;

        // For loop to iterate through a selected amount of workouts (14, 18, or 22)
        for (int workout = 0; workout <= solution.length / userConfig[5]; workout++) {

            // HashSet for checking whether exercises repeat in a workout
            Set<Integer> selectedExercises = new HashSet<>();
            int chestTrained = 0;
            int backTrained = 0;
            int legsTrained = 0;
            int armsShouldersTrained = 0;
            int coreTrained = 0;

            int totalSets = 0;
            int totalReps = 0;
            int averageNumOfSets = 0;
            int averageNumOfReps = 0;

            // For loop to iterate through all the exercises per workout (4, 5, 6)
            for (int exercise = 0; exercise < userConfig[4]; exercise++) {
                // For loop to iterate through 3 genes per exercise
                for (int gene = 0; gene < 3; gene++) {
                    int chromosomePosition = gene + exercise * 3 + workout * userConfig[4];
                    int currentGene = solution[chromosomePosition];

                    // Calculate the number of exercises per body part
                    if (gene == 0) {
                        // Check if there are any repeating exercises inside the workout and decrement the fitness
                        if (selectedExercises.contains(currentGene)) {
                            fitness -= 500;
                        }
                        // If not, add them to the set
                        if (!selectedExercises.contains(currentGene)){
                            selectedExercises.add(currentGene);
                        }
                        if (currentGene <= 5) {
                            chestTrained++;
                        }
                        if (currentGene > 5 && currentGene <= 10) {
                            backTrained++;
                        }
                        if (currentGene > 10 && currentGene <= 15) {
                            legsTrained++;
                        }
                        if (currentGene > 15 && currentGene <= 20) {
                            armsShouldersTrained++;
                        }
                        if (currentGene > 20 && currentGene <= 25){
                            coreTrained++;
                        }
                        //Calculate the fitness according to the sets and progressive overload
                    } else if (gene == 1) {
                        totalSets += currentGene;

                        if (workout < userConfig[0]) {
                            if (currentGene == userPlan[0][0]) {
                                fitness += 1000;
                            } else {
                                fitness -= 500;
                            }
                        } else if (workout > (userConfig[0] - 1) && workout < (userConfig[0] + userConfig[1])) {
                            if (currentGene == userPlan[1][0]) {
                                fitness += 1000;
                            } else {
                                fitness -= 500;
                            }
                        } else if (workout > ((userConfig[0] + userConfig[1]) - 1) && workout < (userConfig[0] + userConfig[1] + userConfig[2])) {
                            if (currentGene == userPlan[2][0]) {
                                fitness += 1000;
                            } else {
                                fitness -= 500;
                            }
                        } else {
                            if (currentGene == userPlan[3][0]) {
                                fitness += 1000;
                            } else {
                                fitness -= 500;
                            }
                        }
                        // Calculate the fitness according to the reps and progressive overload
                    } else {
                        totalReps += currentGene;

                        if (workout < userConfig[0]) {
                            if (currentGene >= userPlan[0][1] && currentGene <= userPlan[0][2]) {
                                fitness += 1000;
                            } else {
                                fitness -= 500;
                            }
                        } else if (workout > (userConfig[0] - 1) && workout < (userConfig[0] + userConfig[1])) {
                            if (currentGene >= userPlan[1][1] && currentGene <= userPlan[1][2]) {
                                fitness += 1000;
                            } else {
                                fitness -= 500;
                            }
                        } else if (workout > ((userConfig[0] + userConfig[1]) - 1) && workout < (userConfig[0] + userConfig[1] + userConfig[2])) {
                            if (currentGene >= userPlan[2][1] && currentGene <= userPlan[2][2]) {
                                fitness += 1000;
                            } else {
                                fitness -= 500;
                            }
                        } else {
                            if (currentGene >= userPlan[3][1] && currentGene <= userPlan[3][2]) {
                                fitness += 1000;
                            } else {
                                fitness -= 500;
                            }
                        }
                    }
                }
            }

            averageNumOfSets = totalSets / userConfig[4];
            averageNumOfReps = totalReps / userConfig[4];

            // Fitness calculations for the average number of sets in a workout
            if (workout < userConfig[0]) {
                if (averageNumOfSets != userPlan[0][0]) {
                    fitness -= Math.abs(userPlan[0][0] - averageNumOfSets) * 200;
                } else {
                    fitness += 1000;
                }
            } else if (workout > (userConfig[0] - 1) && workout < (userConfig[0] + userConfig[1])) {
                if (averageNumOfSets != userPlan[1][0]) {
                    fitness -= Math.abs(userPlan[1][0] - averageNumOfSets) * 200;
                } else {
                    fitness += 1000;
                }
            } else if (workout > ((userConfig[0] + userConfig[1]) - 1) && workout < (userConfig[0] + userConfig[1] + userConfig[2])) {
                if (averageNumOfSets != userPlan[2][0]) {
                    fitness -= Math.abs(userPlan[2][0] - averageNumOfSets) * 200;
                } else {
                    fitness += 1000;
                }
            } else {
                if (averageNumOfSets != userPlan[3][0]) {
                    fitness -= Math.abs(userPlan[3][0] - averageNumOfSets) * 200;
                } else {
                    fitness += 1000;
                }
            }

            // Fitness calculations for the average number of reps in a workout
            if (workout < userConfig[0]) {
                if (averageNumOfReps >= userPlan[0][1] && averageNumOfReps <= userPlan[0][2]) {
                    fitness += 1000;
                } else {
                    if (averageNumOfReps < userPlan[0][1]) {
                        fitness -= Math.abs(userPlan[0][1] - averageNumOfReps) * 200;
                    } else {
                        fitness -= Math.abs(userPlan[0][2] - averageNumOfReps) * 200;
                    }
                }
            } else if (workout > (userConfig[0] - 1) && workout < (userConfig[0] + userConfig[1])) {
                if (averageNumOfReps >= userPlan[1][1] && averageNumOfReps <= userPlan[1][2]) {
                    fitness += 1000;
                } else {
                    if (averageNumOfReps < userPlan[1][1]) {
                        fitness -= Math.abs(userPlan[1][1] - averageNumOfReps) * 200;
                    } else {
                        fitness -= Math.abs(userPlan[1][2] - averageNumOfReps) * 200;
                    }
                }
            } else if (workout > ((userConfig[0] + userConfig[1]) - 1) && workout < (userConfig[0] + userConfig[1] + userConfig[2])) {
                if (averageNumOfReps >= userPlan[2][1] && averageNumOfReps <= userPlan[2][2]) {
                    fitness += 1000;
                } else {
                    if (averageNumOfReps < userPlan[2][1]) {
                        fitness -= Math.abs(userPlan[2][1] - averageNumOfReps) * 200;
                    } else {
                        fitness -= Math.abs(userPlan[2][2] - averageNumOfReps) * 200;
                    }
                }
            } else {
                if (averageNumOfReps >= userPlan[3][1] && averageNumOfReps <= userPlan[3][2]) {
                    fitness += 1000;
                } else {
                    if (averageNumOfReps < userPlan[3][1]) {
                        fitness -= Math.abs(userPlan[3][1] - averageNumOfReps) * 200;
                    } else {
                        fitness -= Math.abs(userPlan[3][2] - averageNumOfReps) * 200;
                    }
                }
            }

            if (chestTrained > 2) {
                fitness -= 500;
            } else if (backTrained > 2) {
                fitness -= 500;
            } else if (armsShouldersTrained > 2) {
                fitness -= 500;
            } else if (coreTrained > 2) {
                fitness -= 500;
            }
        }

        //Used to check how the fitness values look like for individuals
        System.out.println(fitness);
        return fitness;
    }

    /**
     * A method to output the solution in a more readable format with
     * words, and not an int chromosome
     */
    public static void solutionToString(int[] solution, String[] exercises, int[] userConfig) {
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
        for (int i = 0; i < solution.length; i += 3) {
            System.out.print(exerciseMap.get(solution[i]) + " -->" + " Sets: " +
                    solution[i + 1] + " Reps: " + solution[i + 2]);
            System.out.println();

            if ((i + 3) % (userConfig[4] * 3) == 0) {
                System.out.println("<---------------------------------------- Workout " +
                        counter + " ---------------------------------------->");
                counter++;
            }
        }
    }
}
