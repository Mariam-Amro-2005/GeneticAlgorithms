package genetic.operators.selection;
import genetic.core.Chromosome;
import genetic.core.Population;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**

 Tournament Selection:

 Randomly selects a group of individuals (3) and chooses the one with the best fitness.
 */
public class TournamentSelection implements SelectionStrategy {

    private final int tournamentSize;

    public TournamentSelection() {
        this(3); // اThe default size of the tournament
    }

    public TournamentSelection(int tournamentSize) {
        this.tournamentSize = tournamentSize;
    }

    @Override
    public Chromosome selectParent(Population population) {
        Random random = new Random();
        List<Chromosome> competitors = new ArrayList<>();
        // Randomly select several chromosomes based on the tournament size.
        for (int i = 0; i < tournamentSize; i++) {
            int randomIndex = random.nextInt(population.size());
            competitors.add(population.getIndividuals().get(randomIndex));
        }

        // Return the chromosome with the highest fitness.
        Chromosome best = competitors.getFirst();
        for (Chromosome c : competitors) {
            if (c.getFitness() > best.getFitness()) {
                best = c;
            }
        }

        return best;
    }
}