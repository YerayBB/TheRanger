package theRanger.actions.brown.generic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DamageCardAction extends AbstractGameAction {

    private AbstractCard card;

    public DamageCardAction(AbstractCard card, AbstractCreature target, AbstractCreature source){
        this.card = card;
        this.target = target;
        this.source = source;
        this.actionType = ActionType.DAMAGE;
    }


    @Override
    public void update() {
        if(!this.isDone){
            this.card.calculateCardDamage((AbstractMonster) this.target);
            addToTop(new DamageAction(this.target, new DamageInfo(this.source, this.card.damage)));
            this.isDone = true;
        }

    }
}
