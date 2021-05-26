package theRanger.actions.brown.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theRanger.powers.brown.EssencePower;
import theRanger.powers.brown.ExposedPower;
import theRanger.powers.brown.KukriPower;

public class LastDitchEffortAction extends AbstractGameAction {

    private int damage;
    private int extra;

    public LastDitchEffortAction(final int damage, final int extra, final AbstractCreature target){
        this.damage = damage;
        this.extra = extra;
        this.target = target;
        this.source = AbstractDungeon.player;
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        if(!this.isDone){
            addToTop(new ApplyPowerAction(this.source, this.source, new ExposedPower(this.source)));
            int aux1 = 0;
            int aux2 = 0;
            if(this.source.hasPower(EssencePower.POWER_ID)){
                aux1 = this.source.getPower(EssencePower.POWER_ID).amount;
            }
            if(this.source.hasPower(KukriPower.POWER_ID)){
                aux2 = this.source.getPower(KukriPower.POWER_ID).amount;
            }
            if(aux1 > 0 || aux2 > 0){
                addToTop(new LoseHPAction(this.target,this.source,this.damage, AttackEffect.SLASH_HORIZONTAL));
            }else{
                addToTop(new LoseHPAction(this.target,this.source,this.damage+this.extra, AttackEffect.SMASH));
            }
            this.isDone = true;
        }
    }
}
