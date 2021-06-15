package theRanger.actions.brown.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.ReaperEffect;
import theRanger.powers.brown.EssencePower;
import theRanger.powers.brown.SpiritPower;

public class SoulReapAction extends AbstractGameAction {

    private DamageInfo info;
    private boolean upgraded;

    public SoulReapAction(final AbstractCreature target, final DamageInfo info, boolean upgraded) {
        this.info = info;
        this.upgraded = upgraded;
        this.setValues(target, info);
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
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.LIGHTNING, false));
                this.target.damage(this.info);
                if (this.target.lastDamageTaken > 0) {
                    if(this.upgraded){
                        addToTop(new ApplyPowerAction(this.source, this.source, new EssencePower(this.source, this.target.lastDamageTaken), this.target.lastDamageTaken));
                    }
                    addToTop(new ApplyPowerAction(this.source, this.source, new SpiritPower(this.source, this.target.lastDamageTaken), this.target.lastDamageTaken));
                    addToTop(new VFXAction(new ReaperEffect(),0.1f));
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
