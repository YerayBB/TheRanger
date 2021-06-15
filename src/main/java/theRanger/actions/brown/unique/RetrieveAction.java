package theRanger.actions.brown.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import theRanger.powers.brown.HeavyWoundsPower;
import theRanger.powers.brown.KukriPower;

public class RetrieveAction extends AbstractGameAction {

    public RetrieveAction(AbstractCreature source, AbstractCreature target, int damage){
        this.source = source;
        this.target = target;
        this.amount = damage;
        this.actionType = ActionType.SPECIAL;
        this.isDone = false;
    }

    @Override
    public void update() {
        if(!this.isDone){
            if(this.target.hasPower(HeavyWoundsPower.POWER_ID)){
                addToTop(new ApplyPowerAction(this.source, this.source, new KukriPower(this.source,this.source,1),1));
            }
            addToTop(new DamageAction(this.target, new DamageInfo(this.source, this.amount, DamageInfo.DamageType.NORMAL)));
            this.isDone = true;
        }

    }
}
