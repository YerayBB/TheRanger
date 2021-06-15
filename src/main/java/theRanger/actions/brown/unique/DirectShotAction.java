package theRanger.actions.brown.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theRanger.actions.brown.generic.StandardShotAction;
import theRanger.powers.brown.InfusedPower;

public class DirectShotAction extends AbstractGameAction {

    private int infuse;
    private int shot;
    private boolean isupgrade;

    public DirectShotAction(final AbstractCreature target, final int damage, final int infuse, final boolean upgrade, final int shotamount){
        this.target = target;
        this.amount = damage;
        this.infuse = infuse;
        this.shot = shotamount;
        this.actionType = ActionType.SPECIAL;
        this.source = AbstractDungeon.player;
        this.isupgrade = upgrade;
        this.isDone = false;
    }

    @Override
    public void update() {
        if(!this.isDone){
            AbstractGameAction[] list;
            if(this.isupgrade) {
                list = new AbstractGameAction[]{
                        new ApplyPowerAction(this.target, this.source, new InfusedPower(this.target, this.source, this.infuse)),
                        new DamageAction(this.target, new DamageInfo(this.source, this.amount))};
            }else {
                list = new AbstractGameAction[]{
                        new DamageAction(this.target, new DamageInfo(this.source, this.amount))};
            }
            addToTop(new StandardShotAction(this.shot, list));
            this.isDone = true;
        }

    }
}
