package theRanger.actions.brown.generic;

import basemod.BaseMod;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class InfusePowerTurnAction extends AbstractGameAction {

    private boolean refreshed;
    private AbstractPower p;

    public InfusePowerTurnAction(AbstractPower p, boolean refresh){
        this.refreshed = refresh;
        this.p = p;
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        BaseMod.logger.info("Called Start Turn Infused");
        if(this.p.amount == 0){
            this.p.owner.powers.remove(this.p);
            BaseMod.logger.info("Called endRound Infused: REMOVE");
            return;
        }
        else {
            if (this.refreshed) {
                this.p.reducePower(1);
                BaseMod.logger.info("Called endRound Infused: REDUCE 1");
            } else {
                this.p.reducePower(Math.max(1, MathUtils.floor(this.amount / 2.0f)));
                BaseMod.logger.info("Called endRound Infused: REDUCE HALF");
            }
        }
        this.isDone = true;
    }
}
