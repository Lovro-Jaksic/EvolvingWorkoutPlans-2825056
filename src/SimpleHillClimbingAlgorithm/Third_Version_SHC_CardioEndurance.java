package SimpleHillClimbingAlgorithm;

import java.util.*;

public class Third_Version_SHC_CardioEndurance {
    // Array to store all the available exercises for beginner users (currently 20)
    static String[] beginnerHIITExercises = {
            "Jumping Jacks",
            "High Knees",
            "Butt Kicks",
            "Jump Rope",
            "Plank Jacks",
            "Pushups",
            "Mountain Climbers",
            "Skater Jumps",
            "Jump Squats",
            "Bear Crawls",
            "Burpees",
            "Alternating Lunges",
            "Box Jumps",
            "Tricep Dips",
            "Side Plank",
            "Dumbbell Thrusters",
            "Renegade Rows",
            "Russian Twists",
            "Medicine Ball Slams",
            "Battle Ropes"
    };

    // Array to store all the available exercises for intermediate users (currently 20)
    static String[] intermediateHIITExercises = {
            "Box jumps",
            "Battle ropes swings",
            "Jumping lunges",
            "Kettlebell swings",
            "Mountain climbers",
            "Plank jacks",
            "Burpees",
            "Jump squats",
            "Rowing machine sprints",
            "Medicine ball slams",
            "Bike sprints",
            "Jumping jacks",
            "High knees",
            "Jump rope",
            "Dumbbell thrusters",
            "Russian twists",
            "Treadmill sprints",
            "TRX pushups",
            "Sled pushes",
            "TRX rowing"
    };

    // Array to store all the available exercises for advanced users (currently 20)
    static String[] advancedHIITExercises = {
            "Burpee box jumps",
            "Battle ropes",
            "Jumping lunges",
            "Plyometric push-ups",
            "Kettlebell swings",
            "Medicine ball slams",
            "Sled pushes",
            "Rowing sprints",
            "Boxing bag drills",
            "Squat jumps with overhead press",
            "Sprint intervals on the treadmill",
            "Jump rope double unders",
            "Bear crawl with push-up",
            "Turkish get-ups",
            "Treadmill sprints",
            "Wall balls",
            "Assault bike sprints",
            "Broad jumps",
            "Barbell thrusters",
            "Power snatches"
    };

    // Exercise and rest time goals for each week, used for progressive overload, defined in ranges for all 3 plans
    // First 2 elements represent exercise time ranges, last 2 rest time ranges
    static int[][] beginnerPlan = {
            // Week 1
            {20, 30, 20, 40},
            // Week 2
            {25, 35, 20, 40},
            // Week 3
            {35, 40, 15, 35},
            // Week 4
            {35, 40, 10, 30}
    };

    static int[][] intermediatePlan = {
            // Week 1
            {25, 35, 15, 35},
            // Week 2
            {35, 40, 15, 35},
            // Week 3
            {45, 50, 10, 30},
            // Week 4
            {45, 50, 10, 25}
    };

    static int[][] advancedPlan = {
            // Week 1
            {40, 45, 10, 30},
            // Week 2
            {50, 55, 10, 25},
            // Week 3
            {55, 60, 10, 20},
            // Week 4
            {55, 60, 10, 15}
    };

    // Workouts per week - number of exercises per workout - total number of workouts over 4 weeks for all 3 levels - total genes in the chromosome
    static int[] beginnerConfig = {3, 4, 12, 144};
    static int[] intermediateConfig = {4, 5, 16, 240};
    static int[] advancedConfig = {5, 6, 20, 360};

    // The number of generations used for terminating the algorithm
    public static int maxGenerations = 1000000;
    public static int[] gridSearchGenerations = {1000, 10000, 100000, 500000, 1000000};

