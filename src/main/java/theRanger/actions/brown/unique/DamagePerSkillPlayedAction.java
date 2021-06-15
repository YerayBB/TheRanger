package theRanger.actions.brown.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class DamagePerSkillPlayedAction extends AbstractGameAction{
    private DamageInfo info;

    public DamagePerSkillPlayedAction(AbstractCreature target, DamageInfo info, AbstractGameAction.AttackEffect effect) {
        this.info = info;
        this.setValues(target, info);
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.attackEffect = effect;
    }

    @Override
    public void update() {
        this.isDone = true;
        if (this.target != null && this.target.currentHealth > 0) {
            int count = 0;
            Iterator var2 = AbstractDungeon.actionManager.cardsPlayedThisTurn.iterator();

            while(var2.hasNext()) {
                AbstractCard c = (AbstractCard)var2.next();
                if (c.type == AbstractCard.CardType.SKILL) {
                    ++count;
                }
            }

            --count;

            for(int i = 0; i < count; ++i) {
                this.addToTop(new DamageAction(this.target, this.info, this.attackEffect));
            }
        }

    }

}
