package sk.upjs.ics.diplomovka.operators.mutation;

import sk.upjs.ics.diplomovka.base.ChromosomeBase;
import sk.upjs.ics.diplomovka.base.MutationBase;
import sk.upjs.ics.diplomovka.utils.Utils;

public class UniformFlipMutation extends MutationBase {

    public UniformFlipMutation(double probability) {
        super(probability);
    }

    @Override
    public void doMutation(ChromosomeBase chromosome) {
        for (int i = 0; i < chromosome.getLength(); i++) {
            if (Math.random() < probability) {
                int newValue = Utils.randomInt(0, chromosome.getMaxValue());
                chromosome.setGeneOnPosition(i, newValue);
            }
        }
    }
}