    public static void main(String[] args) {
        long totalDurationMillis = 0;
        long startTime = System.currentTimeMillis();
        int runCounter = 1;
        double [] allValues = new double[30];

        int bestNumOfGenerations = -1;
        double bestAverageFitness = Double.NEGATIVE_INFINITY;

        // Loop through all the configurations
//        for (int numOfGenerations : gridSearchGenerations) {
            double totalFitness = 0.0;
            // Loop 30 times and get the average
            for (int i = 0; i < 30; i++) {
                // Initialize the current best solution
                int[] bestSolution = generateRandomSolution(advancedConfig[3]);
                double bestFitness = calcFitness(bestSolution, advancedPlan, advancedConfig);

                // The current iteration
                int currentIteration = 0;

                // The algorithm loop
                while (currentIteration < maxGenerations) {
                    // Generate a new solution by changing one exercise
                    int[] newSolution = changeSetOfGenes(bestSolution, advancedPlan, advancedConfig, advancedHIITExercises);
                    double newFitness = calcFitness(newSolution, advancedPlan, advancedConfig);

                    // If the new solution is better, accept it as the current best solution
                    if (newFitness > bestFitness) {
                        bestSolution = newSolution;
                        bestFitness = newFitness;
                    }
                    // Increment the iteration count
                    currentIteration++;
                }
                allValues[i] = bestFitness;
//                System.out.println("Stopped after " + maxGenerations + " generations");

                // Print the best solution and its fitness
//                System.out.println("Best solution: " + Arrays.toString(bestSolution));
//                System.out.println("Fitness: " + bestFitness);

                // Print the workouts in readable format
//                solutionToString(bestSolution, intermediateHIITExercises, intermediateConfig);

                // Print the best solution and its fitness
//                System.out.println("Best solution: " + Arrays.toString(bestSolution));
//                totalFitness += bestFitness;
//                System.out.println("Run number: " + runCounter + ", Fitness: " + bestFitness);
//                runCounter++;
            }
//            double averageFitness = totalFitness / 30;
//            System.out.println("Average fitness after 30 runs: " + averageFitness +
//                    ", Number of generations: " + numOfGenerations);
//
//            if (averageFitness > bestAverageFitness) {
//                bestAverageFitness = averageFitness;
//                bestNumOfGenerations = numOfGenerations;
//            }
//        }
//        long endTime = System.currentTimeMillis();
//        //Calculate the duration
//        long durationMillis = endTime - startTime;
//        double durationSeconds = durationMillis / 1000.0;
//        double durationMinutes = durationSeconds / 60.0;
//        double durationHours = durationMinutes / 60.0;
//
//        // Print the durations
//        System.out.println("Duration of the run: " + durationMillis + " milliseconds");
//        System.out.println("Duration of the run: " + durationSeconds + " seconds");
//        System.out.println("Duration of the run: " + durationMinutes + " minutes");
//        System.out.println("Duration of the run: " + durationHours + " hours");
//
//        System.out.println("Best performing number of generations: " + bestNumOfGenerations
//                + ", Best average fitness: " + bestAverageFitness);

        int tracker = 1;
        for (double fitness : allValues) {
            System.out.println("Value no." + tracker + " " + fitness);
            tracker++;
        }
    }

    /**
     * Generates a random solution
     *
     * @return a random solution
     */
    public static int[] generateRandomSolution(int chromosomeLength) {
        int[] solution = new int[chromosomeLength];
        for (int gene = 0; gene < chromosomeLength; gene++) {
            // Randomly select an exercise
            Random random = new Random();
            int randomNumber;
            if (gene % 3 == 0) {
                // generate random number between 1 and 20 for index 0, 3, 6, 9, etc.
                randomNumber = random.nextInt(20) + 1;
                solution[gene] = randomNumber;
            } else if (gene % 3 == 1) {
                // generate random number between 20 and 60 for index 1, 4, 7, 10, etc.
                randomNumber = random.nextInt(41) + 20;
                solution[gene] = randomNumber;
            } else {
                // generate random number between 10 and 40 for index 2, 5, 8, 11, etc.
                randomNumber = random.nextInt(31) + 10;
                solution[gene] = randomNumber;
            }
        }
        return solution;
    }

    /**
     * Changes one exercise in the given solution randomly
     *
     * @param solution the solution to change
     * @return the new solution with one exercise changed
     */
    public static int[] changeSetOfGenes(int[] solution, int[][] userPlan, int[] userConfig, String[] exerciseList) {
        int[] newSolution = Arrays.copyOf(solution, solution.length);
        Random random = new Random();
        double randomNumber = random.nextDouble();

        int exerciseIndex = generateRandomExerciseIndex(userConfig[3]);

        if (exerciseIndex > (userConfig[3] - 9)) {
            exerciseIndex -= 9;
        }

        if (randomNumber < (1.0 / 3.0)) {
            newSolution = changeExercise(newSolution, exerciseList, exerciseIndex);
            newSolution = changeActiveTime(newSolution, userPlan, userConfig, exerciseIndex + 1);
            newSolution = changeRestTime(newSolution, userPlan, userConfig, exerciseIndex + 2);
            // Alter 6 genes
        } else if (randomNumber < (2.0 / 3.0)) {
            for (int i = 0; i < 6; i += 3) {
                newSolution = changeExercise(newSolution, exerciseList, exerciseIndex);
                newSolution = changeActiveTime(newSolution, userPlan, userConfig, exerciseIndex + i + 1);
                newSolution = changeRestTime(newSolution, userPlan, userConfig, exerciseIndex + i + 2);
            }
            // Alter 9 genes
        } else {
            for (int i = 0; i < 9; i += 3) {
                newSolution = changeExercise(newSolution, exerciseList, exerciseIndex);
                newSolution = changeActiveTime(newSolution, userPlan, userConfig, exerciseIndex + i + 1);
                newSolution = changeRestTime(newSolution, userPlan, userConfig, exerciseIndex + i + 2);
            }
        }
        return newSolution;
    }

