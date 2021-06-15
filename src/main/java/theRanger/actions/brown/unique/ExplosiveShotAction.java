package theRanger.actions.brown.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import theRanger.powers.brown.InfusedPower;
import theRanger.util.ShotHelper;

public class ExplosiveShotAction extends AbstractGameAction {

    private int shot;

    public ExplosiveShotAction(AbstractCreature source, AbstractCreature target, int damage, int shot){
        this.source = source;
        this.target = target;
        this.amount = damage;
        this.shot = shot;
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        if(!this.isDone){
            if(ShotHelper.Shot(this.amount) == this.amount) {
                int var = 0;
                if (target.hasPower(InfusedPower.POWER_ID)) {
                    var = target.getPower(InfusedPower.POWER_ID).amount;
                }
                if (var > 0) {
                    this.addToTop(new DamageAllEnemiesAction(this.source, DamageInfo.createDamageMatrix(var, true), DamageInfo.DamageType.THORNS, AttackEffect.FIRE));
                    this.addToTop(new RemoveSpecificPowerAction(this.target, this.source, InfusedPower.POWER_ID));
                }
                this.addToTop(new DamageAction(this.target, new DamageInfo(this.source, this.amount), AttackEffect.BLUNT_LIGHT));
            }
            this.isDone = true;
        }
    }
}
