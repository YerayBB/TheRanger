package theRanger.actions.brown.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRanger.powers.brown.InfusedPower;
import theRanger.util.ShotHelper;

public class ArrowHellAction extends AbstractGameAction {

    private int amount;
    private int damage;
    private int infuse;

    public ArrowHellAction(final int damage, final int infuse, final int amount, final AbstractCreature source){
        this.damage = damage;
        this.amount = amount;
        this.source = source;
        this.infuse = infuse;
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        if(!this.isDone){
            if(ShotHelper.Shot(this.amount) == this.amount){
                AbstractMonster mo = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
                addToTop(new ArrowHellAction(this.damage, this.infuse, this.amount, this.source));
                addToTop(new ApplyPowerAction(mo,this.source, new InfusedPower(mo,this.source,this.infuse), this.infuse));
                addToTop(new DamageAction(mo, new DamageInfo(this.source, this.damage, DamageInfo.DamageType.NORMAL), AttackEffect.SLASH_DIAGONAL));
            }
            this.isDone = true;
        }
    }
}
