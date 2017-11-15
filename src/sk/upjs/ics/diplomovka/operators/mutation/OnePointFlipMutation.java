package sk.upjs.ics.diplomovka.operators.mutation;

import sk.upjs.ics.diplomovka.base.ChromosomeBase;
import sk.upjs.ics.diplomovka.base.MutationBase;
import sk.upjs.ics.diplomovka.utils.Utils;

public class OnePointFlipMutation extends MutationBase {

    public OnePointFlipMutation(double probability) {
        super(probability);
    }

    @Override
    public void doMutation(ChromosomeBase chromosome) {
        int position = Utils.randomInt(0, chromosome.getLength());
        doMutation(chromosome, position);
    }

    public void doMutation(ChromosomeBase chromosome, int position) {
        if (Math.random() < probability) {
            int newValue = Utils.randomInt(0, chromosome.getMaxValue());
            chromosome.setGeneOnPosition(position, newValue);
        }
    }
}
