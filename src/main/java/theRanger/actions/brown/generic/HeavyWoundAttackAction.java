package theRanger.actions.brown.generic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import theRanger.powers.brown.HeavyWoundsPower;

import java.util.Iterator;

public class HeavyWoundAttackAction extends AbstractGameAction{
    private DamageInfo info;

    public HeavyWoundAttackAction(final AbstractCreature target, final DamageInfo info) {
        this.info = info;
        setValues(target, info);
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.startDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startDuration;
    }

    public void update() {
        if (shouldCancelAction()) {
            this.isDone = true;
        } else {
            this.tickDuration();
            if (this.isDone) {
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.SLASH_HEAVY, false));
                this.target.damage(this.info);
                if (this.target.lastDamageTaken > 0) {
                    addToTop(new ApplyPowerAction(this.target, this.source, new HeavyWoundsPower(this.target, this.source, this.target.lastDamageTaken), this.target.lastDamageTaken));
                }
                if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.clearPostCombatActions();
                } else {
                    addToTop(new WaitAction(0.1F));
                }
            }
        }
    }
}
