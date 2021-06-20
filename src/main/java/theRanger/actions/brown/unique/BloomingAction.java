package theRanger.actions.brown.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theRanger.powers.brown.InfusedPower;

public class BloomingAction extends AbstractGameAction {

    private boolean upgraded;

    public BloomingAction(AbstractCreature target, boolean upgraded){
        this.target = target;
        this.source = AbstractDungeon.player;
        this.actionType = ActionType.DAMAGE;
        this.upgraded = upgraded;
    }

    @Override
    public void update() {
        if(!this.isDone){
            int dmg = 0;
            if(this.target.hasPower(InfusedPower.POWER_ID)){
                dmg = this.target.getPower(InfusedPower.POWER_ID).amount;
            }
            addToTop(new LoseHPAction(this.target,this.source, dmg));
            if(!this.upgraded){
                addToTop(new RemoveSpecificPowerAction(this.target,this.source, InfusedPower.POWER_ID));
            }
            this.isDone = true;
        }

    }
}
