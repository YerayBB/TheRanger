package theRanger.actions.brown.generic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRanger.powers.brown.InfusedPower;

public class RandomEnemyDmgWithInfusion extends AbstractGameAction {

    private AbstractCard card;
    private int infusion;


    public RandomEnemyDmgWithInfusion(final AbstractCard card, final int infuse){
        this.card = card;
        this.infusion = infuse;
        this.attackEffect = AttackEffect.NONE;
    }

    public RandomEnemyDmgWithInfusion(final AbstractCard card, final int infuse, final AttackEffect effect){
        this.card = card;
        this.infusion = infuse;
        this.attackEffect = effect;
    }

    @Override
    public void update() {
        if(!this.isDone) {
            this.target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
            if (this.target != null) {
                this.card.calculateCardDamage((AbstractMonster) this.target);
                addToTop(new ApplyPowerAction(this.target, AbstractDungeon.player, new InfusedPower(this.target, AbstractDungeon.player, this.infusion), this.infusion));
                addToTop(new DamageAction(this.target, new DamageInfo(AbstractDungeon.player, this.card.damage, this.card.damageTypeForTurn), this.attackEffect));
            }
        }
    }
}
