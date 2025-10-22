package genetic.client;

import genetic.case_studies.cpu.*;
import genetic.core.Chromosome;
import genetic.core.Gene;
import genetic.engine.*;
import genetic.operators.crossover.CrossoverStrategy;
import genetic.operators.crossover.NPointCrossover;
import genetic.operators.mutation.MutationStrategy;
import genetic.operators.selection.SelectionStrategy;
import genetic.replacement.ReplacementStrategy;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== GA Library Demo: CPU Scheduling ===");

        List<Job> jobs = List.of(
                new Job("P1", 0, 5),
                new Job("P2", 1, 3),
                new Job("P3", 2, 6),
                new Job("P4", 4, 2),
                new Job("P5", 6, 8),
                new Job("P6", 7, 4),
                new Job("P7", 8, 5),
                new Job("P8", 10, 7),
                new Job("P9", 12, 3),
                new Job("P10", 13, 9),
                new Job("P11", 15, 2),
                new Job("P12", 16, 6)
        );


        final long seed = 42L; // reproducible

        // create initial population of job permutations using the same seed
        Random rng = new Random(seed);
        List<Chromosome> initialPopulation = createJobInitialPopulation(jobs, 50, rng);

        // Step 1: Configure parameters (chromosome length must equal number of jobs)
        GAParameters params = new GAParameters.Builder()
                .setPopulationSize(50)
                .setGenerations(10)
                .setChromosomeLength(jobs.size())
                .setFitnessThreshold(0.0236)
                .setCrossoverRate(0.9)
                .setMutationRate(0.25)
                .setRepresentationType("JOB")
                .setRandomSeed(seed)
                .setInitialPopulation(initialPopulation)
                .build();

        FitnessFunction fitnessFunction = new CPUJobScheduling();

        // Build and run GA with defaults
        GeneticAlgorithmEngine ga = new GeneticAlgorithmEngine.Builder(params, fitnessFunction).build();
        Chromosome best = ga.run();

    }

    private static List<Chromosome> createJobInitialPopulation(List<Job> jobs, int populationSize, Random rng) {
        List<Chromosome> pop = new ArrayList<>(populationSize);
        for (int i = 0; i < populationSize; i++) {
            List<Job> shuffled = new ArrayList<>(jobs);
            Collections.shuffle(shuffled, rng);
            List<Gene<?>> genes = shuffled.stream()
                    .map(JobGene::new)
                    .collect(Collectors.toList());
            Chromosome chrom = new Chromosome(genetic.core.RepresentationType.JOB, genes);
            pop.add(chrom);
        }
        return pop;
    }



    private static GeneticAlgorithmEngine buildEngineFromUserInput(FitnessFunction fitnessFunction) {

        System.out.println("=== GA Library Demo: CPU Scheduling ===");

        List<Job> jobs = List.of(
                new Job("P1", 0, 5),
                new Job("P2", 1, 3),
                new Job("P3", 2, 6),
                new Job("P4", 4, 2),
                new Job("P5", 6, 8),
                new Job("P6", 7, 4),
                new Job("P7", 8, 5),
                new Job("P8", 10, 7),
                new Job("P9", 12, 3),
                new Job("P10", 13, 9),
                new Job("P11", 15, 2),
                new Job("P12", 16, 6)
        );


        final long seed = 42L; // reproducible

        Random rng = new Random(seed);
        List<Chromosome> initialPopulation = createJobInitialPopulation(jobs, 50, rng);


        Scanner sc = new Scanner(System.in);


        System.out.print("Enter crossover rate (0.0 - 1.0): ");
        double crossoverRate = sc.nextDouble();
        System.out.print("Enter mutation rate (0.0 - 1.0): ");
        double mutationRate = sc.nextDouble();
        sc.nextLine(); // consume newline

        System.out.print("\nEnter fitness limit for early stopping  between (0, 1): ");
        double fitnessLimitStop =  sc.nextDouble();



        GAParameters params = new GAParameters.Builder()
                .setPopulationSize(50)
                .setGenerations(200)
                .setChromosomeLength(jobs.size())    // <- important
                .setCrossoverRate(crossoverRate)
                .setMutationRate(mutationRate)
                .setFitnessThreshold(fitnessLimitStop)
                .setRepresentationType("JOB")        // must match RepresentationType enum
                .setRandomSeed(seed)                 // same seed used to create initial pop
                .setInitialPopulation(initialPopulation)
                .build();

        // ==== User Selections ====
        System.out.println("\nChoose Selection Method:");
        System.out.println("1 - Tournament (default)");
        System.out.println("2 - Roulette");
        System.out.print("> ");
        int selChoice = sc.nextInt();

        System.out.println("\nChoose Crossover Method:");
        System.out.println("1 - Order (default)");
        System.out.println("2 - N-Point");
        System.out.println("3 - Uniform");
        System.out.print("> ");
        int crossChoice = sc.nextInt();

        // Only ask for number of points if user picked N-Point
        int nPoints = 0;
        if (crossChoice == 2) {
            System.out.print("Enter number of crossover points: ");
            nPoints = sc.nextInt();
        }

        System.out.println("\nChoose Mutation Method:");
        System.out.println("1 - Swap (default)");
        System.out.println("2 - BitFlip");
        System.out.println("3 - FloatingPoint");
        System.out.print("> ");
        int mutChoice = sc.nextInt();

        System.out.println("\nChoose Replacement Method:");
        System.out.println("1 - SteadyState (default)");
        System.out.println("2 - Elitism");
        System.out.println("3 - Generational");
        System.out.print("> ");
        int repChoice = sc.nextInt();

        // ==== Create Strategies via Factory ====
        SelectionStrategy selection = switch (selChoice) {
            case 1 -> OperatorFactory.createSelection("tournament");
            case 2 -> OperatorFactory.createSelection("roulette");
            default -> null;
        };

        CrossoverStrategy crossover = switch (crossChoice) {
            case 1 -> OperatorFactory.createCrossover("order", params);
            case 2 -> new NPointCrossover(nPoints, params.getCrossoverRate());
            case 3 -> OperatorFactory.createCrossover("uniform", params);
            default -> null;
        };

        MutationStrategy mutation = switch (mutChoice) {
            case 1 -> OperatorFactory.createMutation("swap", params);
            case 2 -> OperatorFactory.createMutation("bitflip", params);
            case 3 -> OperatorFactory.createMutation("floating", params);
            default -> null;
        };

        ReplacementStrategy replacement = switch (repChoice) {
            case 1 -> OperatorFactory.createReplacement("steady");
            case 2 -> OperatorFactory.createReplacement("elitism");
            case 3 -> OperatorFactory.createReplacement("generational");
            default -> null;
        };


        // === Combine everything ===
        GeneticAlgorithmEngine.Builder ga = new GeneticAlgorithmEngine.Builder(
                params,
                fitnessFunction
        )
                .withCrossover(crossover)
                .withMutation(mutation)
                .withSelection(selection)
                .withReplacement(replacement);


        System.out.println("\nRunning GA with your chosen configuration...");

        return ga.build();
    }

}


// --- Option B: Advanced setup (custom operators) ---
        /*
        SelectionStrategy selection = new TournamentSelection();
        CrossoverStrategy crossover = new NPointCrossover(2, params.getCrossoverRate());
        MutationStrategy mutation = new SwapMutation(params.getMutationRate());
        ReplacementStrategy replacement = new SteadyStateReplacement();

        GeneticAlgorithmEngine gaCustom = new GeneticAlgorithmEngine.Builder(params, fitnessFunction)
                .withSelection(selection)
                .withCrossover(crossover)
                .withMutation(mutation)
                .withReplacement(replacement)
                .build();

        gaCustom.run();
        */