package MuscleBuilding_1st_Version;

import java.util.Random;

public class GeneticAlgorithm {
    private int populationSize;
    private double mutationRate;
    private double crossoverRate;
    private int elitismCount;
    protected int tournamentSize;

    public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount, int tournamentSize) {
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitismCount = elitismCount;
        this.tournamentSize = tournamentSize;
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
     * <p>
     * TODO: Calculate the fitness score based on how the workouts progress over the 4 weeks
     * (sets and reps and later more difficult exercises)
     * <p>
     * TODO: Calculate the fitness depending on whether the exercises repeat in a single workout
     *
     * @param individual
     * @return
     */
    public double calcFitness(Individual individual) {
        int fitness = 0;

        for (int workout = 0; workout <= individual.getChromosomeLength() / 18; workout++) {

            int chestTrained = 0;
            int backTrained = 0;
            int legsTrained = 0;
            int armsShouldersTrained = 0;
            int coreTrained = 0;

            for (int exercise = 0; exercise < 6; exercise++) {
                for (int gene = 0; gene < 3; gene++) {
                    int chromosomePosition = gene + exercise * 3 + workout * 6;
                    int currentGene = individual.getGene(chromosomePosition);

                    // Calculate the number of exercises per body part
                    if (gene == 0) {
                        if (currentGene <= 5) {
                            chestTrained++;
                        } else if (currentGene > 5 && currentGene <= 10) {
                            backTrained++;
                        } else if (currentGene > 10 && currentGene <= 15) {
                            legsTrained++;
                        } else if (currentGene > 15 && currentGene <= 20) {
                            armsShouldersTrained++;
                        } else {
                            coreTrained++;
                        }
                        //Calculate the fitness according to the sets
                    } else if (gene == 1) {
                        if (currentGene == 5) {
                            fitness += 25;
                        } else if (currentGene == 4) {
                            fitness += 50;
                        } else if (currentGene == 3) {
                            fitness += 100;
                        }
//                        else if (currentGene < 3) {
//                            fitness -= 25;
//                        } else if (currentGene > 5) {
//                            fitness -= 50 + (currentGene - 3);
//                        }
                        // Calculate the fitness according to the reps
                    } else {
//                        if (currentGene > 12) {
//                            fitness -= 50 + (currentGene - 12);
//                        } else
                        if (currentGene <= 12 && currentGene >= 10) {
                            fitness += 100;
                        } else if (currentGene < 10 && currentGene >= 8) {
                            fitness += 50;
                        } else if (currentGene < 8 && currentGene >= 6) {
                            fitness += 25;
                        }
//                        else if (currentGene < 6) {
//                            fitness -= 25;
//                        }
                    }
                }
            }
            if (chestTrained > 2) {
                fitness -= 100;
            } else if (backTrained > 2) {
                fitness -= 100;
            } else if (legsTrained > 2) {
                fitness -= 100;
            } else if (armsShouldersTrained > 2) {
                fitness -= 100;
            } else if (coreTrained > 2) {
                fitness -= 100;
            }
        }
        individual.setFitness(fitness);

        //Used to check how the fitness values look like for individuals
        System.out.println(fitness);

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
    public void evaluatePopulation(Population population) {
        double populationFitness = 0;

        for (Individual individual : population.getIndividuals()) {
            populationFitness += calcFitness(individual);
        }
        population.setPopulationFitness(populationFitness);
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
     * <p>
     * TODO: Implement multiple selection methods and test their performance
     *
     * @param population
     * @return
     */
    public Individual selectParent(Population population) {
        Population tournament = new Population(this.tournamentSize);

        //Shuffle the given population
        population.shuffle();
        for (int i = 0; i < this.tournamentSize; i++) {
            Individual tournamentIndividual = population.getIndividual(i);
            tournament.setIndividual(i, tournamentIndividual);
        }
        //Return the best individual
        return tournament.getFittest(0);
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
                Individual parent2 = this.selectParent(population);

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
                Individual parent2 = this.selectParent(population);

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
                Individual parent2 = this.selectParent(population);

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
                Individual parent2 = this.selectParent(population);

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
                Individual parent2 = this.selectParent(population);
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

                        // Keep track of the different mutations for exercises, sets, and reps
                        if (positionTracker == 0) {
                            newGene = random.nextInt(25) + 1;
                        } else if (positionTracker == 1) {
                            newGene = random.nextInt(3) + 3;
                        } else {
                            newGene = random.nextInt(3) + 10;
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
