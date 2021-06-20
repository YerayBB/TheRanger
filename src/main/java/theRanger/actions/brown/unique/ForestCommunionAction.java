package theRanger.actions.brown.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import theRanger.powers.brown.EssencePower;
import theRanger.powers.brown.SpiritPower;

public class ForestCommunionAction extends AbstractGameAction {

    public ForestCommunionAction(AbstractCreature target){
        this.target = target;
    }

    @Override
    public void update() {
        if(!this.isDone){
            if(this.target.hasPower(SpiritPower.POWER_ID)){
                int aux = this.target.getPower(SpiritPower.POWER_ID).amount+1;
                if(aux > 0) addToTop(new ApplyPowerAction(this.target, this.target, new EssencePower(this.target, aux), aux));
            }
            else{
                addToTop(new ApplyPowerAction(this.target, this.target, new EssencePower(this.target, 1), 1));
            }
            this.isDone = true;
        }
    }
}
