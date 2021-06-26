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
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.calculateCardDamage(m);
        addToBot(new LastDitchEffortAction(this.damage,this.magicNumber,m));
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        this.applyPowersToBlock();
        AbstractPlayer player = AbstractDungeon.player;
        this.isDamageModified = false;
        if (!this.isMultiDamage && mo != null) {
            float tmp = (float)this.baseDamage;
            Iterator var9 = player.relics.iterator();

            //relics
            while(var9.hasNext()) {
                AbstractRelic r = (AbstractRelic)var9.next();
                tmp = r.atDamageModify(tmp, this);
                if (this.baseDamage != (int)tmp) {
                    this.isDamageModified = true;
                }
            }

            //players powers
            AbstractPower p;
            var9 = player.powers.iterator();
            while(var9.hasNext()) {
                p = (AbstractPower)var9.next();
                if(p.type == AbstractPower.PowerType.BUFF){
                    tmp = p.atDamageGive(tmp, this.damageTypeForTurn, this);
                }
            }

            //player stance
            tmp = player.stance.atDamageGive(tmp, this.damageTypeForTurn, this);
            if (this.baseDamage != (int)tmp) {
                this.isDamageModified = true;
            }

            //Monster powers
            var9 = mo.powers.iterator();
            while(var9.hasNext()) {
                p = (AbstractPower)var9.next();
                if(p.type == AbstractPower.PowerType.DEBUFF) {
                    tmp = p.atDamageReceive(tmp, this.damageTypeForTurn, this);
                }
            }

            //player powers
            var9 = player.powers.iterator();
            while(var9.hasNext()) {
                p = (AbstractPower)var9.next();
                if(p.type == AbstractPower.PowerType.BUFF) {
                    tmp = p.atDamageFinalGive(tmp, this.damageTypeForTurn, this);
                }
            }

            //monster powers
            var9 = mo.powers.iterator();
            while(var9.hasNext()) {
                p = (AbstractPower)var9.next();
                if(p.type == AbstractPower.PowerType.DEBUFF) {
                    tmp = p.atDamageFinalReceive(tmp, this.damageTypeForTurn, this);
                }
            }

            if (tmp < 0.0F) {
                tmp = 0.0F;
            }

            if (this.baseDamage != MathUtils.floor(tmp)) {
                this.isDamageModified = true;
            }

            this.damage = MathUtils.floor(tmp);
        } else {
            ArrayList<AbstractMonster> m = AbstractDungeon.getCurrRoom().monsters.monsters;
            float[] tmp = new float[m.size()];

            int i;
            for(i = 0; i < tmp.length; ++i) {
                tmp[i] = (float)this.baseDamage;
            }

            Iterator var6;
            AbstractPower p;
            for(i = 0; i < tmp.length; ++i) {
                var6 = player.relics.iterator();

                //relics
                while(var6.hasNext()) {
                    AbstractRelic r = (AbstractRelic)var6.next();
                    tmp[i] = r.atDamageModify(tmp[i], this);
                    if (this.baseDamage != (int)tmp[i]) {
                        this.isDamageModified = true;
                    }
                }

                //player powers
                var6 = player.powers.iterator();
                while(var6.hasNext()) {
                    p = (AbstractPower)var6.next();
                    if(p.type == AbstractPower.PowerType.BUFF) {
                        tmp[i] = p.atDamageGive(tmp[i], this.damageTypeForTurn, this);
                    }
                }

                //player stance
                tmp[i] = player.stance.atDamageGive(tmp[i], this.damageTypeForTurn, this);
                if (this.baseDamage != (int)tmp[i]) {
                    this.isDamageModified = true;
                }
            }

            for(i = 0; i < tmp.length; ++i) {
                var6 = (m.get(i)).powers.iterator();

                //monster power
                while(var6.hasNext()) {
                    p = (AbstractPower)var6.next();
                    if(p.type == AbstractPower.PowerType.DEBUFF) {
                        if (!(m.get(i)).isDying && !(m.get(i)).isEscaping) {
                            tmp[i] = p.atDamageReceive(tmp[i], this.damageTypeForTurn, this);
                        }
                    }
                }
            }

            for(i = 0; i < tmp.length; ++i) {
                //player powers
                var6 = player.powers.iterator();
                while (var6.hasNext()) {
                    p = (AbstractPower)var6.next();
                    if(p.type == AbstractPower.PowerType.BUFF) {
                        tmp[i] = p.atDamageFinalGive(tmp[i], this.damageTypeForTurn, this);
                    }
                }
            }

            for(i = 0; i < tmp.length; ++i) {
                var6 = (m.get(i)).powers.iterator();

                //monster powers
                while(var6.hasNext()) {
                    p = (AbstractPower)var6.next();
                    if(p.type == AbstractPower.PowerType.DEBUFF) {
                        if (!(m.get(i)).isDying && !(m.get(i)).isEscaping) {
                            tmp[i] = p.atDamageFinalReceive(tmp[i], this.damageTypeForTurn, this);
                        }
                    }
                }
            }

            for(i = 0; i < tmp.length; ++i) {
                if (tmp[i] < 0.0F) {
                    tmp[i] = 0.0F;
                }
            }

            this.multiDamage = new int[tmp.length];

            for(i = 0; i < tmp.length; ++i) {
                if (this.baseDamage != MathUtils.floor(tmp[i])) {
                    this.isDamageModified = true;
                }

                this.multiDamage[i] = MathUtils.floor(tmp[i]);
            }

            this.damage = this.multiDamage[0];
        }

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
