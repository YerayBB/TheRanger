package theRanger.cards.Skills;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import theRanger.DefaultMod;
import theRanger.cards.AbstractDynamicCard;
import theRanger.characters.TheDefault;
import theRanger.powers.brown.EssencePower;
import theRanger.powers.brown.InfusedPower;
import theRanger.powers.brown.SpiritPower;

import java.util.Iterator;

import static theRanger.DefaultMod.makeCardPath;

public class SpiritDetonation extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(SpiritDetonation.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");//makeCardPath("SpiritDetonation.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_BROWN;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;

    // /STAT DECLARATION/


    public SpiritDetonation() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p.hasPower(SpiritPower.POWER_ID)){
            int amount = p.getPower(SpiritPower.POWER_ID).amount;
            if(amount > 0){
                addToBot(new RemoveSpecificPowerAction(p,p,SpiritPower.POWER_ID));

                if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {

                    Iterator var3 = AbstractDungeon.getMonsters().monsters.iterator();

                    while(var3.hasNext()) {
                        AbstractMonster monster = (AbstractMonster)var3.next();
                        if (!monster.isDead && !monster.isDying) {
                            this.addToBot(new ApplyPowerAction(monster, p, new InfusedPower(monster, p, amount), amount, AbstractGameAction.AttackEffect.FIRE));
                        }
                    }
                }
            }
        }

    }

    @Override
    public void triggerOnGlowCheck() {
        if (AbstractDungeon.player.hasPower(SpiritPower.POWER_ID)) {
            if(AbstractDungeon.player.getPower(SpiritPower.POWER_ID).amount > 0){
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
            } else{
                this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
            }
        }else{
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADED_COST);
            this.initializeDescription();
        }
    }
}
