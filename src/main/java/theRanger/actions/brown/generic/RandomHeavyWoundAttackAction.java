package theRanger.actions.brown.generic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


public class RandomHeavyWoundAttackAction extends AbstractGameAction {
    private int damage;
    private AbstractCreature source;

    public RandomHeavyWoundAttackAction(int damage) {
        this.damage = damage;
        this.source = AbstractDungeon.player;
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        if(!this.isDone) {
            this.target =AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
            if(this.target != null) {
                addToTop(new HeavyWoundAttackAction(this.target, new DamageInfo(this.source, this.damage)));
            }
            this.isDone = true;
        }
    }
}
