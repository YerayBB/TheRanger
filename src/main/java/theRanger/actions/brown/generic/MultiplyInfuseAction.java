package theRanger.actions.brown.generic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.PoisonPower;
import theRanger.powers.brown.InfusedPower;

public class MultiplyInfuseAction extends AbstractGameAction {

    public MultiplyInfuseAction(AbstractCreature target, AbstractCreature source, int multiplier) {
        this.target = target;
        this.source = source;
        this.amount = multiplier;
        this.actionType = ActionType.DEBUFF;
        this.attackEffect = AttackEffect.FIRE;
    }

    public void update() {
        if (this.target != null && this.target.hasPower(InfusedPower.POWER_ID)) {
            this.addToTop(new ApplyPowerAction(this.target, this.source, new InfusedPower(this.target, this.source, this.target.getPower(InfusedPower.POWER_ID).amount * this.amount), this.target.getPower(InfusedPower.POWER_ID).amount * this.amount));
        }

        this.isDone = true;
    }
}
