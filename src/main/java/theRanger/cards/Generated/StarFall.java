package theRanger.cards.Generated;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import theRanger.DefaultMod;
import theRanger.cards.AbstractDynamicCard;
import theRanger.interfaces.OnShotTrigger;

import static theRanger.DefaultMod.makeCardPath;

public class StarFall extends AbstractDynamicCard implements OnShotTrigger {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(StarFall.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");//makeCardPath("StarFall.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = CardColor.COLORLESS;//TheDefault.Enums.COLOR_BROWN;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;

    private static final int DAMAGE = 0;

    private static final int MAGIC = 7;
    private static final int UPGRADE_PLUS_MAGIC = 3;

    private int shots;

    // /STAT DECLARATION/


    public StarFall() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.isEthereal = true;
        this.exhaust = true;
        this.shots = 0;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new RainingGoldEffect(this.magicNumber * 2, true)));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void applyPowers() {
        this.baseDamage = this.shots * this.magicNumber;
        super.applyPowers();
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + this.shots + cardStrings.EXTENDED_DESCRIPTION[1];
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[2];
        this.initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            this.upgradeBaseCost(UPGRADED_COST);
            this.initializeDescription();
        }
    }

    @Override
    public void OnShot() {
        this.shots +=1;
    }
}
