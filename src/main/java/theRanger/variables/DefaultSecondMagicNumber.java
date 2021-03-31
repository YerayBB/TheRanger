package theRanger.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theRanger.cards.AbstractDefaultCard;

import static theRanger.DefaultMod.makeID;

public class DefaultSecondMagicNumber extends DynamicVariable {

    //For in-depth comments, check the other variable(DefaultCustomVariable). It's nearly identical.

    @Override
    public String key() {
        return makeID("M2");
        // This is what you put between "!!" in your card strings to actually display the number.
        // You can name this anything (no spaces), but please pre-phase it with your mod name as otherwise mod conflicts can occur.
        // Remember, we're using makeID so it automatically puts "theDefault:" (or, your id) before the name.
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if(card instanceof AbstractDefaultCard) return ((AbstractDefaultCard) card).isDefaultSecondMagicNumberModified;
        else return false;
    }

    @Override
    public int value(AbstractCard card) {
        if(card instanceof AbstractDefaultCard) return ((AbstractDefaultCard) card).defaultSecondMagicNumber;
        else return 0;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if(card instanceof AbstractDefaultCard)return ((AbstractDefaultCard) card).defaultBaseSecondMagicNumber;
        else return 0;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if(card instanceof AbstractDefaultCard) return ((AbstractDefaultCard) card).upgradedDefaultSecondMagicNumber;
        else return false;
    }
}