    public static int[] changeExercise(int[] solution, String[] exerciseList, int exerciseIndex) {
        Random random = new Random();
        int newExercise = random.nextInt(exerciseList.length);
        solution[exerciseIndex] = newExercise;
        return solution;
    }

    public static int[] changeActiveTime(int[] solution, int[][] userPlan, int[] userConfig, int exerciseIndex) {
        // Randomly select the active time within the target range
        if (exerciseIndex < (userConfig[0] * userConfig[1] * 3)) {
            int minActiveTime = userPlan[0][0];
            int maxActiveTime = userPlan[0][1];
            Random randomActiveTime = new Random();
            solution[exerciseIndex] = randomActiveTime.nextInt(maxActiveTime - minActiveTime + 1) + minActiveTime;
        } else if (exerciseIndex > ((userConfig[0] * userConfig[1] * 3) - 1) && (exerciseIndex) < (userConfig[0] * 2 * userConfig[1] * 3)) {
            int minActiveTime = userPlan[1][0];
            int maxActiveTime = userPlan[1][1];
            Random randomActiveTime = new Random();
            solution[exerciseIndex] = randomActiveTime.nextInt(maxActiveTime - minActiveTime + 1) + minActiveTime;
        } else if (exerciseIndex > ((userConfig[0] * 2 * userConfig[1] * 3) - 1) && exerciseIndex < (userConfig[0] * 3 * userConfig[1] * 3)) {
            int minActiveTime = userPlan[2][0];
            int maxActiveTime = userPlan[2][1];
            Random randomActiveTime = new Random();
            solution[exerciseIndex] = randomActiveTime.nextInt(maxActiveTime - minActiveTime + 1) + minActiveTime;
        } else {
            int minActiveTime = userPlan[3][0];
            int maxActiveTime = userPlan[3][1];
            Random randomActiveTime = new Random();
            solution[exerciseIndex] = randomActiveTime.nextInt(maxActiveTime - minActiveTime + 1) + minActiveTime;
        }
        return solution;
    }

    public static int[] changeRestTime(int[] solution, int[][] userPlan, int[] userConfig, int exerciseIndex) {
        // Randomly select the rest time within the target range
        if (exerciseIndex < (userConfig[0] * userConfig[1] * 3)) {
            int minRestTime = userPlan[0][2];
            int maxRestTime = userPlan[0][3];
            Random randomRestTime = new Random();
            solution[exerciseIndex] = randomRestTime.nextInt(maxRestTime - minRestTime + 1) + minRestTime;
        } else if (exerciseIndex > ((userConfig[0] * userConfig[1] * 3) - 1) && exerciseIndex < (userConfig[0] * 2 * userConfig[1] * 3)) {
            int minRestTime = userPlan[1][2];
            int maxRestTime = userPlan[1][3];
            Random randomRestTime = new Random();
            solution[exerciseIndex] = randomRestTime.nextInt(maxRestTime - minRestTime + 1) + minRestTime;
        } else if (exerciseIndex > ((userConfig[0] * 2 * userConfig[1] * 3) - 1) && exerciseIndex < (userConfig[0] * 3 * userConfig[1] * 3)) {
            int minRestTime = userPlan[2][2];
            int maxRestTime = userPlan[2][3];
            Random randomRestTime = new Random();
            solution[exerciseIndex] = randomRestTime.nextInt(maxRestTime - minRestTime + 1) + minRestTime;
        } else {
            int minRestTime = userPlan[3][2];
            int maxRestTime = userPlan[3][3];
            Random randomRestTime = new Random();
            solution[exerciseIndex] = randomRestTime.nextInt(maxRestTime - minRestTime + 1) + minRestTime;
        }
        return solution;
    }

