package theRanger.actions.brown.generic;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;
import theRanger.powers.brown.InfusedPower;
import theRanger.util.ShotHelper;

import java.util.ArrayList;
import java.util.Iterator;

public class RainShotAction extends AbstractGameAction {

    private static final UIStrings UIStrings = CardCrawlGame.languagePack.getUIString("theRanger:MissingEssence");

    private int shot;
    private int infuse;

    public RainShotAction(int damage, int shot, int infuse){
        this.amount = damage;
        this.infuse = infuse;
        this.source = AbstractDungeon.player;
        this.shot = shot;
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        if(!this.isDone){
            ArrayList<AbstractMonster> tmp = new ArrayList<AbstractMonster>();
            int enemies = 0;
            Iterator var = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
            while (var.hasNext()){
                AbstractMonster mo = (AbstractMonster) var.next();
                if(!mo.isDying && !mo.isEscaping && !mo.halfDead){
                    tmp.add(mo);
                    ++enemies;
                }
            }
            int shotsneeded = enemies*this.shot;
            int shotsmade = ShotHelper.Shot(shotsneeded);

            if(shotsmade == shotsneeded){//complete
                for (AbstractMonster am: tmp) {
                    addToTop(new ApplyPowerAction(am,this.source,new InfusedPower(am,this.source,this.infuse), this.infuse));
                }
                addToTop(new DamageAllEnemiesAction((AbstractPlayer) this.source, this.amount, DamageInfo.DamageType.NORMAL, AttackEffect.NONE));
                int aux = this.shot == 1 ? 20 : 50;
                addToTop(new VFXAction(new BlizzardEffect(aux, AbstractDungeon.getMonsters().shouldFlipVfx()), 1.0F));
                this.isDone = true;
                return;

            }
            else if(shotsmade == 0){//null
                AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, UIStrings.TEXT[0], true));
            }
            else{//partial
                int aux = this.shot == 1 ? 20 : 30;
                int total = shotsmade/this.shot;
                aux *= (float)total/(float)enemies;
                aux += this.shot == 1 ? 0 : 20;
                while(total > 0){
                    AbstractMonster mo = tmp.get(MathUtils.random(0, tmp.size() - 1));
                    addToTop(new ApplyPowerAction(mo,this.source, new InfusedPower(mo, this.source, this.infuse), this.infuse));
                    addToTop(new DamageAction(mo, new DamageInfo(this.source,this.amount, DamageInfo.DamageType.NORMAL)));
                    tmp.remove(mo);
                    total -= 1;
                }

                addToTop(new VFXAction(new BlizzardEffect(aux, AbstractDungeon.getMonsters().shouldFlipVfx()), 1.0F));
                AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, UIStrings.TEXT[2], true));
            }
            this.isDone = true;
        }
    }
}
