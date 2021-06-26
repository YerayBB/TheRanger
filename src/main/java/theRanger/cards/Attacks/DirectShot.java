package theRanger.cards.Attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRanger.DefaultMod;
import theRanger.actions.brown.unique.DirectShotAction;
import theRanger.cards.AbstractDynamicCard;
import theRanger.cards.AbstractInfusedCard;
import theRanger.characters.TheDefault;
import theRanger.powers.brown.EssencePower;
import theRanger.powers.brown.InfusedPower;

import static theRanger.DefaultMod.makeCardPath;

public class DirectShot extends AbstractInfusedCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(DirectShot.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_BROWN;

    private static final int COST = 1;

    private static final int DAMAGE = 8;
    private static final int UPGRADE_PLUS_DMG = 4;

    private static final int INFUSE = 0;
    private static final int UPGRADE_PLUS_INFUSE = 4;

    private static final int SHOTAMOUNT = 1;

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /STAT DECLARATION/


    public DirectShot() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.baseInfuseNumber = INFUSE;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DirectShotAction(m, this.damage, this.infuseNumber, this.upgraded, SHOTAMOUNT));
    }

    @Override
    public void triggerOnGlowCheck() {
        if (AbstractDungeon.player.hasPower(EssencePower.POWER_ID)) {
            if(AbstractDungeon.player.getPower(EssencePower.POWER_ID).amount >= SHOTAMOUNT) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
            } else {
                this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
            }
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DMG);
            this.upgradeInfuseNumber(UPGRADE_PLUS_INFUSE);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
