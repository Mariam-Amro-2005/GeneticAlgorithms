package genetic.engine;

import genetic.core.RepresentationType;

public class GAParameters {
    private int populationSize;
    private int generations;
    private int chromosomeLength;
    private double crossoverRate;
    private double mutationRate;
    private RepresentationType representationType;

    // Getters & setters
    public int getPopulationSize() { return populationSize; }
    public void setPopulationSize(int populationSize) { this.populationSize = populationSize; }

    public int getGenerations() { return generations; }
    public void setGenerations(int generations) { this.generations = generations; }

    public int getChromosomeLength() { return chromosomeLength; }
    public void setChromosomeLength(int chromosomeLength) { this.chromosomeLength = chromosomeLength; }

    public double getCrossoverRate() { return crossoverRate; }
    public void setCrossoverRate(double crossoverRate) { this.crossoverRate = crossoverRate; }

    public double getMutationRate() { return mutationRate; }
    public void setMutationRate(double mutationRate) { this.mutationRate = mutationRate; }

    public RepresentationType getRepresentationType() { return representationType; }
    public void setRepresentationType(String representationType) { this.representationType = RepresentationType.valueOf(representationType); }
}
