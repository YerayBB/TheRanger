package theRanger.actions.brown.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theRanger.powers.brown.TemporalStrengthDownPower;

public class BluffAction extends AbstractGameAction {

    public BluffAction(){
        this.source = AbstractDungeon.player;
        this.target = AbstractDungeon.player;
        this.actionType = ActionType.SPECIAL;
    }
    @Override
    public void update() {
        if(!this.isDone){
            int aux = 0;
            if(this.target.hasPower(VigorPower.POWER_ID)){
                aux = this.target.getPower(VigorPower.POWER_ID).amount;
            }
            if(aux != 0){
                addToTop(new ApplyPowerAction(this.target, this.source, new TemporalStrengthDownPower(this.target, this.source, aux), aux));
                addToTop(new ApplyPowerAction(this.target, this.source, new VigorPower(this.target, aux), aux));
            }
            this.isDone = true;
        }
    }
}
