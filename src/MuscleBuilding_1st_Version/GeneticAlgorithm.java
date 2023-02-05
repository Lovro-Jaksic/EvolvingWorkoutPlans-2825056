package MuscleBuilding_1st_Version;

public class GeneticAlgorithm {
    private int populationSize;
    private double mutationRate;
    private double crossoverRate;
    private int elitismCount;

    public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount) {
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitismCount = elitismCount;
    }

    public Population initPopulation(int chromosomeLength) {
        Population population = new Population(this.populationSize, chromosomeLength);
        return population;
    }

    public double calcFitness(Individual individual) {
        int fitness = 0;

        for (int workout = 0; workout <= individual.getChromosomeLength() / 18; workout ++) {

            int chestTrained = 0;
            int backTrained = 0;
            int legsTrained = 0;
            int armsShouldersTrained = 0;
            int coreTrained = 0;

            for (int exercise = 0; exercise < 6; exercise++) {
                for (int gene = 0; gene < 3; gene++) {
                    int chromosomePosition = gene + exercise*3 + workout*6;
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
                        if (currentGene > 3) {
                            fitness = currentGene - 3;
                        } else if (currentGene < 3) {
                            fitness = currentGene + 3;
                        } else {
                            fitness += 10;
                        }
                        // Calculate the fitness according to the reps
                    } else {
                        if (currentGene > 12) {
                            fitness -= currentGene - 12;
                        } else if (currentGene < 6) {
                            fitness -= currentGene;
                        } else if (currentGene < 8 && currentGene >= 6) {
                            fitness += 2;
                        } else if (currentGene < 10 && currentGene >= 8) {
                            fitness += 5;
                        } else if (currentGene <= 12 && currentGene >= 10) {
                            fitness++;
                        }
                    }
                }
            }
            if (chestTrained > 2) {
                fitness -= chestTrained - 2;
            } else if (backTrained > 2) {
                fitness -= backTrained - 2;
            } else if (legsTrained > 2) {
                fitness -= backTrained - 2;
            } else if (armsShouldersTrained > 2) {
                fitness -= backTrained - 2;
            } else if (coreTrained > 2) {
                fitness -= backTrained - 2;
            }
        }
        individual.setFitness(fitness);
        return fitness;
    }
}
