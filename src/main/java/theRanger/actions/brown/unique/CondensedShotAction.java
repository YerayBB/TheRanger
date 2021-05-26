package theRanger.actions.brown.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;
import theRanger.actions.brown.generic.StandardShotAction;
import theRanger.powers.brown.DensityPower;
import theRanger.powers.brown.EssencePower;

public class CondensedShotAction extends AbstractGameAction {

    private int basedamage;
    private int shotamount;

    public CondensedShotAction(final AbstractCreature target, final int damage, final int shotamount) {
        this.source = AbstractDungeon.player;
        this.shotamount = shotamount;
        this.basedamage = damage;
        this.target = target;
        this.actionType = ActionType.SPECIAL;
        this.startDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startDuration;
    }

    @Override
    public void update() {
        if(!this.isDone){
            int damage = this.basedamage;
            int aux1 = 0;
            if(this.source.hasPower(EssencePower.POWER_ID) && this.source.hasPower(DensityPower.POWER_ID)){
                int aux2 = this.source.getPower(DensityPower.POWER_ID).amount;
                aux1 =this.source.getPower(EssencePower.POWER_ID).amount;
                damage += aux1*aux2;
            }
            addToBot(new StandardShotAction(this.shotamount,
                    new AbstractGameAction[] {
                            new ReducePowerAction(this.source,this.source,EssencePower.POWER_ID,aux1),
                            new VFXAction(this.source, new MindblastEffect(this.source.dialogX, this.source.dialogY, this.source.flipHorizontal), 0.1F),
                            new DamageAction(this.target,new DamageInfo(this.source,damage, DamageInfo.DamageType.NORMAL))}));
            this.isDone = true;
        }
    }
}
