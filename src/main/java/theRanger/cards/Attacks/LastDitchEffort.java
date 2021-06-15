package theRanger.cards.Attacks;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theRanger.DefaultMod;
import theRanger.actions.brown.unique.LastDitchEffortAction;
import theRanger.cards.AbstractDynamicCard;
import theRanger.characters.TheDefault;
import theRanger.powers.brown.EssencePower;
import theRanger.powers.brown.KukriPower;

import java.util.ArrayList;
import java.util.Iterator;

import static theRanger.DefaultMod.makeCardPath;

public class LastDitchEffort extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(LastDitchEffort.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");//makeCardPath("LastDitchEffort.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_BROWN;

    private static final int COST = 3;

    private static final int DAMAGE = 15;
    private static final int UPGRADE_PLUS_DMG = 5;

    private static final int MAGIC = 30;
    private static final int UPGRADE_PLUS_MAGIC = 10;

    // /STAT DECLARATION/


    public LastDitchEffort() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.baseMagicNumber = MAGIC;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LastDitchEffortAction(this.damage,this.magicNumber,m));
    }

    @Override
    public void applyPowers() {
        this.applyPowersToBlock();
        AbstractPlayer player = AbstractDungeon.player;
        this.isDamageModified = false;
        float tmp = (float)this.baseDamage;
        Iterator var3 = player.relics.iterator();

        //relics
        while(var3.hasNext()) {
            AbstractRelic r = (AbstractRelic)var3.next();
            tmp = r.atDamageModify(tmp, this);
            if (this.baseDamage != (int)tmp) {
                this.isDamageModified = true;
            }
        }

        //powers
        AbstractPower p;
        var3 = player.powers.iterator();
        while(var3.hasNext()) {
            p = (AbstractPower)var3.next();
            if(!(p.type == AbstractPower.PowerType.DEBUFF)) {
                tmp = p.atDamageGive(tmp, this.damageTypeForTurn, this);
            }
        }

        //stances
        tmp = player.stance.atDamageGive(tmp, this.damageTypeForTurn, this);
        if (this.baseDamage != (int)tmp) {
            this.isDamageModified = true;
        }

        //powers last
        var3 = player.powers.iterator();
        while(var3.hasNext()){
            p = (AbstractPower)var3.next();
            if(!(p.type == AbstractPower.PowerType.DEBUFF)){
                tmp = p.atDamageFinalGive(tmp, this.damageTypeForTurn, this);
            }
        }

        if (tmp < 0.0F) {
            tmp = 0.0F;
        }

        if (this.baseDamage != MathUtils.floor(tmp)) {
            this.isDamageModified = true;
        }

        this.damage = MathUtils.floor(tmp);

    }

    @Override
    public void triggerOnGlowCheck() {
        int aux1 = 0;
        int aux2 = 0;
        if (AbstractDungeon.player.hasPower(EssencePower.POWER_ID)) {
            aux1 = AbstractDungeon.player.getPower(EssencePower.POWER_ID).amount;
        }
        if(AbstractDungeon.player.hasPower(KukriPower.POWER_ID)) {
            aux2 = AbstractDungeon.player.getPower(KukriPower.POWER_ID).amount;
        }
        if(aux1 > 0 || aux2 > 0) {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }else{
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }

    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DMG);
            this.upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            this.initializeDescription();
        }
    }
}
