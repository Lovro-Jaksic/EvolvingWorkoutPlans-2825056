package cardioEndurance;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GeneticAlgorithm {
    private int populationSize;
    private double mutationRate;
    private double crossoverRate;
    private int elitismCount;
    protected int selectionSize;

    public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount, int selectionSize) {
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitismCount = elitismCount;
        this.selectionSize = selectionSize;
    }

    public Population initPopulation(int chromosomeLength) {
        Population population = new Population(this.populationSize, chromosomeLength);
        return population;
    }

    /**
     * Method used for calculating the fitness of the individual
     * <p>
     * For now this will be based on the frequency of body parts trained in an
     * exercise and the amount of sets and reps trained per exercise
     *
     * (sets and reps and later more difficult exercises)
     *
     * @param individual
     * @return
     */
    public double calcFitness(Individual individual, int[][] userPlan, int[] userConfig) {
        int fitness = 0;

        // For loop to iterate through a selected amount of workouts (12, 16, or 20) depending on the user plan
        for (int workout = 0; workout <= individual.getChromosomeLength() / userConfig[2]; workout++) {

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
                    int currentGene = individual.getGene(chromosomePosition);

                    // Check if there are any repeating exercises inside the workout
                    if (gene == 0) {
                        if (selectedExercises.contains(currentGene)) {
                            fitness -= 500;
                        }
                        // If not, add them to the set
                        if (!selectedExercises.contains(currentGene)){
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
        individual.setFitness(fitness);
        //Used to check how the fitness values look like for individuals
//        System.out.println(fitness);
        return fitness;
    }

    /**
     * Method to evaluate the fitness of the whole population
     * <p>
     * Loop over the individuals in the population and calculate the
     * fitness of each one, after which the algorithm will calculate
     * the fitness of the whole population
     *
     * @param population
     */
    public void evaluatePopulation(Population population, int[][] userPlan, int [] userLevel) {
        double populationFitness = 0;

        for (Individual individual : population.getIndividuals()) {
            populationFitness += calcFitness(individual, userPlan, userLevel);
        }
        population.setPopulationFitness(populationFitness);

        //Used to check how the fitness values look like for the population
//        System.out.println(populationFitness);
    }

    /**
     * Check if the population has reached the termination condition
     * <p>
     * If yes, stop the algorithm (this will be based on the set amount of generations)
     * <p>
     * TODO: Implement different termination conditions and test their performance
     *
     * @param generationsCount
     * @param maxGeneration
     * @return
     */
    public boolean isTerminationConditionMet(int generationsCount, int maxGeneration) {
        return (generationsCount > maxGeneration);
    }

    /**
     * A method used for selecting parents for crossover
     * based on tournament selection
     * <p>
     * Tournament selection works by choosing N random individuals
     * and then choosing the best of them
     *
     * @param population
     * @return
     */
    public Individual tournamentSelection(Population population) {
        // Initialize a tournament population
        Population tournament = new Population(this.selectionSize);

        //Shuffle the given population
        population.shuffle();
        // Select the tournament individuals
        for (int i = 0; i < this.selectionSize; i++) {
            Individual tournamentIndividual = population.getIndividual(i);
            tournament.setIndividual(i, tournamentIndividual);
        }
        // Return the fittest individual in the tournament population
        return tournament.getFittest(0);
    }

    /**
     * A method used for selecting parents for crossover
     * based on Roulette Wheel Selection
     * <p>
     * In this implementation, a random value is generated between 0 and the total fitness of the population
     *
     * The parent is then selected by iterating over the individuals and
     * subtracting their fitness from the random value
     *
     * The first individual whose accumulated fitness is greater than
     * or equal to the random value is selected as the parent.
     * <p>
     *
     * @param population
     * @return
     */
    public Individual rouletteWheelSelection(Population population) {
        // Calculate the total fitness of the population
        double populationFitness = population.getPopulationFitness();
//        System.out.println("Population fitness: " + populationFitness);

        // Generate a random value between 0 and the total fitness of the population
        double randomRoulettePosition = Math.random() * populationFitness;

        double accumulatedFitness = 0;
        // Select the parent by iterating over the individuals and subtracting their fitness from the random value
        for (Individual individual: population.getIndividuals()) {
            accumulatedFitness += individual.getFitness();
            if (accumulatedFitness >= randomRoulettePosition) {
                return individual;
            }
        }
        // Return the last individual in the population if no parent has been selected
        return population.getIndividual(population.size() - 1);
    }

    /**
     * A method used for selecting parents for crossover
     * based on Stochastic Universal Sampling (SUS) selection
     *
     * In this implementation, we first calculate the sum of the fitness of all individuals in the population.
     * Then, we calculate the average fitness by dividing the fitness sum by the population size.
     * After that, we choose a random number pointer between 0 and the average fitness.
     *
     * Next, we loop through the individuals in the population, and keep track
     * of the cumulative fitness by adding the fitness of each individual to the cumulative fitness.
     *
     * When the pointer becomes less than the cumulative fitness,
     * we have found the selected parent, and we return that individual.
     * @param population
     * @return
     */
    public Individual stochasticSelection(Population population) {
        int populationSize = population.size();
        // Calculate the total fitness of the population
        double populationFitness = population.getPopulationFitness();
        // Calculate the average fitness of the population
        double averageFitness = populationFitness / populationSize;

        // Randomly select a pointer that falls within the range of the average fitness
        double pointer = Math.random() * averageFitness;

        // Initialize an index to keep track of the selected individual
        int index = 0;
        double cumulativeFitness = population.getIndividual(index).getFitness();

        // Loop until the pointer falls within the cumulative fitness range of an individual and prevent going out of bounds
        while(pointer > cumulativeFitness && index < populationSize - 1) {
            index++;
            // Update the cumulative fitness with the fitness of the next individual
            cumulativeFitness += population.getIndividual(index).getFitness();
        }
        return population.getIndividual(index);
    }

    /**
     * A method used for selecting parents for crossover
     * based on Truncation selection
     *
     * The basic idea behind truncation selection is to select a portion of the fittest individuals
     * from the current population and then choose one of those individuals as a parent
     *
     * The selectionSize variable in this case is used as a percentage of the
     * population that should be used for the selection of the parent and
     * for the size of the new truncated population
     *
     * The fittest individuals from the current population are then selected and added to
     * the truncation population one by one.
     *
     * Finally, the fittest individual in the truncation population is selected and returned as the parent.
     *
     * However, if the same selectionSize is used the first parent in the
     * crossover will always be the same which is not really desirable,
     * so I don't know how useful this method will be
     *
     *
     * @param population
     * @return
     */
    public Individual truncationSelection(Population population) {
        int populationSize = population.size();
        // Calculate the truncation index, which is a fraction of the population size determined by the selection size parameter
        int truncationIndex = (int) (populationSize * this.selectionSize);

        // Ensure the truncationIndex is within bounds
        if (truncationIndex >= populationSize) {
            truncationIndex = populationSize - 1;
        }

        // Create a new population with the truncation size
        Population truncationPopulation = new Population(truncationIndex);

        // Loop through the truncation size
        for (int i = 0; i < truncationIndex; i++) {
            // Get the fittest individual in the population
            Individual truncationIndividual = population.getFittest(i);
            // Add the individual to the truncation population
            truncationPopulation.setIndividual(i, truncationIndividual);
        }

        // Return the fittest individual in the truncation population
        return truncationPopulation.getFittest(0);
    }

    /**
     * A method used for selecting parents for crossover
     * based on Linear Rank selection
     *
     * The basic idea behind rank selection is to assign a rank to each individual
     * in the population based on their fitness
     *
     * Individuals with higher fitness are assigned
     * lower ranks. The selection process then involves selecting individuals based on their rank
     *
     * This implementation of rank selection is based on a linear distribution of ranks,
     * where the probability of an individual being selected is proportional to their rank
     *
     * The rank size variable is used to determine the number of individuals to select based on rank
     *
     * Finally, a parent is selected from the rank population based on rank-based probability and returned
     *
     * The probability of an individual being selected is proportional to their rank-based probability
     *
     *
     * @param population
     * @return
     */
    public Individual linearRankSelection(Population population) {
        int populationSize = population.size();
        double [] cumulativeFitness = new double[populationSize];
        double totalCumulativeFitness = 0;

        Individual[] individuals = new Individual[populationSize];

        // Rank the individuals based on their fitness in ascending order
        for (int i = 0; i < populationSize; i++) {
            individuals[i] = population.getFittest(i);
        }

        // Calculate the cumulative fitness of each individual based on their rank
        for (int i = 0; i < populationSize; i++) {
            totalCumulativeFitness += (populationSize - 1);
            cumulativeFitness[i] = totalCumulativeFitness;
        }

        // Generate a random value between 0 and the total cumulative fitness of the population
        double randomLinearRank = Math.random() * totalCumulativeFitness;

        // Select the parent by iterating over the individuals and subtracting their cumulative fitness from the random value
        for (int i = 0; i < populationSize; i++) {
            if (cumulativeFitness[i] >= randomLinearRank) {
                return individuals[i];
            }
        }
        // Return the last individual in the population if no parent has been selected
        return population.getIndividual(population.size() - 1);
    }

    /**
     * A crossover method for the population using half of parent1's
     * genes and half of parent2's genes for the offspring
     *
     * If the crossover condition is not met, the fittest individual is
     * simply copied over to the new population
     *
     * TODO: Implement a heuristic crossover method?
     *
     * @param population
     * @return
     */
    public Population singlePointCrossover(Population population) {
        Population newPopulation = new Population(population.size());

        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            Individual parent1 = population.getFittest(populationIndex);

            if (this.crossoverRate > Math.random() && populationIndex >= this.elitismCount) {
                Individual offspring = new Individual(parent1.getChromosomeLength());
                Individual parent2 = this.tournamentSelection(population);

                int swapPoint = parent1.getChromosomeLength() / 2;

                for (int geneIndex = 0; geneIndex < parent1.getChromosomeLength(); geneIndex++) {
                    if (geneIndex < swapPoint) {
                        offspring.setGene(geneIndex, parent1.getGene(geneIndex));
                    } else {
                        offspring.setGene(geneIndex, parent2.getGene(geneIndex));
                    }
                }
                newPopulation.setIndividual(populationIndex, offspring);
            } else {
                newPopulation.setIndividual(populationIndex, parent1);
            }
        }
        return newPopulation;
    }

    /**
     * A crossover method for the population that will choose
     * a random single crossover point based on which the genes for the
     * offspring will be selected
     *
     * Genes before the crossover point will come from parent1
     * and the genes after the crossover point will come from parent2
     *
     * Better performance than single point crossover using half of both
     * parents' genes
     *
     * If the crossover condition is not met, the fittest individual is
     * simply copied over to the new population
     *
     * @param population
     * @return
     */
    public Population randomSinglePointCrossover(Population population) {
        Population newPopulation = new Population(population.size());

        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            Individual parent1 = population.getFittest(populationIndex);

            if (this.crossoverRate > Math.random() && populationIndex >= this.elitismCount) {
                Individual offspring = new Individual(parent1.getChromosomeLength());
                Individual parent2 = this.tournamentSelection(population);

                int swapPoint = (int) (Math.random() * parent1.getChromosomeLength());

                for (int geneIndex = 0; geneIndex < parent1.getChromosomeLength(); geneIndex++) {
                    if (geneIndex < swapPoint) {
                        offspring.setGene(geneIndex, parent1.getGene(geneIndex));
                    } else {
                        offspring.setGene(geneIndex, parent2.getGene(geneIndex));
                    }
                }
                newPopulation.setIndividual(populationIndex, offspring);
            } else {
                newPopulation.setIndividual(populationIndex, parent1);
            }
        }
        return newPopulation;
    }

    /**
     * A crossover method for the population that will choose
     * 2 random crossover points based on which the genes for the
     * offspring will be selected
     *
     * Genes between the 2 crossover points will come from parent2
     * and all the other genes will come from parent1
     *
     * If the crossover condition is not met, the fittest individual is
     * simply copied over to the new population
     *
     * @param population
     * @return
     */
    public Population randomTwoPointCrossover(Population population) {
        Population newPopulation = new Population(population.size());

        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            Individual parent1 = population.getFittest(populationIndex);

            if (this.crossoverRate > Math.random() && populationIndex >= this.elitismCount) {
                Individual offspring = new Individual(parent1.getChromosomeLength());
                Individual parent2 = this.tournamentSelection(population);

                int crossoverPoint1 = (int) (Math.random() * parent1.getChromosomeLength());
                int crossoverPoint2 = (int) (Math.random() * parent1.getChromosomeLength());

                if (crossoverPoint1 > crossoverPoint2) {
                    int temp = crossoverPoint1;
                    crossoverPoint1 = crossoverPoint2;
                    crossoverPoint2 = temp;
                }

                for (int geneIndex = 0; geneIndex < parent1.getChromosomeLength(); geneIndex++) {
                    if (geneIndex >= crossoverPoint1 && geneIndex <= crossoverPoint2) {
                        offspring.setGene(geneIndex, parent2.getGene(geneIndex));
                    } else {
                        offspring.setGene(geneIndex, parent1.getGene(geneIndex));
                    }
                }
                newPopulation.setIndividual(populationIndex, offspring);
            } else {
                newPopulation.setIndividual(populationIndex, parent1);
            }
        }
        return newPopulation;
    }

    /**
     * A crossover method for the population that will choose
     * the genes for the offspring based on parent's genes.
     *
     * Each offspring's gene will have 50% chance to come either
     * from paren1 or from parent2
     *
     * If the crossover condition is not met, the fittest individual is
     * simply copied over to the new population
     *
     * Best performance so far, but for some reason the number of
     * sets starts rising as the number of workouts rises
     *
     * @param population
     * @return
     */
    public Population uniformCrossover(Population population) {
        Population newPopulation = new Population(population.size());

        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            Individual parent1 = population.getFittest(populationIndex);

            if (this.crossoverRate > Math.random() && populationIndex >= this.elitismCount) {
                Individual offspring = new Individual(parent1.getChromosomeLength());
                Individual parent2 = this.tournamentSelection(population);

                for (int geneIndex = 0; geneIndex < parent1.getChromosomeLength(); geneIndex++) {
                    if (Math.random() <= 0.5) {
                        offspring.setGene(geneIndex, parent1.getGene(geneIndex));
                    } else {
                        offspring.setGene(geneIndex, parent2.getGene(geneIndex));
                    }
                }
                newPopulation.setIndividual(populationIndex, offspring);
            } else {
                newPopulation.setIndividual(populationIndex, parent1);
            }
        }
        return newPopulation;
    }

    /**
     * A crossover method based on arithmetic crossover
     *
     * The method combines the genes of the 2 selected parents
     * using a linear combination, with 'alpha' being the weighing factor
     *
     * If the crossover condition is not met, the fittest individual is
     * simply copied over to the new population
     *
     * Second-worst performance, but surprisingly the sets are in a good
     * range quite consistently, reps are usually below 10
     *
     * @param population
     * @return
     */
    public Population arithmeticCrossover(Population population) {
        Population newPopulation = new Population(population.size());

        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            Individual parent1 = population.getFittest(populationIndex);

            if (this.crossoverRate > Math.random() && populationIndex >= this.elitismCount) {
                Individual offspring = new Individual(parent1.getChromosomeLength());
                Individual parent2 = this.tournamentSelection(population);
                double alpha = Math.random();

                for (int geneIndex = 0; geneIndex < parent1.getChromosomeLength(); geneIndex++) {
                    offspring.setGene(geneIndex, (int) (alpha * parent1.getGene(geneIndex) + (1 - alpha) * parent2.getGene(geneIndex)));
                }
                newPopulation.setIndividual(populationIndex, offspring);
            } else {
                newPopulation.setIndividual(populationIndex, parent1);
            }
        }
        return newPopulation;
    }

    /**
     * A method used for applying mutation to the population
     *
     * In this case, uniform mutation
     *
     * TODO: Implement different mutation methods and test the results
     *
     * @param population
     * @return
     */
    public Population mutatePopulation(Population population) {
        int positionTracker = 0;
        // Initialize a new population
        Population newPopulation = new Population(this.populationSize);

        // Loop over the current population based on the fitness of the individuals
        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            Individual individual = population.getFittest(populationIndex);

            // Loop over individuals
            for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
                // Only mutate the individuals that are not elite
                if (populationIndex >= this.elitismCount) {
                    // If mutation occurs...
                    if (this.mutationRate > Math.random()) {
                        Random random = new Random();
                        int newGene = 1;

                        // Keep track of the different mutations for exercises, active times, and rest times
                        if (positionTracker == 0) {
                            newGene = random.nextInt(20) + 1;
                        } else if (positionTracker == 1) {
                            newGene = random.nextInt(41) + 20;
                        } else {
                            newGene = random.nextInt(31) + 10;
                        }
                        // Mutate the specific gene of the individual
                        individual.setGene(geneIndex, newGene);
                    }
                }
                // Position tracker is used to know what the gene in the loop represents
                if (positionTracker == 2) {
                    positionTracker = 0;
                } else {
                    positionTracker++;
                }
            }
            // Add the individual to the new population
            newPopulation.setIndividual(populationIndex, individual);
        }
        // Return the mutated population
        return newPopulation;
    }
}
