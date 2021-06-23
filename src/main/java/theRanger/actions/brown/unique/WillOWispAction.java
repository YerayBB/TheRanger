package theRanger.actions.brown.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theRanger.powers.brown.EssencePower;
import theRanger.powers.brown.SpiritPower;

public class WillOWispAction extends AbstractGameAction {

    private boolean upgraded;

    public WillOWispAction(int amount, boolean upgrade){
        this.amount = amount;
        this.upgraded = upgrade;
        this.target = AbstractDungeon.player;
    }

    @Override
    public void update() {
        if(!this.isDone){
            int aux1 = 0;
            int aux2 = 0;
            if(this.target.hasPower(EssencePower.POWER_ID)){
                aux1 = this.target.getPower(EssencePower.POWER_ID).amount;
            }
            if(this.target.hasPower(SpiritPower.POWER_ID)){
                aux2 = this.target.getPower(SpiritPower.POWER_ID).amount;
            }
            if(this.upgraded && ((aux2+1)-aux1) > this.amount){
                addToTop(new GainEnergyAction(1));
            }
            addToTop(new ApplyPowerAction(this.target, this.target, new EssencePower(this.target, this.amount), this.amount));
            this.isDone = true;
        }
    }
}
