package theRanger.actions.brown.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theRanger.powers.brown.CamouflagePower;

public class DecoyAction extends AbstractGameAction {

    public DecoyAction(){
        this.target = AbstractDungeon.player;
        this.actionType = ActionType.BLOCK;
    }

    @Override
    public void update() {
        if(!this.isDone){
            int aux = 0;
            if(this.target.hasPower(CamouflagePower.POWER_ID)){
                aux = this.target.getPower(CamouflagePower.POWER_ID).amount;
            }
            addToTop(new GainBlockAction(this.target, aux));
            addToTop(new RemoveSpecificPowerAction(this.target, this.target, CamouflagePower.POWER_ID));
            this.isDone = true;
        }

    }
}
