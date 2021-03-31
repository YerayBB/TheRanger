package theRanger.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theRanger.cards.AbstractInfusedCard;

import static theRanger.DefaultMod.makeID;

public class InfuseNumber extends DynamicVariable {

    @Override
    public String key() {
        return makeID("I");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if(card instanceof AbstractInfusedCard) return ((AbstractInfusedCard) card).isInfuseNumberModified;
        else return false;
    }

    @Override
    public int value(AbstractCard card) {
        if(card instanceof AbstractInfusedCard) return ((AbstractInfusedCard) card).infuseNumber;
        else return 0;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if(card instanceof AbstractInfusedCard)return ((AbstractInfusedCard) card).baseInfuseNumber;
        else return 0;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if(card instanceof AbstractInfusedCard) return ((AbstractInfusedCard) card).upgradedInfuseNumber;
        else return false;
    }
}