    /**
     * Method used for calculating the fitness of the solution
     * <p>
     * For now this will be based on the frequency of body parts trained in an
     * exercise and the amount of sets and reps trained per exercise
     * <p>
     * (sets and reps and later more difficult exercises)
     *
     * @param solution
     * @param userConfig
     * @param userPlan
     * @return fitness
     */
    public static double calcFitness(int[] solution, int[][] userPlan, int[] userConfig) {
        int fitness = 0;

        // For loop to iterate through a selected amount of workouts (12, 16, or 20)
        for (int workout = 0; workout <= solution.length / userConfig[2]; workout++) {

            // HashSet for checking whether exercises repeat in a workout
            Set<Integer> selectedExercises = new HashSet<>();

            int totalActiveTime = 0;
            int totalRestTime = 0;
            int averageActiveTime;
            int averageRestTime;

            // For loop to iterate through all the exercises per workout (4, 5, 6)
            for (int exercise = 0; exercise < userConfig[1]; exercise++) {
                // For loop to iterate through 3 genes per exercise
                for (int gene = 0; gene < 3; gene++) {
                    int chromosomePosition = gene + exercise * 3 + workout * userConfig[1];
                    int currentGene = solution[chromosomePosition];

                    // Check if there are any repeating exercises inside the workout
                    if (gene == 0) {
                        if (selectedExercises.contains(currentGene)) {
                            fitness -= 500;
                        }
                        // If not, add them to the set
                        if (!selectedExercises.contains(currentGene)) {
                            selectedExercises.add(currentGene);
                        }

                        // Calculate the fitness according to the ranges for exercise times
                    } else if (gene == 1) {
                        totalActiveTime += currentGene;

                        if (workout < userConfig[2] / 4) {
                            if (currentGene >= userPlan[0][0] && currentGene <= userPlan[0][1]) {
                                fitness += 1000;
                            } else {
                                fitness -= 500;
                            }
                        } else if (workout > (userConfig[2] / 4) - 1 && workout < (userConfig[2] / 4) * 2) {
                            if (currentGene >= userPlan[1][0] && currentGene <= userPlan[1][1]) {
                                fitness += 1000;
                            } else {
                                fitness -= 500;
                            }
                        } else if (workout > ((userConfig[2] / 4) * 2) - 1 && workout < (userConfig[2] / 4) * 3) {
                            if (currentGene >= userPlan[2][0] && currentGene <= userPlan[2][1]) {
                                fitness += 1000;
                            } else {
                                fitness -= 500;
                            }
                        } else {
                            if (currentGene >= userPlan[3][0] && currentGene <= userPlan[3][1]) {
                                fitness += 1000;
                            } else {
                                fitness -= 500;
                            }
                        }
                        // Calculate the fitness according to the ranges for rest times
                    } else {
                        totalRestTime += currentGene;

                        if (workout < userConfig[2] / 4) {
                            if (currentGene >= userPlan[0][2] && currentGene <= userPlan[0][3]) {
                                fitness += 1000;
                            } else {
                                fitness -= 500;
                            }
                        } else if (workout > (userConfig[2] / 4) - 1 && workout < (userConfig[2] / 4) * 2) {
                            if (currentGene >= userPlan[1][2] && currentGene <= userPlan[1][3]) {
                                fitness += 1000;
                            } else {
                                fitness -= 500;
                            }
                        } else if (workout > ((userConfig[2] / 4) * 2) - 1 && workout < (userConfig[2] / 4) * 3) {
                            if (currentGene >= userPlan[2][2] && currentGene <= userPlan[2][3]) {
                                fitness += 1000;
                            } else {
                                fitness -= 500;
                            }
                        } else {
                            if (currentGene >= userPlan[3][2] && currentGene <= userPlan[3][3]) {
                                fitness += 1000;
                            } else {
                                fitness -= 500;
                            }
                        }
                    }
                }
            }

            averageActiveTime = totalActiveTime / userConfig[1];
            averageRestTime = totalRestTime / userConfig[1];

            // Fitness calculations for the average active time in a single workout
            if (workout < userConfig[2] / 4) {
                if (averageActiveTime >= userPlan[0][0] && averageActiveTime <= userPlan[0][1]) {
                    fitness += 1000;
                } else {
                    if (averageActiveTime < userPlan[0][0]) {
                        fitness -= Math.abs(userPlan[0][0] - averageActiveTime) * 200;
                    } else {
                        fitness -= Math.abs(userPlan[0][1] - averageActiveTime) * 200;
                    }
                }
            } else if (workout > (userConfig[2] / 4) - 1 && workout < (userConfig[2] / 4) * 2) {
                if (averageActiveTime >= userPlan[1][0] && averageActiveTime <= userPlan[1][1]) {
                    fitness += 1000;
                } else {
                    if (averageActiveTime < userPlan[1][0]) {
                        fitness -= Math.abs(userPlan[1][0] - averageActiveTime) * 200;
                    } else {
                        fitness -= Math.abs(userPlan[1][1] - averageActiveTime) * 200;
                    }
                }
            } else if (workout > ((userConfig[2] / 4) * 2) - 1 && workout < (userConfig[2] / 4) * 3) {
                if (averageActiveTime >= userPlan[2][0] && averageActiveTime <= userPlan[2][1]) {
                    fitness += 1000;
                } else {
                    if (averageActiveTime < userPlan[2][0]) {
                        fitness -= Math.abs(userPlan[2][0] - averageActiveTime) * 200;
                    } else {
                        fitness -= Math.abs(userPlan[2][1] - averageActiveTime) * 200;
                    }
                }
            } else {
                if (averageActiveTime >= userPlan[3][0] && averageActiveTime <= userPlan[3][1]) {
                    fitness += 1000;
                } else {
                    if (averageActiveTime < userPlan[3][0]) {
                        fitness -= Math.abs(userPlan[3][0] - averageActiveTime) * 200;
                    } else {
                        fitness -= Math.abs(userPlan[3][1] - averageActiveTime) * 200;
                    }
                }
            }

            // Fitness calculations for the average rest time in a single workout
            if (workout < userConfig[2] / 4) {
                if (averageRestTime >= userPlan[0][2] && averageRestTime <= userPlan[0][3]) {
                    fitness += 1000;
                } else {
                    if (averageRestTime < userPlan[0][2]) {
                        fitness -= Math.abs(userPlan[0][2] - averageRestTime) * 200;
                    } else {
                        fitness -= Math.abs(userPlan[0][3] - averageRestTime) * 200;
                    }
                }
            } else if (workout > (userConfig[2] / 4) - 1 && workout < (userConfig[2] / 4) * 2) {
                if (averageRestTime >= userPlan[1][2] && averageRestTime <= userPlan[1][3]) {
                    fitness += 1000;
                } else {
                    if (averageRestTime < userPlan[1][2]) {
                        fitness -= Math.abs(userPlan[1][2] - averageRestTime) * 200;
                    } else {
                        fitness -= Math.abs(userPlan[1][2] - averageRestTime) * 200;
                    }
                }
            } else if (workout > ((userConfig[2] / 4) * 2) - 1 && workout < (userConfig[2] / 4) * 3) {
                if (averageRestTime >= userPlan[2][2] && averageRestTime <= userPlan[2][3]) {
                    fitness += 1000;
                } else {
                    if (averageRestTime < userPlan[2][2]) {
                        fitness -= Math.abs(userPlan[2][2] - averageRestTime) * 200;
                    } else {
                        fitness -= Math.abs(userPlan[2][3] - averageRestTime) * 200;
                    }
                }
            } else {
                if (averageRestTime >= userPlan[3][2] && averageRestTime <= userPlan[3][3]) {
                    fitness += 1000;
                } else {
                    if (averageRestTime < userPlan[3][2]) {
                        fitness -= Math.abs(userPlan[3][2] - averageRestTime) * 200;
                    } else {
                        fitness -= Math.abs(userPlan[3][3] - averageRestTime) * 200;
                    }
                }
            }
        }
        //Used to check how the fitness values look like for individuals
        System.out.println(fitness);
        return fitness;
    }

    public static int generateRandomExerciseIndex(int length) {
        Random random = new Random();
        int maxIndex = length / 3;
        int index = random.nextInt(maxIndex + 1);
        int number = index * 3;
        if (number == length) {
            return number - 3;
        }
        return number;
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
                " Optimized Cardio Endurance Schedule " +
                " ------------------------------------------------------->");

        // A for loop to print out individual exercises and their corresponding active and rest times
        for (int i = 0; i < solution.length; i += 3) {
            System.out.print(exerciseMap.get(solution[i]) + " -->" + " Active time: " +
                    solution[i + 1] + "sec Rest time: " + solution[i + 2]
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
