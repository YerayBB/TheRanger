package theRanger.cards.Attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRanger.DefaultMod;
import theRanger.cards.AbstractDynamicCard;
import theRanger.characters.TheDefault;
import theRanger.interfaces.OnOverchargeTrigger;

import static theRanger.DefaultMod.makeCardPath;

public class EssenceBlast extends AbstractDynamicCard implements OnOverchargeTrigger {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(EssenceBlast.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");//makeCardPath("EssenceBlast.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_BROWN;

    private static final int COST = 0;
    private static final int UPGRADED_COST = 0;

    private static final int DAMAGE = 4;

    private static final int MAGIC = 2;

    // /STAT DECLARATION/


    public EssenceBlast() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
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

    @Override
    public void OnOvercharge() {
        if(this.upgraded){
            addToBot(new ModifyDamageAction(this.uuid, this.magicNumber));
        }
        addToBot(new DiscardToHandAction(this));
    }
}
